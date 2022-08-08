package com.dzl.duanzil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzl.duanzil.extension.homeApi
import com.dzl.duanzil.state.ListStatus
import com.dzl.duanzil.state.home.HomeTabViewAction
import com.dzl.duanzil.state.home.HomeTabViewState
import com.lvhttp.net.launch.launchHttp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @name HomeViewModel
 * @package com.dzl.duanzil.viewmodel
 * @author 345 QQ:1831712732
 * @time 2022/08/03 18:14
 * @description
 */
class HomeTabViewModel : ViewModel() {

    private val _state by lazy { MutableLiveData<HomeTabViewState>() }
    val state: LiveData<HomeTabViewState> = _state

    private var type: Int = 0
    private val intent = MutableSharedFlow<HomeTabViewAction>()

    init {
        viewModelScope.launch {
            intent.collect {
                when (it) {
                    is HomeTabViewAction.LoadMore -> refresh(it.page)
                    is HomeTabViewAction.RefreshList -> refresh(0)
                    is HomeTabViewAction.Type -> type = it.type
                }
            }
        }
    }

    fun dispatch(action: HomeTabViewAction) {
        viewModelScope.launch {
            intent.emit(action)
        }
    }


    private fun jokesLike(id:String,status:Boolean){
        viewModelScope.launch {

        }
    }

    private fun refresh(page: Int) {
        viewModelScope.launch {
            launchHttp {
                when (type) {
                    0 -> homeApi.homeRecommend(page)
                    1 -> homeApi.homeLatest(page)
                    2 -> homeApi.homePic(page)
                    3 -> homeApi.homeText(page)
                    else -> homeApi.homeLatest(page)
                }
            }.toData {
                if (page == 0)
                    _state.value = HomeTabViewState(ListStatus.RefreshSuccessStatus, it.data)
                else
                    _state.value = HomeTabViewState(ListStatus.LoadMoreSuccessStatus, it.data)
            }.toError {
                if (page == 0)
                    _state.value = HomeTabViewState(ListStatus.RefreshSuccessStatus, null)
                else
                    _state.value = HomeTabViewState(ListStatus.LoadMoreSuccessStatus, null)
            }
        }
    }


}