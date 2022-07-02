package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentBookBinding
import com.maple.template.vm.HomeViewModel

class BookFragment : BaseViewFragment<FragmentBookBinding,HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): BookFragment {
            return BookFragment()
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_book

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}