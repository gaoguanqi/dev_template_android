package com.maple.template.model.repository

import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseRepository
import com.maple.template.model.RetrofitClient
import com.maple.template.utils.HttpRequestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class CommonRepository : BaseRepository() {

    private val retrofitClient = RetrofitClient.service


    override fun getPublicParams(): WeakHashMap<String, Any> {
        return HttpRequestUtils.requestParams()
    }

    suspend fun loginPhone(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        LogUtils.logGGQ("----请求参数----")
        params.forEach {
            LogUtils.logGGQ("${it.key} --- ${it.value}")
        }
        LogUtils.logGGQ("---------------")
        retrofitClient.loginPhone(params2Body(params))
    }

    suspend fun getBanner(keyword: String,type: Int, page: Int, limit: Int) = withContext(Dispatchers.IO) {
        LogUtils.logGGQ("----请求参数----")
        LogUtils.logGGQ("---- ${keyword} ---")
        LogUtils.logGGQ("---- ${type} ---")
        LogUtils.logGGQ("---- ${page} ---")
        LogUtils.logGGQ("---- ${limit} ---")
        LogUtils.logGGQ("---------------")
        retrofitClient.getBanner(keyword,type,page,limit)
    }

    suspend fun getRecordList(keyword: String, page: Int, limit: Int) = withContext(Dispatchers.IO) {
        LogUtils.logGGQ("----请求参数----")
        LogUtils.logGGQ("---- ${keyword} ---")
        LogUtils.logGGQ("---- ${page} ---")
        LogUtils.logGGQ("---- ${limit} ---")
        LogUtils.logGGQ("---------------")
        retrofitClient.getRecordList(keyword,page,limit)
    }

    suspend fun checkVersion(appId: String, appType: String) = withContext(Dispatchers.IO) {
        LogUtils.logGGQ("----请求参数----")
        LogUtils.logGGQ("---- ${appId} ---")
        LogUtils.logGGQ("---- ${appType} ---")
        LogUtils.logGGQ("---------------")
        retrofitClient.checkVersion(appId,appType)
    }

    suspend fun checkBsdiff(appId: String,diffCode: String, versionCode: String) = withContext(Dispatchers.IO) {
        LogUtils.logGGQ("----请求参数----")
        LogUtils.logGGQ("---- ${appId} ---")
        LogUtils.logGGQ("---- ${diffCode} ---")
        LogUtils.logGGQ("---- ${versionCode} ---")
        LogUtils.logGGQ("---------------")
        retrofitClient.checkBsdiff(appId,diffCode,versionCode)
    }
}