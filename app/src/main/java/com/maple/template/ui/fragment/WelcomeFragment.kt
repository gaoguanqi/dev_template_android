package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.blankj.utilcode.util.SPUtils
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.app.Const
import com.maple.commonlib.base.BaseFragment
import com.maple.commonlib.utils.ToastUtils
import com.maple.template.R
import com.maple.template.ui.activity.HomeActivity

class WelcomeFragment(val index: Int): BaseFragment(){

    private var ivPic: ImageView? = null
    private var btnLaunch: Button? = null

    companion object {
        @JvmStatic
        fun getInstance(index: Int): WelcomeFragment {
            return WelcomeFragment(index)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_welcome

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        ivPic = view.findViewById(R.id.iv_pic)
        btnLaunch = view.findViewById(R.id.btn_launch)
        btnLaunch?.setOnClickListener {
            SPUtils.getInstance().put(Const.SaveInfoKey.HAS_APP_FIRST,true)
            onStartActivity(HomeActivity::class.java, isFinish = true)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        LogUtils.logGGQ("==WelcomeFragment===>>${index}")
        when(index) {
            1 -> {
                ivPic?.setBackgroundResource(R.drawable.welcome_1)
            }
            2 -> {
                ivPic?.setBackgroundResource(R.drawable.welcome_2)
            }
            3 -> {
                ivPic?.setBackgroundResource(R.drawable.welcome_3)
                btnLaunch?.toGone()
            }
            4 -> {
                ivPic?.setBackgroundResource(R.drawable.welcome_4)
                btnLaunch?.toVisible()
            }
        }
    }
}