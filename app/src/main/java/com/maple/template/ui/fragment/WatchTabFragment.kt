package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.NetworkUtil
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentWatchTabBinding
import com.maple.template.model.entity.BannerEntity
import com.maple.template.model.entity.RecordPageEntity
import com.maple.template.ui.adapter.BannerAdapter
import com.maple.template.ui.adapter.RecordAdapter
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

    private val listAdapter: RecordAdapter by lazy {
        RecordAdapter(requireContext()).apply {
            this.setListener(object :RecordAdapter.OnClickListener{
                override fun onBannerItemClick(pos: Int, item: BannerEntity.Data.Banner?) {
                    item?.let {
                        showToast(it.title)
                    }
                }


                override fun onListItemClick(pos: Int, item: RecordPageEntity.Data.RecordList?) {
                    item?.let {
                        showToast(it.createTime)
                    }
                }
            })
        }
    }

    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_watch_tab



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

        viewModel.refreshEvent.observe(this,{
            finishRefresh()
        })

        viewModel.loadMoreEvent.observe(this,{
            finishLoadMore()
        })

        viewModel.bannerList.observe(this, Observer {
            LogUtils.logGGQ("====bannerList========>>${it.size}")
            listAdapter.setBanner(it)
        })

        viewModel.recordList.observe(this, Observer {
            listAdapter.setList(it,viewModel.total)
            setNoMoreData(viewModel.getNoMoreData())
        })
        viewModel.recordRefreshList.observe(this, Observer {
            listAdapter.setList(it,viewModel.total)
            setNoMoreData(viewModel.getNoMoreData())
        })
        viewModel.recordLoadMoreList.observe(this, Observer {
            listAdapter.upDataList(it,viewModel.total)
            setNoMoreData(viewModel.getNoMoreData())
        })

        binding.let { bd ->
            bd.refreshLayout.apply {
                this.setEnableRefresh(true)//是否启用下拉刷新功能
                this.setEnableLoadMore(true)//是否启用上拉加载功能
                this.setOnRefreshListener {
                    if(NetworkUtil.isConnected()) {
                        onRefreshData()
                    } else {
                        showToast("请检查网络！")
                        finishRefresh()
                    }
                }
                this.setOnLoadMoreListener {
                    if(NetworkUtil.isConnected()) {
                        onLoadMoreData()
                    } else {
                        showToast("请检查网络！")
                        finishLoadMore()
                    }

                }
            }
            bd.rvList.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                this.adapter = listAdapter
            }
        }
        viewModel.getBanner()
        viewModel.getRecordList()
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }


    //结束下拉刷新
    private fun finishRefresh() {
        binding.refreshLayout.let {
            if (it.isRefreshing) it.finishRefresh()
        }
    }

    private fun setNoMoreData(noMoreData: Boolean) {
        binding.refreshLayout.let {
            it.setNoMoreData(noMoreData)
        }
    }

    //结束加载更多
    private fun finishLoadMore() {
        binding.refreshLayout.let {
            if (it.isLoading) it.finishLoadMore()
        }
    }

    private fun onRefreshData() {
        viewModel.refreshRecordList()
    }

    private fun onLoadMoreData() {
        viewModel.loadMoreRecordList()
    }
}