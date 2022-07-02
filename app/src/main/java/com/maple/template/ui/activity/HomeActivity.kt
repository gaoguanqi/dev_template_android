package com.maple.template.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.blankj.utilcode.util.AppUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.widget.bsdiff.DownloadDialog
import com.maple.commonlib.widget.update.UpdateDialog
import com.maple.template.R

class HomeActivity : BaseActivity() {

    override fun hasStatusBarMode(): Boolean = true

    private val updateDialog: UpdateDialog by lazy {
        UpdateDialog()
    }
    private val downloadDialog: DownloadDialog by lazy {
        DownloadDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initData(savedInstanceState: Bundle?) {
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            updateDialog.showAllowStateLoss(this.supportFragmentManager,"updateDialog")
        }
        findViewById<Button>(R.id.btn_bsdiff).setOnClickListener {
            downloadDialog.showAllowStateLoss(this.supportFragmentManager,"downloadDialog")
        }

        findViewById<TextView>(R.id.tv_version).setText(AppUtils.getAppVersionName())

    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_used_protocol, fitWindow)
    }
}