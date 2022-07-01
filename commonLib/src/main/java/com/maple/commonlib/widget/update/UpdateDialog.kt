package com.maple.commonlib.widget.update

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.widget.dialog.BaseDialogFragment
import com.maple.commonlib.R
import com.maple.commonlib.databinding.DialogUpdateBinding
import com.maple.commonlib.utils.ToastUtils.Companion.showToast
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import java.io.File


// http://192.168.1.84:9000/imchat/app/20220628/i1xkwvpach7murirkyyoc7g9h1hbk14k.apk
class UpdateDialog: BaseDialogFragment<DialogUpdateBinding>(
    mHeight = (ScreenUtils.getScreenHeight() * 0.54f).toInt()){

    private val downloadUrl: String = "http://192.168.1.84:9000/imchat/app/20220628/i1xkwvpach7murirkyyoc7g9h1hbk14k.apk"

    private val viewModel by viewModels<UpdateViewModule>()

    override fun getCancelable(): Boolean = true

    override fun getLayoutId(): Int = R.layout.dialog_update

    override fun bindViewModel() {
        super.bindViewModel()
        this.binding.viewModel = viewModel
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })

        viewModel.ignoreEvent.observe(this, Observer {
            dismissAllowingStateLoss()
        })

        viewModel.updateEvent.observe(this, Observer {
            viewModel.ignoreState.set(false)
            viewModel.updateState.set(false)
            onDownloadApk(downloadUrl)
        })

        viewModel.updateTitle.set("升级到V1.0.1版本")
        viewModel.updateInfo.set("1,修复bug \n" +
                " 2,修复bug \n" +
                " 3,修复bug \n" +
                " 4,修复bug ")
    }

    private fun onDownloadApk(downloadUrl: String) {
        XUpdate.newBuild(this.requireActivity())
            .apkCacheDir(PathUtils.getExternalDownloadsPath()) //设置下载缓存的根目录
            .build()
            .download(downloadUrl, object : OnFileDownloadListener {
                //设置下载的地址和下载的监听
                override fun onStart() {
                    //HProgressDialogUtils.showHorizontalProgressDialog(context, "下载进度", false)
                    viewModel.progressState.set(true)
                }

                override fun onProgress(progress: Float, total: Long) {
                   // HProgressDialogUtils.setProgress(Math.round(progress * 100))
                   val mProgress =  Math.round(progress * 100)
                    this@UpdateDialog.binding.npbProgress.progress = mProgress
                }

                override fun onCompleted(file: File): Boolean {
                   // HProgressDialogUtils.cancel()
                   // ToastUtils.toast("apk下载完毕，文件路径：" + file.getPath())
                    viewModel.progressState.set(false)
                    return false
                }

                override fun onError(throwable: Throwable) {
                   // HProgressDialogUtils.cancel()
                }
            })
    }

    override fun onReset() {
        super.onReset()
        viewModel.updateTitle.set("")
        viewModel.updateInfo.set("")
        viewModel.updateState.set(true)
        viewModel.ignoreState.set(true)
        viewModel.progressState.set(false)
        viewModel.progressValue.set(0)
    }
}