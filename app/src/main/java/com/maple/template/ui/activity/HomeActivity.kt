package com.maple.template.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.base.BaseActivity
import com.maple.commonlib.base.BaseFragment
import com.maple.commonlib.common.MyFragmentStateAdapter
import com.maple.template.R
import com.maple.template.ui.fragment.BookFragment
import com.maple.template.ui.fragment.FindFragment
import com.maple.template.ui.fragment.MainFragment
import com.maple.template.ui.fragment.MineFragment
import com.maple.template.widget.lottie.LottieAnimation

class HomeActivity : BaseActivity() {

    private var pager: ViewPager2? = null
    private var bnav: BottomNavigationView? = null

    private val list: MutableList<BaseFragment> = mutableListOf()

    private val navMenus = listOf(
        LottieAnimation.MAIN,
        LottieAnimation.BOOK,
        LottieAnimation.FIND,
        LottieAnimation.MINE
    )

    // 推荐 watch
    // 关注 follow
    private val navTexts = arrayListOf(
        "首页",
        "通讯",
        "发现",
        "我的"
    )

    override fun hasStatusBarMode(): Boolean = true

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        pager = findViewById(R.id.pager)
        bnav = findViewById(R.id.bnav)
    }

    override fun initData(savedInstanceState: Bundle?) {
        list.apply {
            this.add(MainFragment.getInstance())
            this.add(BookFragment.getInstance())
            this.add(FindFragment.getInstance())
            this.add(MineFragment.getInstance())
        }
        pager?.let {
            it.offscreenPageLimit = list.size
            it.isUserInputEnabled = false
            val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.adapter = adapter
            it.currentItem = 0
            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position) {
                        0 -> {
                            LogUtils.logGGQ("首页")
                        }
                        1 -> {
                            LogUtils.logGGQ("通讯")
                        }
                        2 -> {
                            LogUtils.logGGQ("发现")
                        }
                        3 -> {
                            LogUtils.logGGQ("我的")
                            clearMineBadge()
                        }
                    }
                }
            })
        }

        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        bnav?.let {
            it.menu.apply {
                for (i in navTexts.indices) {
                    // 添加title
                    this.add(Menu.NONE, i, Menu.NONE, navTexts[i])
                }
                this.setLottieDrawable(navMenus,bnav!!)
            }

            it.setOnNavigationItemSelectedListener { item ->
                handleNavigationItem(item)
                // 联动 ViewPager2
                pager?.setCurrentItem(item.itemId, true)
                true
            }
            it.setOnNavigationItemReselectedListener { item ->
                handleNavigationItem(item)
            }
            it.selectedItemId = 0

            // 处理长按 MenuItem 提示 TooltipText
            it.menu.forEachIndexed { index, item ->
                val menuItemView = it.findViewById(item.itemId) as BottomNavigationItemView
                menuItemView.setOnLongClickListener {
                    true
                }

                LogUtils.logGGQ("==itemId==>>${item.itemId}")
                // 我的 增加小红点
                if(item.itemId == (navTexts.size - 1)) {
                    mineBadge = it.getOrCreateBadge(item.itemId)
                    mineBadge?.let { menuBadge ->
                        menuBadge.badgeGravity = BadgeDrawable.TOP_END
                        menuBadge.backgroundColor = ContextCompat.getColor(this,R.color.common_badge_bg)
                        menuBadge.badgeTextColor = ContextCompat.getColor(this, R.color.common_badge_text)
                        menuBadge.verticalOffset = 18
                        menuBadge.horizontalOffset = 24
                        // badge最多显示字符，默认999+ 是4个字符（带'+'号）
                        menuBadge.maxCharacterCount = 3
                        mineBadge?.isVisible = false
                    }

                }
            }

            // 设置随机数小红点
            val random = (0..199).random()
            setMineBadge(random)
        }
    }

    private var mineBadge: BadgeDrawable? = null

    private fun setMineBadge(number: Int) {
        mineBadge?.number = number
        mineBadge?.isVisible = true
    }

    private fun clearMineBadge() {
        mineBadge?.clearNumber()
        mineBadge?.isVisible = false
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_white, fitWindow)
    }

    private var lastBackPressedMillis: Long = 0L

    override fun hasEventKeyBack(): Boolean = true

    override fun onKeyBack(keyCode: Int) {
        if (lastBackPressedMillis + 2000L > System.currentTimeMillis()) {
            //moveTaskToBack(true)
            this@HomeActivity.onFinish()
        } else {
            lastBackPressedMillis = System.currentTimeMillis()
            showToast(UIUtils.getString(R.string.exit_app))
        }
        super.onKeyBack(keyCode)
    }


    private fun Menu.setLottieDrawable(lottieAnimationList: List<LottieAnimation>, bottomNavBar: BottomNavigationView) {
        for (i in lottieAnimationList.indices) {
            val item = findItem(i)
            item.icon = getLottieDrawable(lottieAnimationList[i], bottomNavBar)
        }
    }

    private fun getLottieDrawable(
        animation: LottieAnimation,
        bottomNavigationView: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromAssetSync(
                bottomNavigationView.context.applicationContext, animation.value
            )
            callback = bottomNavigationView
            composition = result.value
        }
    }

   private var currentItemId = 0
   private fun handleNavigationItem(item: MenuItem) {
       handlePlayLottieAnimation(item)
       currentItemId = item.itemId
   }
    private fun handlePlayLottieAnimation(item: MenuItem) {
        val currentIcon = item.icon as? LottieDrawable
        currentIcon?.apply {
            playAnimation()
        }

        bnav?.let {
            // 处理 tab 切换，icon 对应调整
            if (item.itemId != currentItemId) {
                it.menu.findItem(currentItemId).icon =
                    getLottieDrawable(navMenus[currentItemId], it)
            }
        }

    }

}