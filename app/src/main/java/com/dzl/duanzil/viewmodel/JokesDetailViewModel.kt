package com.dzl.duanzil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzl.duanzil.bean.CommentListBean
import com.dzl.duanzil.bean.CommentListItemBean
import com.dzl.duanzil.extension.jokesApi
import com.lvhttp.net.launch.launchHttp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @name JokesDetailViewModel
 * @package com.dzl.duanzil.viewmodel
 * @author 345 QQ:1831712732
 * @time 2022/08/09 21:12
 * @description
 */
class JokesDetailViewModel : ViewModel() {

    private val _state by lazy { MutableLiveData<JokesUIState>() }
    val state : LiveData<JokesUIState> = _state

    private val intent = MutableSharedFlow<JokesIntent>()

    private var jokeId = -1

    init {
        viewModelScope.launch {
            intent.collect {
                when (it) {
                    is JokesIntent.JokesId -> jokeId = it.jokeId
                    is JokesIntent.LoadMoreComment -> loadComment(it.page)
                    is JokesIntent.RefreshComment -> loadComment(0)
                    is JokesIntent.LoadMoreChildComment -> loadChildComment(
                        it.page, it.commentId, it.parentPos, it.curPos
                    )
                }
            }
        }
    }

    fun dispatch(jokesIntent: JokesIntent) {
        viewModelScope.launch { intent.emit(jokesIntent) }
    }


    private fun loadChildComment(page: Int, commentId: Int, parentPos: Int, curPos: Int) {
        viewModelScope.launch {
            launchHttp {
                jokesApi.jokesCommentListItem(commentId, page)
            }.toData {
                _state.value = JokesUIState.LoadMoreChildComment(it.data, parentPos, curPos)
            }
        }
    }

    private fun loadComment(page: Int) {
        viewModelScope.launch {
            launchHttp {
                jokesApi.jokesCommentList(jokeId, page)
            }.toData {
                if (page == 0)
                    _state.value = JokesUIState.RefreshComment(it.data)
                else
                    _state.value = JokesUIState.LoadMoreComment(it.data)
            }
        }
    }

}


sealed class JokesUIState {
    data class RefreshComment(val comment: CommentListBean) : JokesUIState()
    data class LoadMoreComment(val comment: CommentListBean) : JokesUIState()


    data class LoadMoreChildComment(
        val comment: List<CommentListItemBean>,
        val parentPos: Int,
        val curPos: Int
    ) : JokesUIState()
}

sealed class JokesIntent {
    data class JokesId(val jokeId: Int) : JokesIntent()
    object RefreshComment : JokesIntent()
    data class LoadMoreComment(val page: Int) : JokesIntent()

    data class LoadMoreChildComment(
        val page: Int,
        val commentId: Int,
        val parentPos: Int,
        val curPos: Int
    ) : JokesIntent()
}