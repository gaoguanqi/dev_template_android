package com.maple.template.ui.fragment

import android.os.Bundle
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentLoginBinding
import com.maple.template.vm.AccountViewModel

class LoginFragment : BaseViewFragment<FragmentLoginBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()


    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }


}