package com.maple.commonlib.utils

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.maple.commonlib.widget.snackbar.AnimatedSnackbar

class ToastUtils {

    companion object {
        @JvmStatic
        fun showToast(s: String?) {
            ToastUtils.showShort(s)
        }

        @JvmStatic
        fun showSnackBar (context: Context?, s: String?) {
            if(null != context && null != s){
                AnimatedSnackbar(context)
                    .setMessage(s)
                    .show()
            }
        }
    }
}