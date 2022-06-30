package com.maple.baselib.manager

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver

// 加入生命周期
open class LifecycleHandler(private val lifecycleOwner: LifecycleOwner) : Handler(Looper.getMainLooper()), DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        removeCallbacksAndMessages(null)
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}