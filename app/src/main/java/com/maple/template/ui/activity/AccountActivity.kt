package com.maple.template.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.navigation.fragment.findNavController
import com.maple.commonlib.app.Const
import com.maple.commonlib.base.BaseActivity
import com.maple.template.R

class AccountActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_account

    override fun initData(savedInstanceState: Bundle?) {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        val navController = navHost?.findNavController()
        navController?.setGraph(R.navigation.navigation_account)
        navController?.navigateUp()
    }

    private val bundleType: String? by lazy {
        this.intent?.getStringExtra(Const.BundleKey.EXTRA_TYPE)
    }

    override fun onFinish() {
        if(TextUtils.equals("mine",bundleType)) {
            setResult(RESULT_OK, Intent().putExtra("result","ok"))
        }
        super.onFinish()
    }

}