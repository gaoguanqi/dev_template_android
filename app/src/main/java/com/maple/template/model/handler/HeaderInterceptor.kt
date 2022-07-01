package com.maple.template.model.handler

import com.maple.baselib.utils.LogUtils
import com.maple.template.utils.HttpRequestUtils
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*
/**
 * 添加公共请求头拦截器
 */
class HeaderInterceptor (private val headers: WeakHashMap<String, Any>?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().run {
            if (!headers.isNullOrEmpty()) {
                val originalRequest: Request = chain.request()
                val originalHeaders: Headers = originalRequest.headers
                headers.put("Authorization", HttpRequestUtils.getAccessToken())
                for (headMap in headers) {
                    //添加统一通用header，不存在则添加，存在则不添加。
                    if (originalHeaders.get(headMap.key) == null) {
                        LogUtils.logGGQ("公共请求参数：${headMap.key}--${headMap.value}")
                        addHeader(headMap.key, headMap.value.toString()).build()
                    }
                }
            }
            build()
        })
    }
}