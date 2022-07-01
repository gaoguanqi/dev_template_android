package com.maple.template.model

import com.maple.commonlib.http.BaseRetrofitClient
import com.maple.template.app.Config
import com.maple.template.model.api.ApiService
import com.maple.template.model.handler.URLInterceptor
import com.maple.template.utils.HttpRequestUtils
import com.maple.template.model.handler.HeaderInterceptor
import okhttp3.OkHttpClient

object RetrofitClient: BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, Config.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(URLInterceptor())
        builder.addInterceptor(HeaderInterceptor(HttpRequestUtils.requestHeader()))
    }


}