package com.dzl.duanzil.core.base

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.dzl.base.BaseActivity
import com.dzl.duanzil.R
import com.gyf.immersionbar.ImmersionBar

/**
 * @name BaseAppActivity
 * @package com.dzl.duanzil.core.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 16:01
 * @description Activity 业务基类
 */
abstract class BaseAppActivity : BaseActivity() {


    override fun initLayout() {
        super.initLayout()
        initStatusBar()
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
                findViewById<View>(R.id.toolbar)?.let {
                    ImmersionBar.setTitleBar(this, it)
                }
            }
        }
    }


    /** 是否启用沉浸式状态栏 */
    open fun isStatusBarEnable(): Boolean = true

    /** 状态栏字体深色模式 */
    open fun isStatusDarkFont(): Boolean = true

    open fun getTitleBar(): View? = null


    override fun onDestroy() {
        super.onDestroy()
        getContentView()?.run {
            traverse(this)
        }
    }

    private fun traverse(root: ViewGroup) {
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                child.background = null
                traverse(child)
            } else {
                EditText(this).addTextChangedListener { }
                child.background = null
                when {
                    child is ImageView -> child.setImageDrawable(null)
                }
            }
        }
    }
}