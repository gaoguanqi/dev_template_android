package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentRegisterBinding
import com.maple.template.vm.AccountViewModel

class RegisterFragment : BaseViewFragment<FragmentRegisterBinding, AccountViewModel>() {
    private val viewModel by viewModels<AccountViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}