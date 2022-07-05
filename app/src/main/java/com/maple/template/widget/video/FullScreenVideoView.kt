package com.maple.template.widget.video

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

class FullScreenVideoView: VideoView {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet,0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = View.getDefaultSize(0,widthMeasureSpec)
        val heightSize = View.getDefaultSize(0,heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }
}