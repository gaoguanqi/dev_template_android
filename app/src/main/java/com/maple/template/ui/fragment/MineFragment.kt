package com.maple.template.ui.fragment

import android.os.Bundle
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

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}