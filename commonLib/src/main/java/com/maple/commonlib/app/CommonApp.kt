package com.maple.commonlib.app

import android.os.Message
import com.kongqw.network.monitor.NetworkMonitorManager
import com.maple.baselib.app.BaseApp
import com.maple.baselib.manager.ForebackLifeObserver
import com.maple.baselib.utils.LogUtils

abstract class CommonApp: BaseApp() {

    companion object {
        @JvmStatic
        lateinit var instance: CommonApp
            private set
    }


    override fun initApp() {
        super.initApp()
        instance = this
        NetworkMonitorManager.getInstance().init(this)

        registerLifecycle(object : ForebackLifeObserver.Callback{
            override fun onForeground() {
                LogUtils.logGGQ("onForeground---->>>")
            }

            override fun onBackstage() {
                LogUtils.logGGQ("onBackstage---->>>")
            }
        })
    }

    override fun onTerminate() {
        super.onTerminate()
        LogUtils.logGGQ("--APP-->>onTerminate")
    }
}