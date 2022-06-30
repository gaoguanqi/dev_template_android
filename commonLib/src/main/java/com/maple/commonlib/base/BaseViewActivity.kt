package com.maple.commonlib.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseViewActivity<VB : ViewDataBinding, VM : BaseViewModel>: BaseActivity(), CoroutineScope by MainScope() {

    /***
     *  创建 viewModel
     */
    inline fun <reified VM : ViewModel> viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }


    protected val binding: VB by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as VB }

    abstract fun bindViewModel()

    override fun setContentLayout() {
        super.setContentLayout()
        this.binding.lifecycleOwner = this
        this.bindViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }

}