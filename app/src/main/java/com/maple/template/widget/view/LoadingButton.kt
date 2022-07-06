package com.maple.template.widget.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.ContentLoadingProgressBar
import com.maple.baselib.ext.layoutInflater
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.UIUtils
import com.maple.template.R

class LoadingButton  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayoutCompat(context, attrs, defStyle){

    private var llLoading: LinearLayout? = null
    private var tvTxt: TextView? = null
    private var progressBar: ContentLoadingProgressBar? = null

    private var txt: String = ""

    private val enableDrawable by lazy {
        UIUtils.getDrawable(R.drawable.shape_common_enable)
    }

    private val activeDrawable by lazy {
        UIUtils.getDrawable(R.drawable.selector_common)
    }

    private val normalDrawable by lazy {
        UIUtils.getDrawable(R.drawable.shape_common_normal)
    }

    private var listener: OnListener? = null

    fun setListener(l: OnListener?) {
        l?.let {
            listener = l
            llLoading?.setOnClickListener {
                if(progressBar?.visibility == View.GONE && llLoading?.background == activeDrawable) {
                    listener?.onStart()
                }
            }
        }
    }

    init {
        val view: View = context.layoutInflater.inflate(R.layout.layout_loading_button,this)
        llLoading = view.findViewById(R.id.ll_loading)
        setEnableState()
        tvTxt = view.findViewById(R.id.tv_txt)
        progressBar = view.findViewById(R.id.progress_bar)
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.LoadingButton)
            typedArray.let { ta ->
                val txt = ta.getString(R.styleable.LoadingButton_txt)
                txt?.let {
                    this.txt = it
                    tvTxt?.text = it
                }
                ta.recycle()
            }
        }
    }


    fun setEnableState() {
        llLoading?.let {
            if(it.background != enableDrawable) {
                it.background = enableDrawable
            }
        }
    }

    private fun setNormalState() {
        llLoading?.let {
            if(it.background != normalDrawable) {
                it.background = normalDrawable
            }
        }
    }

    fun setActiveState() {
        llLoading?.let {
            if(it.background != activeDrawable) {
                it.background = activeDrawable
            }
        }
    }

    fun onLoading() {
        progressBar?.toVisible()
        tvTxt?.text = "请稍后..."
        setNormalState()
    }

    fun onCancel() {
        progressBar?.toGone()
        setEnableState()
        tvTxt?.text = this.txt
    }

    interface OnListener {
        fun onStart()
    }
}