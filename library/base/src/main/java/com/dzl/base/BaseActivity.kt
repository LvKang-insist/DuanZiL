package com.dzl.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

/**
 * @name BaseActivity
 * @package com.dzl.base
 * @author 345 QQ:1831712732
 * @time 2022/08/02 15:31
 * @description Activity 基类
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initActivity()
    }

    private fun initActivity() {
        initLayout()
        initView()
        initData()
    }

    open fun initLayout() {
        initSoftKeyboard()
    }

    open fun initContentView() {
        getLayoutId()?.run { setContentView(this) }
    }


    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }


    /** 获取布局 ID */
    abstract fun getLayoutId(): Int?


    /** 初始化 View */
    abstract fun initView()

    /** 初始化数据 */
    open fun initData() = Unit


    override fun finish() {
        super.finish()
        // 隐藏软键，避免内存泄漏
        hideKeyboard(currentFocus)
    }

    /**
     * 初始化软键盘
     */
    protected open fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView()?.setOnClickListener(View.OnClickListener { v: View? ->
            // 隐藏软键，避免内存泄漏
            hideKeyboard(currentFocus)
        })
    }

    /**
     * 隐藏软键盘
     */
    open fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val manager = view.context
            .getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return
        manager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    open fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }
}