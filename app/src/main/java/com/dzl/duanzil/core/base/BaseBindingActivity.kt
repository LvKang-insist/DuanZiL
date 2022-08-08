package com.dzl.duanzil.core.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @name BaseBindingActivity
 * @package com.dzl.duanzil.core.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 16:11
 * @description
 */
abstract class BaseBindingActivity<T : ViewDataBinding> : BaseAppActivity() {

    lateinit var binding: T

    override fun initContentView() {
        if (getLayoutId() != null) {
            binding = DataBindingUtil.setContentView(this, getLayoutId()!!)
        } else {
            throw NullPointerException("BaseBindingActivity：please implementation getLayoutId method!")
        }
    }

}