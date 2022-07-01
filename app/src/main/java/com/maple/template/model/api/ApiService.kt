package com.maple.template.model.api

import com.maple.commonlib.app.Global
import com.maple.commonlib.http.resp.BaseResp
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun login(@Body requestBody: RequestBody): BaseResp

}