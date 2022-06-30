package com.maple.baselib.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.maple.baselib.manager.AppLifeCycleCallBack
import com.maple.baselib.manager.ForebackLifeObserver

abstract class BaseApp: Application(),  ViewModelStoreOwner{

    abstract fun getAppPackageName(): String

    abstract fun initSDK(app:Application)

    companion object {
        @JvmStatic
        lateinit var instance: BaseApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApp()
        initSDK(this)
    }

    open fun initApp(){
        Utils.init(this)
        SPUtils.getInstance(getAppPackageName())
    }

     fun registerLifecycle(callBack: ForebackLifeObserver.Callback) {
        registerActivityLifecycleCallbacks(AppLifeCycleCallBack())
        ProcessLifecycleOwner.get().lifecycle.addObserver(ForebackLifeObserver(callBack))
    }

}