package com.dzl.duanzil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzl.duanzil.bean.CommentListBean
import com.dzl.duanzil.bean.JokeBean
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
                    JokesIntent.RefreshComment -> loadComment(0)
                }
            }
        }
    }

    fun dispatch(jokesIntent: JokesIntent) {
        viewModelScope.launch { intent.emit(jokesIntent) }
    }

    private fun jokesDetail() {

    }

    private fun loadComment(page: Int) {
        viewModelScope.launch {
            launchHttp {
                jokesApi.jokesCommentList(jokeId, page)
            }.toData {
                if (page == 1)
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
}

sealed class JokesIntent {
    data class JokesId(val jokeId: Int) : JokesIntent()
    object RefreshComment : JokesIntent()
    data class LoadMoreComment(val page: Int) : JokesIntent()
}