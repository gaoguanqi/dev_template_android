package com.maple.commonlib.widget.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.maple.commonlib.R

class EyeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle), View.OnFocusChangeListener, TextWatcher {

    private var eyeShowIcon: Drawable? = null
    private var eyeHideIcon: Drawable? = null

    init {
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.EyeEditText)
            typedArray.let { ta ->
                eyeShowIcon = ta.getDrawable(R.styleable.EyeEditText_eyeShowIcon)
                eyeHideIcon = ta.getDrawable(R.styleable.EyeEditText_eyeHideIcon)
                ta.recycle()
            }
        }

        eyeHideIcon?.let {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, it, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        return super.onTouchEvent(event)
    }


    private fun setEyeIcon(){
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {

    }


}