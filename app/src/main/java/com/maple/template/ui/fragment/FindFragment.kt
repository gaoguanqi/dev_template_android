package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentFindBinding
import com.maple.template.vm.HomeViewModel

class FindFragment : BaseViewFragment<FragmentFindBinding, HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): FindFragment {
            return FindFragment()
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_find

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}