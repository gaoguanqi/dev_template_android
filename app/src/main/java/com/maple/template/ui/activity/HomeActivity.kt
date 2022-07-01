package com.maple.template.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.blankj.utilcode.util.AppUtils
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.widget.update.UpdateDialog
import com.maple.template.R

class HomeActivity : BaseActivity() {
    private val updateDialog: UpdateDialog by lazy {
        UpdateDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initData(savedInstanceState: Bundle?) {
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            updateDialog.showAllowStateLoss(this.supportFragmentManager,"updateDialog")
        }

        findViewById<TextView>(R.id.tv_version).setText(AppUtils.getAppVersionName())

    }
}