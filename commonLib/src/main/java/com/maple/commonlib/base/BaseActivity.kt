package com.maple.commonlib.base

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.maple.commonlib.R
import com.maple.commonlib.utils.ToastUtils
import com.maple.commonlib.widget.dialog.LoadingDialog
import com.maple.commonlib.widget.state.showEmpty
import com.maple.commonlib.widget.state.showError
import com.maple.commonlib.widget.state.showLoading
import com.maple.commonlib.widget.state.showSuccess
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.maple.baselib.base.BaseActivity as B

/***
 * 上层基础组件封装
 * 用于无网络请求等简单页面
 */
abstract class BaseActivity : B(){
    /**
     * 是否使用多状态视图
     */
    open fun hasUsedStateView(): Boolean = false


    /**
     * 多状态视图
     * 如果使用多状态视图,子类必须重写 hasUsedStateView 并返回 true,即可调用 onStateXXX() 等方法
     * 标题栏 不属于多状态视图内的View,布局文件中需要有一个id为 common_container 作为 切换的视图主体
     * 否则为整个 contentView
     */
    private val stateView by lazy {
        if(hasUsedStateView()) {
            this.findViewById<View>(R.id.common_container)?.bindMultiState() ?: this.bindMultiState()
        } else { null }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    /**
     * toast
     * @param s 显示内容
     */
    open fun showToast(s: String?) {
        ToastUtils.showToast(s)
    }


    private var loadingDialog: LoadingDialog? = null

    /// 展示 loading
    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog?.run {
            if(!this.isShowing)  this.show()
        }
    }

    /// 关闭 loading
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

    /**
     * 设置状态栏颜色
     * @param color R.color.common_white
     * @param fitWindow
     */
    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(color, fitWindow)
        // 只需要设置状态栏，不需要设置导航栏
//        statusBarOnly {
//            // 布局是否侵入状态栏（true 不侵入，false 侵入）
//            this.fitWindow = fitWindow
//            // 状态栏背景颜色（资源 id）
//            this.colorRes = color
//            // light模式：状态栏字体 true: 灰色，false: 白色 Android 6.0+
//            this.light = true
//        }

        immersionBar {
//            statusBarColor(color)
            statusBarDarkFont(true)
            fitsSystemWindows(fitWindow)
        }
    }

    /***
     * 多状态布局 加载页面
     */
    open fun onStateLoading() {
        if(hasUsedStateView()) stateView?.showLoading()
    }

    /***
     * 多状态布局 空页面
     */
    open fun onStateEmpty() {
        if(hasUsedStateView()) stateView?.showEmpty()
    }

    /***
     * 多状态布局 错误页面
     */
    open fun onStateError() {
        if(hasUsedStateView()) stateView?.let { c ->
            c.showError(callBack = { it.retry = {
                onStateRetry(c)
            } })
        }
    }

    /***
     * 多状态布局 内容页面
     */
    open fun onStateSuccess() {
        if(hasUsedStateView()) stateView?.showSuccess()
    }

    /***
     * 多状态布局 错误页面的 重试
     */
    open fun onStateRetry(container: MultiStateContainer?) {}



}