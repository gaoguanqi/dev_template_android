package com.maple.template.db

import android.text.TextUtils
import com.google.gson.Gson
import com.maple.baselib.utils.LogUtils
import com.tencent.mmkv.MMKV

class DBHelper private constructor(){

    companion object {
        private const val KEY_USER: String = "USER-INFO"


        private val mmkv: MMKV? by lazy { MMKV.defaultMMKV() }
        private val gson: Gson? by lazy { Gson() }

        fun clearAll(){
            mmkv?.clearAll()
        }

        // userInfo
        fun saveUser(user: UserInfo?): Boolean {
            if (null != user) {
                try {
                    return mmkv?.encode(KEY_USER, gson?.toJson(user)) ?: false
                } catch (e: Exception) {
                    e.fillInStackTrace()
                }
            }
            return false
        }


        fun getUser(): UserInfo? {
            val userInfo: String? = mmkv?.getString(KEY_USER, "")
            if (!TextUtils.isEmpty(userInfo)) {
                try {
                    return gson?.fromJson<UserInfo>(userInfo, UserInfo::class.java)
                } catch (e: Exception) {
                    e.fillInStackTrace()
                    LogUtils.logGGQ("--异常-->${e.message}")
                }
            }
            return null
        }


        fun removeUser() {
            mmkv?.remove(KEY_USER)
        }
    }
}