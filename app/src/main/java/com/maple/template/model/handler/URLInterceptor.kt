package com.maple.template.model.handler

import android.text.TextUtils
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.app.Global
import com.maple.template.app.Config
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class URLInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//  配置请求头
        val request = chain.request()
        //从request中获取原有的HttpUrl实例oldHttpUrl
        val oldHttpUrl = request.url
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues:List<String> = request.headers(Global.DOMAIN)
        if (headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(Global.DOMAIN)
            //匹配获得新的BaseUrl
            val headerValue = headerValues.first()
            val newBaseUrl: HttpUrl? =
                if (TextUtils.equals(Global.URL_RELEASE,headerValue)) {
                    Config.BASE_URL.toHttpUrlOrNull()
                } else if(TextUtils.equals(Global.URL_DEV_1,headerValue)) {
                    Config.DEV_URL_1.toHttpUrlOrNull()
                }else{
                    oldHttpUrl
                }

            //重建新的HttpUrl，修改需要修改的url部分
            newBaseUrl?.let {
                val newFullUrl = oldHttpUrl.newBuilder()
                    .scheme(newBaseUrl.scheme) //更换网络协议
                    .host(newBaseUrl.host) //更换主机名
                    .port(newBaseUrl.port) //更换端口
                    .build()
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                LogUtils.logGGQ("URLInterceptor->>${newFullUrl}")
                return chain.proceed(builder.url(newFullUrl).build())
            }
        }
        return chain.proceed(request)
    }
}