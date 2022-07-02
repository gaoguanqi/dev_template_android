package com.maple.commonlib.widget.bsdiff

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.widget.dialog.BaseDialogFragment
import com.maple.commonlib.R
import com.maple.commonlib.databinding.DialogDownloadBinding
import com.maple.commonlib.utils.ToastUtils
import com.maple.commonlib.widget.update.UpdateViewModule
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import java.io.File

class DownloadDialog : BaseDialogFragment<DialogDownloadBinding>(
    mGravity = Gravity.TOP,
    mWidth = ScreenUtils.getScreenWidth(),
    mHeight = (ScreenUtils.getScreenHeight() * 0.08f).toInt()){

    private val viewModel by viewModels<UpdateViewModule>()


    override fun getLayoutId(): Int = R.layout.dialog_download

    override fun bindViewModel() {
        super.bindViewModel()
        this.binding.viewModel = viewModel
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showToast(it)
        })

        onDownloadFile("")
    }

    private fun onDownloadFile(downloadUrl: String) {
        if(TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast("无效的下载地址！")
            dismissAllowingStateLoss()
            return
        }
        XUpdate.newBuild(this.requireActivity())
            .apkCacheDir(PathUtils.getExternalDownloadsPath()) //设置下载缓存的根目录
            .build()
            .download(downloadUrl, object : OnFileDownloadListener {
                //设置下载的地址和下载的监听
                override fun onStart() {}

                override fun onProgress(progress: Float, total: Long) {
                    val mProgress =  Math.round(progress * 100)
                    this@DownloadDialog.binding.npbProgress.progress = mProgress
                }

                override fun onCompleted(file: File): Boolean {
                    dismissAllowingStateLoss()
                    return false
                }

                override fun onError(throwable: Throwable) {
                    viewModel.defUI.onToast("增量更新失败！")
                }
            })
    }


    override fun onReset() {
        super.onReset()

    }
}