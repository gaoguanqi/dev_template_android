package com.maple.template.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maple.baselib.ext.layoutInflater
import com.maple.commonlib.base.BaseFragment
import com.maple.commonlib.base.BaseViewFragment
import com.maple.commonlib.common.MyFragmentStateAdapter
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

    private val fragmentList: MutableList<BaseFragment> = mutableListOf()
    private val tabNameList: MutableList<String> = mutableListOf()


    private val viewModel by viewModels<HomeViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {

        fragmentList.add(WatchTabFragment.getInstance())
        fragmentList.add(FollowTabFragment.getInstance())
        tabNameList.add("推荐")
        tabNameList.add("关注")

        this.binding.apply {
            this.pager.adapter = MyFragmentStateAdapter(requireActivity(), fragmentList)
            val mediator: TabLayoutMediator = TabLayoutMediator(this.tab,this.pager) { tab, position ->
                val tabItem: View = this@MainFragment.requireContext().layoutInflater.inflate(R.layout.tab_nav_item, null)
                val tvTab: TextView = tabItem.findViewById(R.id.tv_tab)
                tvTab.let {
                    it.text = tabNameList[position]
                }
                tab.customView = tabItem
            }.apply {
                this.attach()
            }
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

}