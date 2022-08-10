package com.dzl.duanzil.extension

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

/**
 * @param radiusSide 参考 RoundViewHelper
 */
@BindingAdapter("radius", "radiusSide")
fun View.setRadius(radius: Int, radiusSide: Int) {
    RoundViewHelper.setViewOutLine(this, radius, radiusSide)
}
