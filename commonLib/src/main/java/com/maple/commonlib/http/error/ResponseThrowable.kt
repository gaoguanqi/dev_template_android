package com.maple.commonlib.http.error

import com.maple.commonlib.http.resp.BaseResp


open class ResponseThrowable : Exception {
    var code: String = ERROR.UNKNOWN.getKey()
    var errMsg: String = ERROR.UNKNOWN.getValue()

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: String, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }

    constructor(base: BaseResp, e: Throwable? = null) : super(e) {
        this.code = base.code
        this.errMsg = base.msg
    }
}

