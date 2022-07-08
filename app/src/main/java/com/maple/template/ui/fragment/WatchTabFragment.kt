package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentWatchTabBinding
import com.maple.template.model.entity.BannerEntity
import com.maple.template.ui.adapter.BannerAdapter
import com.maple.template.vm.HomeViewModel
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter

class WatchTabFragment : BaseViewFragment<FragmentWatchTabBinding, HomeViewModel>() {

    companion object {
        @JvmStatic
        fun getInstance(): WatchTabFragment {
            return WatchTabFragment()
        }
    }

    private var bannerView: BannerViewPager<BannerEntity.Data.Banner>? = null


    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_watch_tab

    private val bannerAdapter: BannerAdapter by lazy {
        BannerAdapter()
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        bannerView = view.findViewById(R.id.banner)
        bannerView?.apply {
            this.adapter = bannerAdapter
            this.setLifecycleRegistry(lifecycle)
        }?.create()
    }

    override fun initData(savedInstanceState: Bundle?) {

        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })

        viewModel.bannerList.observe(this, Observer {
            bannerView?.refreshData(it)
        })
        viewModel.getBanner()
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }
}