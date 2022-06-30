package com.maple.commonlib.base

import android.os.Bundle
import android.view.View
import com.maple.commonlib.R
import com.zy.multistatepage.bindMultiState
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



}