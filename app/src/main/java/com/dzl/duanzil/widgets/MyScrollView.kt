package com.dzl.duanzil.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.appcompat.widget.LinearLayoutCompat
import kotlin.math.abs

/**
 * @name MyScrollView
 * @package com.dzl.duanzil.widgets
 * @author 345 QQ:1831712732
 * @time 2023/03/07 15:27
 * @description
 */
class MyScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {


    private var mTouchSlop = 0
    private var startY = 0f

    private var mTouViewHeight = 0

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }



    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        //在太透明还没有小时的时候拦截时间
        var interceptor = false

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val endY = ev.rawY
                if (abs(startY - endY) > mTouchSlop) {
                    if (isViewHidden((startY - endY).toInt()) || isViewShow((startY - endY).toInt())) {
                        interceptor = true
                    }
                }
                startY = endY
            }

        }

        return interceptor
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val endY = event.y
                if (abs(endY - startY) > mTouchSlop) {
                    scrollBy(0, (startY - endY).toInt())
                }
                startY = event.y
            }
        }
        return super.onTouchEvent(event)
    }


    override fun scrollTo(x: Int, y: Int) {

        var finalY = 0
        if (y < 0) {

        } else {
            finalY = y
        }

        if (y > mTouViewHeight) {
            finalY = mTouViewHeight
        }

        super.scrollTo(x, finalY)
    }

    /**
     * 头部 View 逐渐消失
     * @param dy 手指滑动的相对距离，大于 0 时，手指向上滑动，小于 0 时，手指向下滑动
     * */
    fun isViewHidden(dy: Int): Boolean {
        return dy > 0 && scrollY < mTouViewHeight
    }


    fun isViewShow(dy: Int): Boolean {
        return dy < 0 && scrollY > 0 && !canScrollVertically(-1)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTouViewHeight = getChildAt(0).measuredHeight
    }
}