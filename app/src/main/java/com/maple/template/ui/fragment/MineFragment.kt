package com.maple.template.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.DhcpInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.google.android.material.appbar.AppBarLayout
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.StringUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.app.Const
import com.maple.commonlib.base.BaseViewFragment
import com.maple.commonlib.ext.load
import com.maple.commonlib.ext.loadCircle
import com.maple.commonlib.widget.bsdiff.DownloadDialog
import com.maple.commonlib.widget.dialog.CommonDialog
import com.maple.commonlib.widget.update.InfoData
import com.maple.commonlib.widget.update.UpdateViewModel
import com.maple.template.R
import com.maple.template.databinding.FragmentMineBinding
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo
import com.maple.template.ui.activity.AboutActivity
import com.maple.template.ui.activity.AccountActivity
import com.maple.template.ui.activity.SettingActivity
import com.maple.template.vm.HomeViewModel

class MineFragment : BaseViewFragment<FragmentMineBinding, HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }

    override fun hasStatusBarMode(): Boolean = true

    private val logoutDialog: CommonDialog by lazy {
        CommonDialog(requireContext(),
            title = UIUtils.getString(R.string.logout_title),
            content = UIUtils.getString(R.string.logout_content),
            cancel = UIUtils.getString(R.string.logout_cancel),
            confirm = UIUtils.getString(R.string.logout_confirm),
            listener = object : CommonDialog.OnClickListener {
                override fun onConfirmClick() {
                    logoutDialog.onCancel()
                    viewModel.userInfoLiveData.value = null
                    DBHelper.clearAll()
                    setUserInfoStateView(false)
                }
            }
        )
    }


    private val viewModel by viewModels<HomeViewModel>()

    private val updateViewModel by viewModels<UpdateViewModel>()


    override fun getLayoutId(): Int = R.layout.fragment_mine

    private val downloadDialog: DownloadDialog by lazy {
        DownloadDialog()
    }

    private var isAnimShow: Boolean = false
    private val showAnimation by lazy {
        AnimationUtils.loadAnimation(requireContext(),R.anim.zoom_show).apply {
            this.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                    isAnimShow = true
                }

                override fun onAnimationEnd(animation: Animation?) {
                    isAnimShow = false
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        }
    }

    private var isAnimHide: Boolean = false
    private val hideAnimation by lazy {
        AnimationUtils.loadAnimation(requireContext(),R.anim.zoom_hide).apply {
            this.setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                    isAnimHide = true
                }

                override fun onAnimationEnd(animation: Animation?) {
                    isAnimHide = false
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
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

        viewModel.userInfoLiveData.value?.let {
            setUserInfoStateView(true)
        }?: let {
            setUserInfoStateView(false)
        }

        viewModel.appInfoLiveData.observe(this, Observer {
            val infoData = InfoData(type = 2,patchUrl = it.downloadUrl)
            updateViewModel.infoLiveData.postValue(infoData)
           downloadDialog.showAllowStateLoss(this.childFragmentManager,"downloadDialog")
        })

        this.binding.let { bd ->
            bd.appBarLayout.addOnOffsetChangedListener(object :
                AppBarLayout.OnOffsetChangedListener {
                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (verticalOffset == 0) {
                        //展开
                        if(!isAnimShow) {
                            bd.llUser.startAnimation(showAnimation)
                        }
                        bd.llUser.toVisible()
                    } else if (Math.abs(verticalOffset) == (binding.appBarLayout.totalScrollRange)) {
                        //折叠
                        if(!isAnimHide) {
                            bd.llUser.startAnimation(hideAnimation)
                        }
                        bd.llUser.toGone()
                    } else {
                        //中间
                    }
                }
            })
            bd.clBsdiff.setOnClickListener {
//                showToast("增量更新")
                //
                if(TextUtils.isEmpty(SPUtils.getInstance().getString(Const.SaveInfoKey.APK_PATH_OLD))) {
                    showToast("无效的旧版本")
                    return@setOnClickListener
                }
                viewModel.checkBsdiff()
            }

            bd.clSetting.setOnClickListener {
                viewModel.userInfoLiveData.value?.let {
                    onStartActivity(SettingActivity::class.java)
//                    showToast("设置")
                }?:let {
                    showToast("请登录")
                }
            }
            bd.clAbout.setOnClickListener {
                onStartActivity(AboutActivity::class.java)
            }

            bd.btnUnlogin.setOnClickListener {
                onOpenAccount()
            }
            bd.btnLogin.setOnClickListener {
                onOpenAccount()
            }
            bd.llUser.setOnClickListener {
                showToast("用户详情页")
            }
            bd.btnLogout.setOnClickListener {
                logoutDialog.show()
            }
        }
    }

    private fun onOpenAccount() {
        launcherResult.launch("mine")
    }

    private val launcherResult = registerForActivityResult(object :
        ActivityResultContract<String, String>() {
        override fun createIntent(context: Context, type: String): Intent {
            return Intent(requireContext(),AccountActivity::class.java).apply {
                putExtra(Const.BundleKey.EXTRA_TYPE,type)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            val data =  intent?.getStringExtra("result")?: ""
            return if(resultCode == RESULT_OK) data else ""
        }
    }) {
        it?.let { result ->
            LogUtils.logGGQ("--result-->${result}")
            viewModel.getUserInfo()
            setUserInfoStateView(viewModel.userInfoLiveData.value != null)
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(color, fitWindow)
    }
    
    private fun setUserInfoStateView(isUserInfo: Boolean) {
        this.binding.apply {
            if(isUserInfo) {
                this.llInfo.toVisible()
                this.btnUnlogin.toGone()
                this.btnLogin.toGone()
                this.btnLogout.toVisible()
                val avatarUrl: String = viewModel?.userInfoLiveData?.value?.avatarUrl?:""
                LogUtils.logGGQ("==avatarUrl===>>${avatarUrl}")
                if(StringUtils.isNotEmpty(avatarUrl)) {
                    this.ivAvatar.loadCircle(avatarUrl)
                }
            } else {
                this.btnUnlogin.toVisible()
                this.llInfo.toGone()
                this.btnLogin.toVisible()
                this.btnLogout.toGone()
                this.ivAvatar.load(R.drawable.pic_default_avatar)
            }
        }
    }
}