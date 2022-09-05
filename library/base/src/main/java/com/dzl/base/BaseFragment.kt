package com.dzl.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @name BaseFragment
 * @package com.dzl.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:17
 * @description
 */
abstract class BaseFragment : Fragment() {

    lateinit var rootView: View

     var isLazyLoad = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initLayout(inflater, container, savedInstanceState)
        return rootView
    }


    open fun initLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        rootView = inflater.inflate(getLayoutId(), container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!isLazyLoad) {
            isLazyLoad = true
            initView()
            initData()
            listener()
        }
    }

    /** 获取布局 ID */
    abstract fun getLayoutId(): Int

    /** 初始化 View */
    abstract fun initView()

    /** 初始化数据 */
    open fun initData() = Unit

    /** 处理事件 */
    open fun listener() = Unit

}