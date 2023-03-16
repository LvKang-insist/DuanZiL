package com.dzl.duanzil.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/**
 * @name DependentView
 * @package com.dzl.duanzil.widgets
 * @author 345 QQ:1831712732
 * @time 2023/03/14 17:14
 * @description
 */
class DependentView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var paint: Paint
    private var mStartX = 0
    private var mStartY = 0

    init {
        paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        isClickable = true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        canvas?.let {
            it.drawRect(
                Rect().apply {
                    left = 200
                    top = 200
                    right = 20
                    bottom = 400
                },
                paint
            )
        }
    }


}