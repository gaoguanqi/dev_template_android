package com.maple.commonlib.widget.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.maple.commonlib.R

/**
 * 实心圆形/圆环
 */
class DotView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
    private var dotColor: Int = Color.RED
    private var dotStype: Int = 0
    private val strokeWidth: Float = 2.0f

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.DotView)
            typedArray.let { ta ->
                dotColor = ta.getColor(R.styleable.DotView_dotColor, dotColor)
                paint.color = dotColor
                dotStype = ta.getInteger(R.styleable.DotView_dotStyle, dotStype)
                if (dotStype != 0) {
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = strokeWidth
                }
                ta.recycle()
            }
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(width / 2.0f, height / 2.0f, width / 2.0f - strokeWidth, paint)
    }


    fun setColor(color: Int) {
        paint.color = color
        invalidate()
    }

}