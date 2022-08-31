package com.dzl.duanzil.widgets.video

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.dzl.duanzil.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 * @name CustomStyledPlayerControlView
 * @package com.dzl.duanzil.widgets.video
 * @author 345 QQ:1831712732
 * @time 2022/08/16 18:38
 * @description
 */
class DyStyledGSYVideoPlayer : StandardGSYVideoPlayer {

    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    lateinit var dyPlay: AppCompatImageView

    override fun init(context: Context?) {
        super.init(context)
        dyPlay = findViewById(R.id.dy_play)
        setOnClickListener {
            if (it.isVisible) {
                it.visibility = View.GONE
                onVideoResume()
            } else {
                it.visibility = View.VISIBLE
                onVideoPause()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dy_video_layout_standard
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