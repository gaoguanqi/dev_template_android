package com.maple.template.ui.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.base.BaseFragment
import com.maple.commonlib.common.MyFragmentStateAdapter
import com.maple.template.R
import com.maple.template.ui.fragment.BookFragment
import com.maple.template.ui.fragment.FindFragment
import com.maple.template.ui.fragment.MainFragment
import com.maple.template.ui.fragment.MineFragment

class HomeActivity : BaseActivity() {

    private var pager: ViewPager2? = null
    private var bnav: BottomNavigationView? = null

    private val list: MutableList<BaseFragment> = mutableListOf()

    override fun hasStatusBarMode(): Boolean = true

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        pager = findViewById(R.id.pager)
    }

    override fun initData(savedInstanceState: Bundle?) {
        list.apply {
            this.add(MainFragment.getInstance())
            this.add(BookFragment.getInstance())
            this.add(FindFragment.getInstance())
            this.add(MineFragment.getInstance())
        }
        pager?.let {
            it.isUserInputEnabled = false
            val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.adapter = adapter
            it.currentItem = 0
            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_white, fitWindow)
    }
}