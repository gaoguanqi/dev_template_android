package com.maple.commonlib.widget.update

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.widget.dialog.BaseDialogFragment
import com.maple.commonlib.R
import com.maple.commonlib.app.Const
import com.maple.commonlib.databinding.DialogUpdateBinding
import com.maple.commonlib.utils.ToastUtils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate._XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import java.io.File

// http://192.168.1.84:9000/imchat/app/20220628/i1xkwvpach7murirkyyoc7g9h1hbk14k.apk
class UpdateDialog: BaseDialogFragment<DialogUpdateBinding>(
    mHeight = (ScreenUtils.getScreenHeight() * 0.54f).toInt()){

    override fun getCancelable(): Boolean = true

    override fun getLayoutId(): Int = R.layout.dialog_update

    private val viewModel by viewModels<UpdateViewModel>()

    override fun bindViewModel() {
        super.bindViewModel()
        this.binding.viewModel = viewModel
    }


    override fun initFragment(view: View, savedInstanceState: Bundle?) {
        viewModel.defUI.toastEvent.observe(this, Observer {
            ToastUtils.showToast("无效的下载地址！")
        })

        viewModel.infoLiveData.observe(this, Observer {
            it?.let { data ->
                if(data.type != 1) {
                    return@Observer
                }
                if(data.isIgnore) binding.tvIgnore.toVisible() else binding.tvIgnore.toGone()
                binding.tvUpdateTitle.text = data.title
                binding.tvUpdateInfo.text = data.content
                binding.btnUpdate.toVisible()
            }
        })

        binding.let { bd ->
            bd.tvIgnore.setOnClickListener {
                this.dismissAllowingStateLoss()
            }
            bd.btnUpdate.setOnClickListener {
                onDownloadApk(viewModel.infoLiveData.value?.downloadUrl)
            }
        }
    }

    private fun onDownloadApk(downloadUrl: String?) {
        if(TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast("无效的下载地址！")
            dismissAllowingStateLoss()
            return
        }
        binding.btnUpdate.toGone()
        binding.tvIgnore.toGone()
        LogUtils.logGGQ("下载地址--->${downloadUrl}")
        XUpdate.newBuild(this.requireActivity())
            .apkCacheDir(PathUtils.getExternalDownloadsPath()) //设置下载缓存的根目录
            .build()
            .download(downloadUrl, object : OnFileDownloadListener {
                //设置下载的地址和下载的监听
                override fun onStart() {
                    this@UpdateDialog.binding.npbProgress.toVisible()
                }

                override fun onProgress(progress: Float, total: Long) {
                   val mProgress =  Math.round(progress * 100)
                    this@UpdateDialog.binding.npbProgress.progress = mProgress
                }

                override fun onCompleted(file: File): Boolean {
                    dismissAllowingStateLoss()
                    if(FileUtils.isFile(file)) {
                        LogUtils.logGGQ("==apk==>>>${file.path}")
                        SPUtils.getInstance().put(Const.SaveInfoKey.APK_PATH_OLD,file.path)
                        _XUpdate.startInstallApk(requireContext(),file)
                        return true
                    }
                    return false
                }

                override fun onError(throwable: Throwable) {
                    ToastUtils.showToast("版本更新失败！")
                    dismissAllowingStateLoss()
                }
            })
    }

    override fun onReset() {
        super.onReset()
        binding.tvIgnore.toGone()
        binding.tvUpdateTitle.text = ""
        binding.tvUpdateInfo.text = ""
        binding.npbProgress.progress = 0
        binding.npbProgress.toGone()
        binding.btnUpdate.toGone()
    }


}