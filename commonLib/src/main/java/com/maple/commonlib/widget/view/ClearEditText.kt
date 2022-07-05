package com.maple.commonlib.widget.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.maple.commonlib.R

class ClearEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {

    private var clearNormalIcon: Drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_normal, null) as Drawable

    private var clearPressIcon: Drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_press, null) as Drawable

    private var clearIcon: Drawable = clearNormalIcon

    private var listener: OnClickListener? = null

    fun setListener(listener: OnClickListener?){
        this.listener = listener
    }

    init {
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.ClearEditText)
            typedArray.let { ta ->
                ta.getDrawable(R.styleable.ClearEditText_clearIcon)?.let {
                    clearNormalIcon = it
                    clearIcon = clearNormalIcon
                }
                ta.getDrawable(R.styleable.ClearEditText_clearPressIcon)?.let {
                    clearPressIcon = it
                }
                ta.recycle()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (isClearButtonVisible() && wasClearButtonTouched(it)) {
                onClearButtonTouched(it)
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    private fun showClearIcon() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearIcon, null)
    }

    private fun hideClearIcon() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }


    private fun isClearButtonVisible(): Boolean {
        return compoundDrawablesRelative[2] != null
    }

    private fun wasClearButtonTouched(event: MotionEvent): Boolean {
        val isClearButtonAtTheStart = layoutDirection == View.LAYOUT_DIRECTION_RTL

        return if (isClearButtonAtTheStart) {
            val clearButtonEnd = paddingStart + clearIcon.intrinsicWidth
            event.x < clearButtonEnd
        } else {
            val clearButtonStart = width - clearIcon.intrinsicWidth - paddingEnd
            event.x > clearButtonStart

        }
    }

    private fun onClearButtonTouched(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clearIcon = clearPressIcon
                showClearIcon()
            }
            MotionEvent.ACTION_UP -> {
                clearIcon = clearNormalIcon
                text?.clear()
                hideClearIcon()
                listener?.onClearClick()
            }
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (text?.isNotEmpty() == true) {
            showClearIcon()
        } else {
            hideClearIcon()
        }
    }

    interface OnClickListener{
        fun onClearClick()
    }

}