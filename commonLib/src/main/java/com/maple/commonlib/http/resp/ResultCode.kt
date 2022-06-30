package com.maple.commonlib.http.resp

import com.maple.commonlib.app.Global


object ResultCode {

    fun isSuccess (code: String): Boolean{
        return code == Global.SUCCESS_CODE
    }
}