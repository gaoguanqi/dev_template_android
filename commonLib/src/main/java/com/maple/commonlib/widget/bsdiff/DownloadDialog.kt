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




class DownloadDialog : BaseDialogFragment<DialogDownloadBinding>(
    mGravity = Gravity.TOP,
    mWidth = ScreenUtils.getScreenWidth(),
    mHeight = (ScreenUtils.getScreenHeight() * 0.08f).toInt()){

    private val viewModel by viewModels<UpdateViewModel>()

    private var downloadUrl: String? = null

    override fun getLayoutId(): Int = R.layout.dialog_download

    override fun bindViewModel() {
        super.bindViewModel()
        this.binding.viewModel = viewModel
    }

    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showToast(it)
        })

        onDownloadFile(downloadUrl)
    }

    private fun onDownloadFile(downloadUrl: String?) {
        if(TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast("无效的下载地址！")
            dismissAllowingStateLoss()
            return
        }
        LogUtils.logGGQ("下载地址--->${downloadUrl}")
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

//                    new Thread(() -> {
//                        File oldApkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "old.apk");
//                        File newApkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "new.apk");
//                        File patchFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "patch");
//                        PatchUtil.patchAPK(oldApkFile.getAbsolutePath(),newApkFile.getAbsolutePath(),patchFile.getAbsolutePath());
//                        //安装APK
//                        AppUtils.installApp(newApkFile);
//                    }).start();

//                    Thread {
//                        PatchUtil.patchAPK(
//                            oldApkFile.absolutePath,
//                            newApkFile.absolutePath,
//                            patchFile.absolutePath
//                        )
//                        //安装APK
//                        AppUtils.installApp(newApkFile)
//                    }.start()


                    return false
                }

                override fun onError(throwable: Throwable) {
                    viewModel.defUI.onToast("增量更新失败！")
                    dismissAllowingStateLoss()
                }
            })
    }


    override fun onReset() {
        super.onReset()

    }

    fun setDownloadUrl(url: String?) {
        this.downloadUrl = url
    }
}