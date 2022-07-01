package com.maple.template.model.repository

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

    suspend fun login(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.login(params2Body(params))
    }
}