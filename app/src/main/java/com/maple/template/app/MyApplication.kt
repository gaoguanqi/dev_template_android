package com.maple.template.app

import android.app.Application
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelStore
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import com.maple.commonlib.app.CommonApp
import com.maple.template.R
import com.tencent.mmkv.MMKV
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

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
        initCoil(this)
    }

    private fun initCoil(application: Application) {
        Coil.setImageLoader(
            ImageLoader.Builder(application)
                .placeholder(ActivityCompat.getDrawable(application, R.drawable.pic_default_placeholder)) //占位符
                .error(ActivityCompat.getDrawable(application, R.drawable.pic_default_error)) //错误图
                .memoryCachePolicy(CachePolicy.ENABLED) //开启内存缓存
                .callFactory(createOkHttp(application)) //主动构造 OkHttpClient 实例
                .build()
        )
    }

    private fun createOkHttp(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(createDefaultCache(application))
            .build()
    }

    private val cacheMaxSize: Long = 10 * 1024 * 1024

    private fun createDefaultCache(application: Application): Cache {
        val cacheDirectory = getDefaultCacheDirectory(application)
        return Cache(cacheDirectory, cacheMaxSize)
    }

    private fun getDefaultCacheDirectory(application: Application): File {
        return File(application.cacheDir, "image_cache").apply { mkdirs() }
    }

    override fun getViewModelStore(): ViewModelStore = ViewModelStore()
}