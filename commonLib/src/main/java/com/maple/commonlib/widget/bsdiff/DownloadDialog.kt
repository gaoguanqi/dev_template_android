package com.maple.commonlib.widget.bsdiff

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.widget.dialog.BaseDialogFragment
import com.maple.commonlib.R
import com.maple.commonlib.databinding.DialogDownloadBinding
import com.maple.commonlib.utils.ToastUtils
import com.maple.commonlib.widget.update.UpdateViewModel
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import java.io.File
import com.blankj.utilcode.util.AppUtils

import com.maple.commonlib.utils.PatchUtil

import android.os.Environment
import com.blankj.utilcode.util.SPUtils
import com.maple.commonlib.app.Const


class DownloadDialog : BaseDialogFragment<DialogDownloadBinding>(
    mGravity = Gravity.TOP,
    mWidth = ScreenUtils.getScreenWidth(),
    mHeight = (ScreenUtils.getScreenHeight() * 0.08f).toInt()){

    private val viewModel by viewModels<UpdateViewModel>()


    override fun getLayoutId(): Int = R.layout.dialog_download

    override fun bindViewModel() {
        super.bindViewModel()
        this.binding.viewModel = viewModel
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showToast(it)
        })

        viewModel.infoLiveData.observe(this, Observer {
            it?.let { data ->
                if(data.type != 2) {
                    return@Observer
                }
                onDownloadFile(data.patchUrl)
            }
        })
    }

    private fun onDownloadFile(downloadUrl: String?) {
        if(TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast("无效的下载地址！")
            dismissAllowingStateLoss()
            return
        }

        LogUtils.logGGQ("下载地址--->${downloadUrl}")

        val oldApk = SPUtils.getInstance().getString(Const.SaveInfoKey.APK_PATH_OLD)
        LogUtils.logGGQ("旧的 APK 地址--->${oldApk}")

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
                    val newApkFile = File(PathUtils.getExternalDownloadsPath(),"new.apk")
                    LogUtils.logGGQ("--旧apk-->${File(oldApk).absolutePath}")
                    LogUtils.logGGQ("--新apk-->${newApkFile.absolutePath}")
                    LogUtils.logGGQ("--patch-->${file.absolutePath}")
                    Thread {
                        PatchUtil.patchAPK(
                            File(oldApk).absolutePath,
                            newApkFile.absolutePath,
                            file.absolutePath
                        )
                        //安装APK
                        AppUtils.installApp(newApkFile)
                    }.start()
                    return false
                }

                override fun onError(throwable: Throwable) {
                    ToastUtils.showToast("增量更新失败！")
                    dismissAllowingStateLoss()
                }
            })
    }


    override fun onReset() {
        super.onReset()
    }

}