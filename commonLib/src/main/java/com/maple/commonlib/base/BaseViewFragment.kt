package com.maple.commonlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.maple.commonlib.R
import com.maple.commonlib.widget.state.showEmpty
import com.maple.commonlib.widget.state.showError
import com.maple.commonlib.widget.state.showLoading
import com.maple.commonlib.widget.state.showSuccess
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseViewFragment<VB : ViewDataBinding, VM : BaseViewModel>: BaseFragment(), CoroutineScope by MainScope() {

    /**
     * 多状态视图
     * 如果使用多状态视图,子类必须重写 hasUsedStateView 并返回 true,即可调用 onStateXXX() 等方法
     * 标题栏 不属于多状态视图内的View,布局文件中需要有一个id为 common_container 作为 切换的视图主体
     * 否则为整个 contentView
     */
    private var stateView: MultiStateContainer? = null

    inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> {
        return lazy {
            ViewModelProvider(requireActivity()).get(VM::class.java)
        }
    }

    protected lateinit var binding: VB

    open fun hasNavController(): Boolean = false

    open fun hasUsedStateView(): Boolean = false

    protected var navController: NavController? = null


    abstract fun bindViewModel()

    override fun setContentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return if(hasUsedStateView()){
            stateView = binding.root.let {rootView ->
                rootView.findViewById<View>(R.id.common_container)?.bindMultiState()
            }
            if(binding.root.findViewById<View>(R.id.common_container) == null){
                stateView!!
            }else{
                binding.root
            }
        }else{
            binding.root
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        this.binding.lifecycleOwner = this
        this.bindViewModel()
        if(hasNavController()) {
            this.navController = Navigation.findNavController(view)
        }
    }

    /**
     * 返回
     */
    open fun onPopBack(){
        if(hasNavController()){
            this.navController?.popBackStack()
        }
    }

    open fun onStateLoading(){
        if(hasUsedStateView())stateView?.showLoading()
    }

    open fun onStateEmpty(){
        if(hasUsedStateView())stateView?.showEmpty()
    }

    open fun onStateError(){
        if(hasUsedStateView())stateView?.let { c ->
            c.showError(callBack = { it.retry = {
                onStateRetry(c)
            } })
        }
    }

    open fun onStateSuccess(){
        if(hasUsedStateView())stateView?.showSuccess()
    }

    open fun onStateRetry(container: MultiStateContainer?){}

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }
}