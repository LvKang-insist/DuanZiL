package com.dzl.duanzil.ui.login

import androidx.lifecycle.Observer
import com.dzl.duanzil.R
import com.dzl.duanzil.core.base.BaseBindingActivity
import com.dzl.duanzil.databinding.ActivityLoginBinding
import com.dzl.duanzil.viewmodel.login.LoginIntent
import com.dzl.duanzil.viewmodel.login.LoginUIState
import com.dzl.duanzil.viewmodel.login.LoginViewModel
import com.hjq.toast.ToastUtils

/**
 * @name LoginActivity
 * @package com.dzl.duanzil.ui.login
 * @author 345 QQ:1831712732
 * @time 2022/08/11 17:05
 * @description
 */
class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {


    val viewModel by lazy { LoginViewModel() }

    override fun getLayoutId(): Int = R.layout.activity_login


    override fun initView() {

    }

    override fun listener() {
        binding.login.setOnClickListener {
            val number = binding.inputNumber.text.toString()
            val pas = binding.inputPassword.text.toString()
            viewModel.dispatch(LoginIntent.CodeLogin(number, pas))
        }

        viewModel.state.observe(this, Observer {
            when (it) {
                LoginUIState.LoginSuccess -> {
                    finish()
                    ToastUtils.show("登录成功")
                }
            }
        })

        binding.sendCode.setOnClickListener {
            val number = binding.inputNumber.text.toString()
            viewModel.dispatch(LoginIntent.SendCode(number))
        }
    }
}