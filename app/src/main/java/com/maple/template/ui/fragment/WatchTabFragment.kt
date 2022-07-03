package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentWatchTabBinding
import com.maple.template.vm.HomeViewModel

class WatchTabFragment : BaseViewFragment<FragmentWatchTabBinding, HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): WatchTabFragment {
            return WatchTabFragment()
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_watch_tab

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}