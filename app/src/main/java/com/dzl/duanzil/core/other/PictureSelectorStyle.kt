package com.dzl.duanzil.core.other

import android.content.Context
import androidx.core.content.ContextCompat
import com.luck.picture.lib.R
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.TitleBarStyle

/**
 * @name PictureSelectorStyle
 * @package com.dzl.duanzil.core.other
 * @author 345 QQ:1831712732
 * @time 2022/08/24 16:32
 * @description
 */
class PictureSelectorStyle(val context: Context) : PictureSelectorStyle() {


    init {
        bottomBarStyle = BottomNavBarStyle()
        bottomBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(context, R.color.ps_color_white)
        bottomBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(context, R.color.ps_color_white)

        titleBarStyle = TitleBarStyle()
        titleBarStyle.titleBackgroundColor = ContextCompat.getColor(context, R.color.ps_color_black)

    }
}