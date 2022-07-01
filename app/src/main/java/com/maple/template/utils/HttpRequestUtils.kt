package com.maple.template.utils

import com.maple.commonlib.app.Const
import com.tencent.mmkv.MMKV
import java.util.*

class HttpRequestUtils {

    companion object{

        private val mmkv: MMKV = MMKV.defaultMMKV()

        fun requestHeader(): WeakHashMap<String, Any> {
            val header: WeakHashMap<String, Any> = WeakHashMap()
            val token = mmkv.getString(Const.SaveInfoKey.ACCESS_TOKEN,"")
            header.put("Authorization", token)
            return header
        }

        fun requestParams(): WeakHashMap<String, Any> {
            val params: WeakHashMap<String, Any> = WeakHashMap()
//            params.put("公共参数key", "公共参数value")
            return params
        }


        fun getAccessToken(): String {
            return mmkv.getString(Const.SaveInfoKey.ACCESS_TOKEN,"")?: ""
        }
    }
}