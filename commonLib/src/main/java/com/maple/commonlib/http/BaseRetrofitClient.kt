package com.maple.commonlib.http

import com.maple.commonlib.http.converter.MyGsonConverterFactory
import com.maple.commonlib.http.handler.SSLSocketClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {

    companion object CLIENT {
        private const val TIME_OUT:Long = 10L
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.run {
                //连接时间
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                //读取时间
//                readTimeout(TIME_OUT, TimeUnit.SECONDS)
                //写入时间
//                writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                // 允许重定向
                followRedirects(true)
                // https支持
                SSLSocketClient.getSSLSocketFactory()?.run {
                    sslSocketFactory(sslSocketFactory, trustManager)
                }
                handleBuilder(builder)
            }
            return builder.build()
        }



    /**
     * 以便对builder可以再扩展
     */
    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    open fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(jsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }

    private val jsonConverterFactory by lazy {
//        GsonConverterFactory.create()
        MyGsonConverterFactory.create()
    }
}