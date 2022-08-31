package com.dzl.duanzil.viewmodel.video

import androidx.lifecycle.*
import com.dzl.duanzil.bean.VideoListBean
import com.dzl.duanzil.extension.jokesApi
import com.lvhttp.net.launch.launchHttp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @name VidioAdapter
 * @package com.dzl.duanzil.viewmodel.video
 * @author 345 QQ:1831712732
 * @time 2022/08/25 15:36
 * @description
 */
class VideoViewModel : ViewModel() {

    private val _state by lazy { MutableLiveData<VideoUIState>() }


    val state: LiveData<VideoUIState> = _state

    private val intent = MutableSharedFlow<VideoIntent>()

    init {
        viewModelScope.launch {
            intent.collect {
                when (it) {
                    VideoIntent.LoadVideoList -> videoList()
                }
            }
        }
    }

    fun dispatch(videoIntent: VideoIntent) {
        viewModelScope.launch {
            intent.emit(videoIntent)
        }
    }

    private fun videoList() {
        viewModelScope.launch {
            launchHttp {
                jokesApi.videoList()
            }.toData {
                _state.value = VideoUIState(it.data)
            }.toError {
                _state.value = VideoUIState(null)
            }
        }
    }

}

class VideoUIState(val list: VideoListBean?)

sealed class VideoIntent {
    object LoadVideoList : VideoIntent()
}