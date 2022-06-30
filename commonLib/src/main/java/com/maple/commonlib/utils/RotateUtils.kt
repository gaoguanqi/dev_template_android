package com.maple.commonlib.utils

import android.view.View

class RotateUtils {
    companion object {
        fun rotateView(v: View, rotation: Float = 180f,duration: Long = 300L) {
            v.animate().setDuration(duration).rotation(if(v.rotation == rotation) 0f else rotation).start();
        }
    }
}