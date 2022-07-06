package com.maple.baselib.utils

import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.StringUtils

class UIUtils {

    companion object {

        fun getString(@StringRes resId: Int): String {
            return StringUtils.getString(resId)
        }

        fun getColor(@ColorRes resId: Int): Int {
            return ColorUtils.getColor(resId)
        }

        fun getDrawable(@DrawableRes resId: Int): Drawable {
            return ResourceUtils.getDrawable(resId)
        }

        fun getSize(dp: Float): Int{
            return SizeUtils.px2dp(dp)
        }

        fun editTransformation(field: EditText, iv: ImageView, showIcon: Drawable, hideIcon: Drawable) {
            if(field.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                field.transformationMethod = PasswordTransformationMethod.getInstance()
                iv.setImageDrawable(hideIcon)
            } else {
                field.transformationMethod = HideReturnsTransformationMethod.getInstance()
                iv.setImageDrawable(showIcon)
            }
            field.setSelection(field.text.length)
        }

        private var lastClickTime: Long = 0L
        private const val DELAY_TIME: Long = 600L

        fun isFastClick(delay: Long = DELAY_TIME): Boolean {
            val time = System.currentTimeMillis()
            val lastTime = time - lastClickTime
            if (0L < lastTime && lastTime < delay) {
                return true
            }
            lastClickTime = time
            return false
        }
    }
}