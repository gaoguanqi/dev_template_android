package com.maple.template.app

import android.app.Application
import androidx.lifecycle.ViewModelStore
import com.maple.commonlib.app.CommonApp
import com.tencent.mmkv.MMKV

class MyApplication : CommonApp() {


    companion object {

        @JvmStatic
        lateinit var instance: MyApplication
            private set
    }


    override fun initApp() {
        super.initApp()
        instance = this
    }

    override fun getAppPackageName(): String = this.packageName


    override fun initSDK(app: Application) {
        MMKV.initialize(this)
    }

    override fun getViewModelStore(): ViewModelStore = ViewModelStore()
}