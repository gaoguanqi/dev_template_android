package com.maple.commonlib.widget.snackbar

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.R


/**
 * An animated snack bar that can be auto-hidden after
 * [autoHideDelayMillis] milliseconds.
 *
 */
class AnimatedSnackbar(context: Context, attrs : AttributeSet? = null) : LinearLayout(context, attrs) {

    private var parentLayout: FrameLayout
    private var imageView: ImageView
    private var textView: TextView

    private var iconDrawable : Drawable? = null
    private var iconTint = Color.TRANSPARENT
    private var message = ""
    private var textTint = Color.WHITE
    private var bgDrawable : Drawable? = null
    private var typeface : Typeface? = null
    private var textSize : Float? = null


    /**
     * Whether or not the bar UI should be auto-hidden after
     * [autoHideDelayMillis] milliseconds.
     */
    private var autoHide = true
    /**
     * If [autoHide] is set, the number of milliseconds to wait before hiding the bar UI.
     */
    private var autoHideDelayMillis : Int = 2000
    private var animationDurationMillis : Long = 300


    init {
        iconDrawable = UIUtils.getDrawable(R.mipmap.ic_launcher)
    }

    fun setIconDrawable(iconDrawable : Drawable?, iconTint : Int = Color.TRANSPARENT): AnimatedSnackbar {
        if(iconDrawable != null) {
            this.iconDrawable = iconDrawable
            imageView.setImageDrawable(iconDrawable)
        }
        this.iconTint = iconTint
        imageView.setColorFilter(iconTint)
        return this
    }

    fun setMessage(message : String, textTint : Int = Color.WHITE): AnimatedSnackbar {
        this.message = message
        textView.text = message

        this.textTint = textTint
        textView.textSize
        textView.setTextColor(textTint)
        return this
    }

    fun setTypeFace(typeface: Typeface){
        this.typeface = typeface
        textView.typeface = typeface
    }

    fun setTextSize(textSize: Float){
        this.textSize = textSize
        textView.textSize = textSize
    }

    fun setBgDrawable(bgDrawable : Drawable?): AnimatedSnackbar {
        if(bgDrawable != null) {
            this.bgDrawable = bgDrawable
            parentLayout.background = bgDrawable
        }
        return this
    }

    fun setAutoHide(autoHide: Boolean, autoHideDelayMillis: Int = 2000){
        this.autoHide = autoHide
        this.autoHideDelayMillis = autoHideDelayMillis
    }

    fun setAnimationDurationMillis(animationDuration : Long){
        this.animationDurationMillis = animationDuration
    }

    private val mHideHandler = Handler(Looper.getMainLooper())
    private val mHideRunnable = Runnable { hide() }
    private var hidden = true

    private val activity: Activity? = (context as? Activity)

    init {
        val view: View = inflate(context, R.layout.snackbar_view_full, this)
//        val view: View = inflate(context, R.layout.snackbar_view_shape, this)
        this.visibility = View.INVISIBLE
        parentLayout = view.findViewById(R.id.parent_layout)
        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)

    }


    private fun prepareSnackbar(){
        // Attach to parent view
        addViewToParent()

        // Add status bar padding
        if(activity != null){
            val rectangle = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(rectangle)
            val statusBarHeight = rectangle.top
            parentLayout.setPadding(0,statusBarHeight,0,0)
        }
    }

    private fun addViewToParent(){
        if(parent != null) return
        val decorView = activity?.window?.decorView as? ViewGroup
        decorView?.addView(this)
    }

    fun show(){
        prepareSnackbar()
        performWhenHeightIsReady {showSnackbar()}
    }

    private fun showSnackbar() : Boolean{
//        if(this.visibility == View.VISIBLE) return false
        hidden = false
        this.visibility = View.VISIBLE
        (bgDrawable as? AnimationDrawable)?.start()
        this.y = -parentLayout.measuredHeight.toFloat()

        this.animate().y(0.0f).setDuration(animationDurationMillis).withLayer()
        mHideHandler.removeCallbacks(mHideRunnable)
        if(autoHide) delayedHide(autoHideDelayMillis)
        return true
    }

    fun hide() : Boolean{
        if(hidden) return false
        hidden = true
        this.animate().y(-parentLayout.height.toFloat()).setDuration(animationDurationMillis).withLayer().withEndAction {
            this.visibility = View.INVISIBLE
            (bgDrawable as? AnimationDrawable)?.stop()
            (parent as? ViewGroup)?.removeView(this@AnimatedSnackbar)
        }

        mHideHandler.removeCallbacks(mHideRunnable)
        return true
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }
}

//Extension to get height in Kotlin dynamically (waits for it to be calculated in case not ready).
fun <T : View> T.performWhenHeightIsReady(function: () -> Unit) {
    if (height == 0)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                function()
            }
        })
    else function()
}