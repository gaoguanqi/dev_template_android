package com.maple.template.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.AppUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.widget.update.InfoData
import com.maple.commonlib.widget.update.UpdateDialog
import com.maple.commonlib.widget.update.UpdateViewModel
import com.maple.template.R
import com.maple.template.vm.AboutViewModel

class AboutActivity : BaseActivity() {

    override fun hasStatusBarMode(): Boolean = true

    private val viewModel by viewModels<AboutViewModel>()

    private val updateViewModel by viewModels<UpdateViewModel>()


    private var tvVersionName: TextView? = null

    private val updateDialog: UpdateDialog by lazy {
        UpdateDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_about

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<TextView>(R.id.tv_title_center)?.text = "关于"
        findViewById<ImageButton>(R.id.ibtn_title_left)?.setOnClickListener {
            this.onFinish()
        }
        tvVersionName = findViewById(R.id.tv_version_name)
        tvVersionName?.apply {
            this.text = "V ${AppUtils.getAppVersionName()}"
            this.setOnClickListener {
                viewModel.checkVersion()
            }
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

        viewModel.appInfoLiveData.observe(this, Observer {
            LogUtils.logGGQ("---->>${it.downloadUrl}")
            val infoData = InfoData(type = 1,title = "是否升级到:${it.versionName}",content = it.updateContent,downloadUrl = it.downloadUrl,isIgnore = it.ignorable)
            updateViewModel.infoLiveData.postValue(infoData)
            updateDialog.showAllowStateLoss(this@AboutActivity.supportFragmentManager,"updateDialog")
        })
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_toolbar, true)
    }
}