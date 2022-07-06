package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentMineBinding
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

            bd.btnLogin.setOnClickListener {
                onOpenAccount()
            }
            bd.btnLoginSwitch.setOnClickListener {
                onOpenAccount()
            }
        }
    }

    private fun onOpenAccount() {
        onStartActivity(AccountActivity::class.java)
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_toolbar, fitWindow)
    }
}