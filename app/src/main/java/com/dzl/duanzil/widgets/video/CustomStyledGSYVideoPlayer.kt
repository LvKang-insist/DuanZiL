package com.dzl.duanzil.widgets.video

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.dzl.duanzil.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * @name CustomStyledPlayerControlView
 * @package com.dzl.duanzil.widgets.video
 * @author 345 QQ:1831712732
 * @time 2022/08/16 18:38
 * @description
 */
class CustomStyledGSYVideoPlayer : StandardGSYVideoPlayer {

    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun init(context: Context?) {
        super.init(context)
    }

    override fun getLayoutId(): Int {
        return R.layout.custom_video_layout_standard
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            setViewShowState(titleTextView, View.VISIBLE)
            setViewShowState(mBackButton, View.VISIBLE)
        } else {
            setViewShowState(titleTextView, View.GONE)
            setViewShowState(mBackButton, View.GONE)
        }
    }

}