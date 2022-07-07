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
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.app.Const
import com.maple.commonlib.base.BaseViewFragment
import com.maple.commonlib.widget.dialog.CommonDialog
import com.maple.template.R
import com.maple.template.databinding.FragmentMineBinding
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo
import com.maple.template.ui.activity.AccountActivity
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

    override fun getLayoutId(): Int = R.layout.fragment_mine

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
            if(TextUtils.equals("ok",result)) {
                viewModel.getUserInfo()
                setUserInfoStateView(true)
            }
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_toolbar, fitWindow)
    }
    
    private fun setUserInfoStateView(isUserInfo: Boolean) {
        this.binding.apply {
            if(isUserInfo) {
                this.llInfo.toVisible()
                this.btnUnlogin.toGone()
                this.btnLogin.toGone()
                this.btnLogout.toVisible()
            } else {
                this.btnUnlogin.toVisible()
                this.llInfo.toGone()
                this.btnLogin.toVisible()
                this.btnLogout.toGone()
            }
        }
    }
}