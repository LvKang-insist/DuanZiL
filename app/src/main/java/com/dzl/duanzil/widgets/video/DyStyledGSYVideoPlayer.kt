package com.dzl.duanzil.widgets.video

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.dzl.duanzil.R
import com.hjq.toast.ToastUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import timber.log.Timber

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
        gestureDetector = GestureDetector(
            getContext().applicationContext,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    touchDoubleUp(e)
                    return super.onDoubleTap(e)
                }

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    ToastUtils.show("hh")
                    if (dyPlay.isVisible) {
                        dyPlay.visibility = View.GONE
                        onVideoResume()
                    } else {
                        dyPlay.visibility = View.VISIBLE
                        onVideoPause()
                    }
                    return super.onSingleTapUp(e)
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    ToastUtils.show("222")
//                    if (!mChangePosition && !mChangeVolume && !mBrightness) {
//                        onClickUiToggle(e)
//                    }

                    return super.onSingleTapConfirmed(e)
                }

                override fun onLongPress(e: MotionEvent) {
                    super.onLongPress(e)
                    touchLongPress(e)
                }
            })
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