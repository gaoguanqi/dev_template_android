package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentFollowTabBinding
import com.maple.template.vm.HomeViewModel

class FollowTabFragment : BaseViewFragment<FragmentFollowTabBinding, HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): FollowTabFragment {
            return FollowTabFragment()
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_follow_tab

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}