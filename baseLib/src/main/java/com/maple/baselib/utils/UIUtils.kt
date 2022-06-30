package com.maple.baselib.utils

import android.graphics.drawable.Drawable
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