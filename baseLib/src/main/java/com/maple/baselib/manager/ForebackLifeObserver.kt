package com.maple.baselib.manager

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.maple.baselib.utils.LogUtils

/**
 * 前后台切换的观察者
 */
class ForebackLifeObserver(private val callBack: Callback): DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtils.logGGQ("--前台---")
        callBack.onForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtils.logGGQ("--后台---")
        callBack.onBackstage()
    }

    interface Callback {
        fun onForeground()
        fun onBackstage()
    }
}