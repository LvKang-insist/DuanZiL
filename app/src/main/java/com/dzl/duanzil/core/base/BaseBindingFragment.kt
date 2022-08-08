package com.dzl.duanzil.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @name BaseBindingFragment
 * @package com.dzl.duanzil.core.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:31
 * @description
 */
abstract class BaseBindingFragment<T : ViewDataBinding> : BaseAppFragment() {

    lateinit var binding: T

    override fun initLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        rootView = binding.root
    }

}