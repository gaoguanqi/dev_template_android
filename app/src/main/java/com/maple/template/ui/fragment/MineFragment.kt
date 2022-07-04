package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.google.android.material.appbar.AppBarLayout
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentMineBinding
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

    private val showAnimation: AlphaAnimation by lazy {
        AlphaAnimation(0.0f,1.0f).apply {
            this.duration = 500
            this.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.llUser.toVisible()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    private val hideAnimation: AlphaAnimation by lazy {
        AlphaAnimation(1.0f,0.0f).apply {
            this.duration = 500
            this.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {

                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        this.binding.let {
            it.appBarLayout.addOnOffsetChangedListener(object :
                AppBarLayout.OnOffsetChangedListener {
                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (verticalOffset == 0) {
                        //展开
                        it.llUser.startAnimation(showAnimation)
                        binding.llUser.toVisible()
                    } else if (Math.abs(verticalOffset) >= binding.appBarLayout.totalScrollRange) {
                        //折叠
                        it.llUser.startAnimation(hideAnimation)
                        binding.llUser.toGone()
                    } else { //中间

                    }
                }
            })
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_toolbar, fitWindow)
    }
}