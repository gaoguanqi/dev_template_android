package com.maple.template.model.api

import com.maple.commonlib.app.Global
import com.maple.commonlib.http.resp.BaseResp
import com.maple.template.model.entity.AppInfoEntity
import com.maple.template.model.entity.BannerEntity
import com.maple.template.model.entity.LoginEntity
import com.maple.template.model.entity.RecordPageEntity
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): LoginEntity

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @GET(ApiURL.URL_SYS_BANNER)
    suspend fun getBanner(
        @Query("keyword") keyword: String,
        @Query("type") type: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): BannerEntity

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @GET(ApiURL.URL_USER_RECORD)
    suspend fun getRecordList(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): RecordPageEntity

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @GET(ApiURL.URL_VERSION_APP)
    suspend fun checkVersion(
        @Query("appId") appId: String,
        @Query("appType") appType: String,
    ): AppInfoEntity

    @Headers("${Global.DOMAIN}:${Global.URL_FINAL}")
    @GET(ApiURL.URL_BSDIFF_APP)
    suspend fun checkBsdiff(
        @Query("appId") appId: String,
        @Query("diffCode") diffCode: String,
        @Query("versionCode") versionCode: String,
    ): AppInfoEntity
}