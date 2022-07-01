package com.maple.template.ui.activity

import android.os.Bundle
import com.maple.commonlib.base.BaseActivity
import com.maple.template.R

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
//        onStartActivity(HomeActivity::class.java,isFinish = true)
    }

}