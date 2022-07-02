package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentMainBinding
import com.maple.template.vm.HomeViewModel

class MainFragment : BaseViewFragment<FragmentMainBinding,HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}