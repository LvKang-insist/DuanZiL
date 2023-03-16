package com.dzl.duanzil.extension

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import com.dzl.duanzil.core.other.RoundViewHelper
import com.dzl.duanzil.utils.ScreenUtil
import timber.log.Timber

/**
 * @name BindingExt
 * @package com.dzl.duanzil.extension
 * @author 345 QQ:1831712732
 * @time 2022/08/10 23:43
 * @description
 */


/**
 * @param radius 圆角大小
 */
@BindingAdapter("radius")
fun View.setRadius(radius: Int) {
    RoundViewHelper.setViewOutLine(this, radius, 0)
}

@BindingAdapter("radiusDp")
fun View.setRadiusDp(radius: Int) {
    RoundViewHelper.setViewOutLine(this, ScreenUtil.dp2px(context, radius.toFloat()), 0)
}


/**
 * @param radiusSide 参考 RoundViewHelper
 */
@BindingAdapter("radius", "radiusSide")
fun View.setRadius(radius: Int, radiusSide: Int) {
    RoundViewHelper.setViewOutLine(this, radius, radiusSide)
}
