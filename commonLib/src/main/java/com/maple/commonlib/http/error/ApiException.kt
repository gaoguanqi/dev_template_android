package com.maple.commonlib.http.error

import java.lang.RuntimeException

class ApiException: RuntimeException {
    constructor(): this("api exception")
    constructor(message:String): super(message)
}