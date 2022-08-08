package com.dzl.duanzil.core.base

import android.view.View
import com.dzl.base.BaseFragment
import com.dzl.duanzil.R
import com.gyf.immersionbar.ImmersionBar

/**
 * @name BaseAppFragment
 * @package com.dzl.duanzil.core.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 17:29
 * @description Fragment 业务基类
 */
abstract class BaseAppFragment : BaseFragment() {


    override fun onResume() {
        initStatusBar()
        super.onResume()
    }

    private fun initStatusBar() {
        if (isStatusBarEnable()) {
            ImmersionBar
                .with(this)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(isStatusDarkFont())
                .init()
            getTitleBar()?.let {
                ImmersionBar.setTitleBar(this, it)
            } ?: kotlin.run {
                rootView.findViewById<View>(R.id.toolbar)?.let {
                    ImmersionBar.setTitleBar(this, it)
                }
            }
        }
    }

    /** 是否启用沉浸式状态栏 */
    open fun isStatusBarEnable(): Boolean = false

    /** 状态栏字体深色模式 */
    open fun isStatusDarkFont(): Boolean = true

    open fun getTitleBar(): View? = null
}