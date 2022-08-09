package com.dzl.duanzil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzl.duanzil.bean.CommentListBean
import com.dzl.duanzil.bean.JokeBean
import com.dzl.duanzil.state.home.HomeTabViewAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
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


    init {
        viewModelScope.launch {
            intent.collect{
                when(it){
                    JokesIntent.LoadJokesDetail -> TODO()
                    JokesIntent.LoadMoreComment -> TODO()
                    JokesIntent.RefreshComment -> TODO()
                }
            }
        }
    }

    private fun dispatch(jokesIntent: JokesIntent){
        viewModelScope.launch { intent.emit(jokesIntent) }
    }

    private fun jokesDetail(){

    }

    private fun loadComment(page:Int){
    }

}


sealed class JokesUIState {
    data class JokesData(val jokeBean: JokeBean) : JokesUIState()
    data class RefreshComment(val comment: CommentListBean) : JokesUIState()
    data class LoadMoreComment(val comment: CommentListBean) : JokesUIState()
}

sealed class JokesIntent {
    object LoadJokesDetail : JokesIntent()
    object RefreshComment : JokesIntent()
    object LoadMoreComment : JokesIntent()
}