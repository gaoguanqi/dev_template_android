package com.maple.commonlib.http.error

enum class ERROR(private val code: String, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN("1000", "未知错误"),
    /**
     * 解析错误
     */
    PARSE_ERROR("2000", "解析错误"),
    /**
     * 网络错误
     */
    NETWORD_ERROR("3000", "网络错误"),
    /**
     * 协议出错
     */
    HTTP_ERROR("4000", "协议出错"),
    /**
     * 连接超时
     */
    TIMEOUT_ERROR("5000", "连接超时"),
    /**
     * 证书出错
     */
    SSL_ERROR("6000", "证书出错"),
    /**
     * 数据异常
     */
    DATA_ERROR("7000", "数据异常");

    fun getValue(): String {
        return err
    }

    fun getKey(): String {
        return code
    }

}