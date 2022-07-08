package com.maple.commonlib.app

import com.kongqw.network.monitor.NetworkMonitorManager
import com.maple.baselib.app.BaseApp
import com.maple.baselib.manager.ForebackLifeObserver
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.R
import com.maple.commonlib.widget.update.OKHttpUpdateHttpService
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION

import com.xuexiang.xupdate.XUpdate




abstract class CommonApp: BaseApp() {

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.common_white, R.color.common_primary)
            MaterialHeader(context).setProgressBackgroundColorSchemeColor(UIUtils.getColor(R.color.white)).setColorSchemeResources(R.color.common_refresh_1, R.color.common_refresh_2, R.color.common_refresh_3)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(context).setDrawableSize(14f).setTextSizeTitle(14f).setAccentColor(
            UIUtils.getColor(R.color.common_gray)) }
    }

    companion object {
        @JvmStatic
        lateinit var instance: CommonApp
            private set
    }


    override fun initApp() {
        super.initApp()
        instance = this
        NetworkMonitorManager.getInstance().init(this)

        registerLifecycle(object : ForebackLifeObserver.Callback{
            override fun onForeground() {
                LogUtils.logGGQ("onForeground---->>>")
            }

            override fun onBackstage() {
                LogUtils.logGGQ("onBackstage---->>>")
            }
        })

        initXUpdate()
    }

    private fun initXUpdate() {
        XUpdate.get()
            .debug(false)
            .isWifiOnly(false) //默认设置只在wifi下检查版本更新
            .isGet(true) //默认设置使用get请求检查版本
            .isAutoMode(false) //默认设置非自动模式，可根据具体使用配置
//            .param("versionCode", UpdateUtils.getVersionCode(this)) //设置默认公共请求参数
//            .param("appKey", packageName)
            .setOnUpdateFailureListener { error ->
                //设置版本更新出错的监听
                if (error.code != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                    LogUtils.logGGQ("版本更新出错-->>>${error.toString()}")
                }
            }
            .supportSilentInstall(true) //设置是否支持静默安装，默认是true
            .setIUpdateHttpService(OKHttpUpdateHttpService()) //这个必须设置！实现网络请求功能。
            .init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        LogUtils.logGGQ("--APP-->>onTerminate")
    }
}