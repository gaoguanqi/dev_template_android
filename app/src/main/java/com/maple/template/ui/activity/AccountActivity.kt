package com.maple.template.ui.activity

import android.os.Bundle
import androidx.navigation.fragment.findNavController
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

}