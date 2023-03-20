package com.dzl.duanzil.extension

import android.view.View
import android.widget.FrameLayout

/**
 * @name UiExt
 * @package com.dzl.duanzil.extension
 * @author 345 QQ:1831712732
 * @time 2023/03/20 09:53
 * @description
 */

/** 将 View 从父控件中移除 */
fun View.removeViewFormParent() {
    when (val parent = parent as? View) {
        is FrameLayout -> {
            parent.removeView(this)
        }
    }
}