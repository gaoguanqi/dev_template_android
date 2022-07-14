package com.maple.template.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.maple.commonlib.base.BaseActivity
import com.maple.template.R
import com.maple.template.vm.SettingViewModel

class SettingActivity : BaseActivity() {


    override fun hasStatusBarMode(): Boolean = true

    private val viewModel by viewModels<SettingViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<TextView>(R.id.tv_title_center)?.text = "设置"
        findViewById<ImageButton>(R.id.ibtn_title_left)?.setOnClickListener {
            this.onFinish()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_toolbar, true)
    }

}