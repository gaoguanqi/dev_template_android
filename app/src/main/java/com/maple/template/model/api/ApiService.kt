package com.maple.template.model.api

import com.maple.commonlib.app.Global
import com.maple.commonlib.http.resp.BaseResp
import com.maple.template.model.entity.LoginEntity
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): LoginEntity

}