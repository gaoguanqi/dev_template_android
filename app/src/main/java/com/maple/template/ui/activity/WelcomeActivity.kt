package com.maple.template.ui.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.common.MyFragmentStateAdapter
import com.maple.template.R
import com.maple.template.ui.fragment.WelcomeFragment
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class WelcomeActivity : BaseActivity() {

    override fun hasStatusBarMode(): Boolean = true

    private var pager: ViewPager2? = null
    private var indicator: IndicatorView? = null


    private val list by lazy {
        listOf(
            WelcomeFragment.getInstance(1),
            WelcomeFragment.getInstance(2),
            WelcomeFragment.getInstance(3),
            WelcomeFragment.getInstance(4)
        )
    }

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        pager = findViewById(R.id.pager)
        indicator = findViewById(R.id.indicator)
    }
    override fun initData(savedInstanceState: Bundle?) {

        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        pager?.apply {
            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = adapter
            this.currentItem = 0
        }
        indicator?.apply {
            this.setSliderColor(resources.getColor(R.color.common_indicator_normal), resources.getColor(R.color.common_indicator_active))
            this.setSliderWidth(resources.getDimension(R.dimen.common_indicator_width))
            this.setSliderHeight(resources.getDimension(R.dimen.common_indicator_height))
            this.setSlideMode(IndicatorSlideMode.WORM)
            this.setIndicatorStyle(IndicatorStyle.CIRCLE)
            this.setPageSize(pager?.adapter?.itemCount?:0)
            this.notifyDataChanged()
        }

        pager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicator?.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator?.onPageSelected(position)
            }
        })
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_white, fitWindow)
    }
}