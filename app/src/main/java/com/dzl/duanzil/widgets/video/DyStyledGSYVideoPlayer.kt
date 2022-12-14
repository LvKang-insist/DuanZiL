package com.dzl.duanzil.widgets.video

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.AttributeSet
import android.view.*
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dzl.duanzil.R
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer

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
    lateinit var mCoverImage: AppCompatImageView

    var mCoverOriginUrl: String? = null
    var mCoverOriginId = 0
    var mDefaultRes = 0

    override fun init(context: Context?) {
        super.init(context)
        dyPlay = findViewById(R.id.dy_play)
        mCoverImage = findViewById(R.id.thumbImage)

        if (mThumbImageViewLayout != null &&
            (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)
        ) {
            mThumbImageViewLayout.visibility = VISIBLE
        }
    }


    init {
        gestureDetector = GestureDetector(
            context.applicationContext,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    touchDoubleUp(e)
                    return super.onDoubleTap(e)
                }

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    setPlayIconState(dyPlay.isVisible)
                    return super.onSingleTapUp(e)
                }

                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
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

    fun setPlayIconState(visibility: Boolean) {
        if (visibility) {
            dyPlay.visibility = View.GONE
            onVideoResume()
        } else {
            dyPlay.visibility = View.VISIBLE
            onVideoPause()
        }
    }

    fun setPlayIconVisible(visibility: Int) {
        dyPlay.visibility = visibility
    }

    override fun startPlayLogic() {
        setPlayIconVisible(View.GONE)
        super.startPlayLogic()
    }

    override fun onVideoPause() {
        setPlayIconVisible(View.VISIBLE)
        super.onVideoPause()
    }

    override fun getLayoutId(): Int {
        return R.layout.dy_video_layout_standard
    }


    fun loadCoverImage(url: String, res: Int) {
        mCoverOriginUrl = url
        mDefaultRes = res
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(0)
                    .centerCrop()
                    .error(res)
//                    .placeholder(res)
            )
            .load(url)
            .into(mCoverImage)
    }

    fun loadCoverImageBy(id: Int, res: Int) {
        mCoverOriginId = id
        mDefaultRes = res
        mCoverImage.setImageResource(id)
    }

    override fun startWindowFullscreen(
        context: Context?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer? {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val dyStyledGSYVideoPlayer: DyStyledGSYVideoPlayer =
            gsyBaseVideoPlayer as DyStyledGSYVideoPlayer
        if (mCoverOriginUrl != null) {
            dyStyledGSYVideoPlayer.loadCoverImage(mCoverOriginUrl!!, mDefaultRes)
        } else if (mCoverOriginId != 0) {
            dyStyledGSYVideoPlayer.loadCoverImageBy(mCoverOriginId, mDefaultRes)
        }
        return gsyBaseVideoPlayer
    }

    override fun showSmallVideo(
        size: Point?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer? {
        //?????????????????????????????????????????????
        val dyStyledGSYVideoPlayer: DyStyledGSYVideoPlayer =
            super.showSmallVideo(size, actionBar, statusBar) as DyStyledGSYVideoPlayer
        dyStyledGSYVideoPlayer.mStartButton.visibility = GONE
        dyStyledGSYVideoPlayer.mStartButton = null
        return dyStyledGSYVideoPlayer
    }

    override fun cloneParams(from: GSYBaseVideoPlayer, to: GSYBaseVideoPlayer) {
        super.cloneParams(from, to)
        val sf: DyStyledGSYVideoPlayer = from as DyStyledGSYVideoPlayer
        val st: DyStyledGSYVideoPlayer = to as DyStyledGSYVideoPlayer
        st.mShowFullAnimation = sf.mShowFullAnimation
    }


    /**
     * ??????window?????????????????????
     */
    override fun clearFullscreenLayout() {
        if (!mFullAnimEnd) {
            return
        }
        mIfCurrentIsFullscreen = false
        var delay = 0
        // ------- ???????????????????????????????????????????????????????????????-------
        // ??????????????????????????????????????? setNeedOrientationUtils(false)
        if (mOrientationUtils != null) {
            delay = mOrientationUtils.backToProtVideo()
            mOrientationUtils.isEnable = false
            if (mOrientationUtils != null) {
                mOrientationUtils.releaseListener()
                mOrientationUtils = null
            }
        }
        if (!mShowFullAnimation) {
            delay = 0
        }
        val vp =
            CommonUtil.scanForActivity(context).findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val oldF = vp.findViewById<View>(fullId)
        if (oldF != null) {
            //??????fix bug#265?????????????????????????????????????????????
            val gsyVideoPlayer: DyStyledGSYVideoPlayer = oldF as DyStyledGSYVideoPlayer
            gsyVideoPlayer.mIfCurrentIsFullscreen = false
        }
        if (delay == 0) {
            backToNormal()
        } else {
            postDelayed({ backToNormal() }, delay.toLong())
        }
    }


    /******************* ?????????????????????????????????????????????????????????????????????????????????  */
    override fun onSurfaceUpdated(surface: Surface?) {
        super.onSurfaceUpdated(surface)
        if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == VISIBLE) {
            mThumbImageViewLayout.visibility = INVISIBLE
        }
    }

    override fun setViewShowState(view: View?, visibility: Int) {
        if (view == null) return
        if (view === mThumbImageViewLayout && visibility != VISIBLE) {
            return
        }
        super.setViewShowState(view, visibility)
    }

    override fun onSurfaceAvailable(surface: Surface?) {
        super.onSurfaceAvailable(surface)
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE) {
            if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == VISIBLE) {
                mThumbImageViewLayout.visibility = INVISIBLE
            }
        }
    }

    /******************* ???????????????????????????????????????????????????????????????????????????????????????  */
    protected var byStartedClick = false

    override fun onClickUiToggle(e: MotionEvent?) {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle(e)
    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
        setViewShowState(mBottomProgressBar, VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
//            setViewShowState(titleTextView, View.VISIBLE)
//            setViewShowState(mBackButton, View.VISIBLE)
//        } else {
//            setViewShowState(titleTextView, View.GONE)
//            setViewShowState(mBackButton, View.GONE)
//        }
    }

}