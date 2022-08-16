package com.dzl.duanzil.widgets.video

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.StyledPlayerControlView

/**
 * @name CustomStyledPlayerControlView
 * @package com.dzl.duanzil.widgets.video
 * @author 345 QQ:1831712732
 * @time 2022/08/16 18:38
 * @description
 */
class CustomStyledPlayerControlView : StyledPlayerControlView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        playbackAttrs: AttributeSet?
    ) : super(context, attrs, defStyleAttr, playbackAttrs)



}