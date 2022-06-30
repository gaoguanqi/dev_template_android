package com.maple.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.maple.baselib.app.BaseApp

/**
 * 网络工具类
 */

object NetworkUtil {

    /**
     * 是否有网络
     */
    fun isConnected(): Boolean {
        return isWifiConnection() || isStationConnection()
    }

    /**
     * 是否使用基站联网
     */
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    fun isStationConnection(): Boolean {
        val manager = BaseApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return if (networkInfo != null) {
            networkInfo.isAvailable && networkInfo.isConnected
        } else false
    }

    /**
     * 是否使用WIFI联网
     */
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    fun isWifiConnection(): Boolean {
        val manager = BaseApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (networkInfo != null) {
            networkInfo.isAvailable && networkInfo.isConnected
        } else false
    }


    /**
     * 网络连接类型
     */
    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    fun netWorkType (): NetType {
        val manager = BaseApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?: return NetType.NO_NET
        val activeNetwork = manager.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.isConnected) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    // Logger.v(TAG, "当前WiFi连接可用 ");
                    return NetType.WIFI
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    // Logger.v(TAG, "当前移动网络连接可用 ");
                    return NetType.NET_4G
                }
            } else {
                // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                return NetType.NO_NET
            }
        } else {
            // Logger.v(TAG, "当前没有网络连接，请确保你已经打开网络 ");
            return NetType.NO_NET
        }
        return NetType.NO_NET
    }


    enum class NetType {
        WIFI, NET_4G, NO_NET
    }
}