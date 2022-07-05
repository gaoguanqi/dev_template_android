package com.maple.template.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SPUtils
import com.maple.commonlib.app.Const
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.utils.PermissionUtil
import com.maple.commonlib.utils.RequestPermission
import com.maple.template.R
import com.tbruyelle.rxpermissions3.RxPermissions

class SplashActivity : BaseActivity() {

    private val isHome: Boolean by lazy {
        SPUtils.getInstance().getBoolean(Const.SaveInfoKey.HAS_APP_FIRST)
    }

    private val rxPermissions: RxPermissions by lazy { RxPermissions(this) }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        applyPermissions()
    }

    override fun onRestart() {
        super.onRestart()
        applyPermissions()
    }

    private fun applyPermissions() {
        PermissionUtil.applyPermissions(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                launchTarget()
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                launchSettings()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                launchSettings()
            }
        }, rxPermissions)
    }

    private fun launchTarget() {
//        if(isHome) {
//            onStartActivity(HomeActivity::class.java,isFinish = true)
//        } else {
//            onStartActivity(WelcomeActivity::class.java,isFinish = true)
//        }

        onStartActivity(AccountActivity::class.java,isFinish = true)
    }

    private fun launchSettings() {
        PermissionUtils.launchAppDetailsSettings()
    }
}