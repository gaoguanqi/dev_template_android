package com.maple.commonlib.widget.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.R

/**
 * 自定义View 绘制虚线, 系统兼容问题
 *
 * 使用：
 * <包名.DashLineView
    ...
    app:line_space="2dp"
    app:blank_space="2dp"
    app:line_color="#000000"
    app:orientation="horizontal"/>
 */
class DashLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): View(context, attrs,defStyle) {

    private val paint: Paint = Paint()
    private var orientation: Int = 0
    private var lineColor: Int = 0
    private var lineSpace: Int = 0
    private var blankSpace: Int = 0
    private var path: Path = Path()

    init {
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.DashLineView)
            typedArray.let { ta ->
                orientation = ta.getInteger(R.styleable.DashLineView_orientation,0)
                lineColor = ta.getColor(R.styleable.DashLineView_line_color,Color.BLACK)
                lineSpace =ta.getDimensionPixelOffset(R.styleable.DashLineView_line_space,dp2px(5f))
                blankSpace =ta.getDimensionPixelOffset(R.styleable.DashLineView_blank_space,dp2px(5f))
                ta.recycle()
            }
        }

        paint.apply {
            color = lineColor
            isAntiAlias = true
            style = Paint.Style.STROKE
            //绘制长度为lineSpace的实线后再绘制长度为blankSpace的空白区域，0位间隔
            pathEffect = DashPathEffect(floatArrayOf(lineSpace.toFloat(),blankSpace.toFloat()), 0f)
            post {
                if(orientation == 0) {
                    paint.strokeWidth = measuredHeight.toFloat()
                } else {
                    paint.strokeWidth = measuredWidth.toFloat()
                }
            }
        }
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.moveTo(0f, 0f)
        if(orientation == 0) {
            path.lineTo(width.toFloat(), 0f)
        } else {
            path.lineTo(0f, height.toFloat())
        }
        canvas?.drawPath(path, paint)
    }

    private fun dp2px(dp: Float): Int{
        return UIUtils.getSize(dp)
    }
}