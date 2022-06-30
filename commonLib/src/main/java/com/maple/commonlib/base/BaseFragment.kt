package com.maple.commonlib.base

import android.os.Bundle
import android.view.View
import com.maple.commonlib.utils.ToastUtils
import com.maple.commonlib.widget.dialog.LoadingDialog
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.maple.baselib.base.BaseFragment as B

abstract class BaseFragment: B() {

    /**
     * toast
     * @param s 显示内容
     */
    open fun showToast(s: String?) {
        ToastUtils.showToast(s)
    }

    private var loadingDialog: LoadingDialog? = null


    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = context?.let {
                LoadingDialog(it)
            }
        }
        loadingDialog?.run {
            if(!this.isShowing) this.show()
        }
    }

    open fun dismissLoading() {
        loadingDialog?.run {
            if (isShowing) {
                runBlocking {
                    delay(500L)
                }
                dismiss()
            }
        }
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(color, fitWindow)
        // 只需要设置状态栏，不需要设置导航栏
        statusBarOnly {
            // 布局是否侵入状态栏（true 不侵入，false 侵入）
            this.fitWindow = fitWindow
            // 状态栏背景颜色（资源 id）
            this.colorRes = color
            // light模式：状态栏字体 true: 灰色，false: 白色 Android 6.0+
            this.light = true
        }
    }


    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
    }
}