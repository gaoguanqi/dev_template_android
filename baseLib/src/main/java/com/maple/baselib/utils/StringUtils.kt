package com.maple.baselib.utils

import android.text.TextUtils

class StringUtils {
    companion object {
        @JvmStatic
        fun isNotEmpty(s: String?): Boolean{
            return !TextUtils.isEmpty(s)
        }
    }
}