package com.maple.baselib.base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kongqw.network.monitor.NetworkMonitorManager
import com.kongqw.network.monitor.enums.NetworkState
import com.kongqw.network.monitor.interfaces.NetworkMonitor
import com.maple.baselib.utils.Event
import com.maple.baselib.utils.EventBusUtils
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity: AppCompatActivity(), IView {

    /// 传递bundle 数据 Key
    private val KEY_BUNDLE_DATA: String = "BUNDLE_DATA"

    /// 布局
    abstract fun getLayoutId(): Int

    abstract fun initData(savedInstanceState: Bundle?): Unit

    /// 是否使用 event bus
    open fun hasUsedEventBus(): Boolean = false
    // 是否使用状态栏
    open fun hasStatusBarMode(): Boolean = false
    // 是否使用 返回键拦截
    open fun hasEventKeyBack(): Boolean = false
    // 是否使用 监听网络状态变化
    open fun hasNetworkState(): Boolean = false

    open fun onBeforeCreate(savedInstanceState: Bundle?) {}
    /// base 中相比 initData 之前的调用的方法,用来封装初始化下拉刷新组件
    open fun initView(savedInstanceState: Bundle?){}

    /// 回退事件
    open fun onKeyBack(keyCode: Int) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        onBeforeCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentLayout()
        initView(savedInstanceState)
        if (hasStatusBarMode()) {
            setStatusBarMode()
        }
        if (hasUsedEventBus()) {
            EventBusUtils.register(this)
        }
        if(hasNetworkState()) {
            NetworkMonitorManager.getInstance().register(this)
        }
        initData(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    /// 设置布局文件(BaseActivity 和 BaseViewActivity分别重写)
    open fun setContentLayout() {
        setContentView(getLayoutId())
    }

    /// 默认透明状态栏
    open fun setStatusBarMode(color: Int = Color.TRANSPARENT, fitWindow: Boolean = true) {}

    /***
     * 打开新的页面
     * @param clazz 新页面
     * @param bundle 传递bundle 数据,非必填
     * @param isFinish 是否关闭当前页面 默认不关闭
     */
    open fun onStartActivity(
        clazz: Class<out Activity?>,
        bundle: Bundle? = null,
        isFinish: Boolean = false
    ) {
        this.startActivity(Intent(this, clazz).apply {
            bundle?.let {
                this.putExtra(KEY_BUNDLE_DATA, it)
            }
        })
        if (isFinish) this.finish()
    }

    /***
     * 关闭页面
     */
    open fun onFinish() {
        this.finish()
    }

    fun onClickProxy(m: () -> Unit) {
        if (!UIUtils.isFastClick()) {
            m()
        }
    }

    /// 回退
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (hasEventKeyBack()) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onKeyBack(keyCode)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 接收到普通的Event
     * 封装MAIN线程模式，子类可重写 onEvnetBusReceive,
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> onEventBusReceive(event: Event<T>?) {
        if (event != null) {
            onEventBusDispense(event)
        }
    }

    /**
     * 接收到粘性的Event
     * 封装MAIN线程模式，子类可重写 onStickyEvnetBusReceive,
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun <T> onStickyEventBusReceive(event: Event<T>?) {
        if (event != null) {
            onStickyEventBusDispense(event)
        }
    }

    /**
     * 子类重写onEventBusDispense，处理接收到的普通事件
     */
    open fun <T> onEventBusDispense(event: Event<T>) {}

    /**
     * 子类重写onStickyEventBusDispense，处理接收到的粘性事件
     */
    open fun <T> onStickyEventBusDispense(event: Event<T>) {}

    @NetworkMonitor
    fun onNetWorkStateChange(networkState: NetworkState) {
        onNetworkChange(networkState)
    }

    /**
     * 子类重写此方法可监听网络状态变化,前提 hasNetworkState 必须为 true
     * 如果 fragment 需要监听网络状态, 可使 activity 和 fragment 使用同一个 viewModule 实现监听
     */
    open fun onNetworkChange(networkState: NetworkState){
        when (networkState) {
            NetworkState.NONE -> {
                // 暂无网络
                LogUtils.logGGQ("--暂无网络->>>")
            }
            NetworkState.WIFI -> {
                // WIFI网络
                LogUtils.logGGQ("--WIFI网络->>>")
            }

            NetworkState.CELLULAR -> {
                // 蜂窝网络
                LogUtils.logGGQ("--蜂窝网络->>>")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(hasNetworkState()) {
            NetworkMonitorManager.getInstance().unregister(this)
        }
        if(hasUsedEventBus()) {
            EventBusUtils.unregister(this)
        }
    }
}