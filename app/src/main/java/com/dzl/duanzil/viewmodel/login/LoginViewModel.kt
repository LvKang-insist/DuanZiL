package com.dzl.duanzil.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzl.duanzil.extension.MMkvEnum
import com.dzl.duanzil.extension.putString
import com.dzl.duanzil.extension.userApi
import com.hjq.toast.ToastUtils
import com.lvhttp.net.launch.launchHttp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @name LoginViewModel
 * @package com.dzl.duanzil.viewmodel.login
 * @author 345 QQ:1831712732
 * @time 2022/08/11 17:37
 * @description
 */
class LoginViewModel : ViewModel() {

    private val _state by lazy { MutableLiveData<LoginUIState>() }
    val state: LiveData<LoginUIState> = _state

    private val intent = MutableSharedFlow<LoginIntent>()

    init {
        viewModelScope.launch {
            intent.collect { it ->
                when (it) {
                    is LoginIntent.CodeLogin -> codeLogin(it.number, it.pas)
                    is LoginIntent.SendCode -> sendCode(it.number)
                }
            }
        }
    }

    fun dispatch(loginIntent: LoginIntent) {
        viewModelScope.launch { intent.emit(loginIntent) }
    }

    private fun sendCode(phone:String){
        viewModelScope.launch {
            launchHttp {
                userApi.sendCode(phone)
            }.toData {
                ToastUtils.show("登录成功")
            }
        }
    }

    private fun codeLogin(number: String, code: String) {
        viewModelScope.launch {
            launchHttp { userApi.loginCode(number, code) }.toData {
                it.data.let { bean ->
                    MMkvEnum.TOKEN.putString(bean.token)
                    _state.value = LoginUIState.LoginSuccess
                }
            }
        }
    }

}

sealed class LoginUIState {
    object LoginSuccess : LoginUIState()
}

sealed class LoginIntent {
    class CodeLogin(val number: String, val pas: String) : LoginIntent()
    class SendCode(val number: String) : LoginIntent()
}