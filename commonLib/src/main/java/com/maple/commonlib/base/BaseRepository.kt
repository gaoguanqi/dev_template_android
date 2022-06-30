package com.maple.commonlib.base
import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.utils.LogUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*
import com.maple.baselib.base.BaseRepository as B

abstract class BaseRepository: B(){

    fun params2Body(params: WeakHashMap<String, Any>, hasToken: Boolean = true): RequestBody {
        //添加公共请求参数
        if(hasToken){
            params.putAll(getPublicParams())
        }
        LogUtils.logGGQ("-------参数明细---------")
        params.forEach {
            LogUtils.logGGQ("提交参数：：->>>${it.key} = ${it.value}")
        }
        LogUtils.logGGQ("----共：${params.size}----")
        LogUtils.logGGQ("--json:-->${GsonUtils.toJson(params)}")
        return JSONObject(params.toMap()).toString().toRequestBody("application/json;charset=utf-8".toMediaType())
    }


    inline fun <reified T>params2Entity(params: WeakHashMap<String, Any>,hasToken: Boolean = true): T {
        //添加公共请求参数
        if(hasToken){
            params.putAll(getPublicParams())
        }
        LogUtils.logGGQ("-------参数明细---------")
        params.forEach {
            LogUtils.logGGQ("提交参数：：->>>${it.key} = ${it.value}")
        }
        LogUtils.logGGQ("----共：${params.size}----")
        LogUtils.logGGQ("--json:-->${GsonUtils.toJson(params)}")
        return GsonUtils.fromJson<T>(params.toString(),T::class.java)
    }
}