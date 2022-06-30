package com.maple.baselib.base

import android.text.TextUtils
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maple.baselib.app.BaseApp
import com.maple.baselib.manager.SingleLiveEvent
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    val app: BaseApp by lazy {
        BaseApp.instance
    }

    val defUI: UIChange by lazy { UIChange() }

    fun onClickProxy(m: () -> Unit) {
        if (!UIUtils.isFastClick()) {
            m()
        }
    }


    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            block()
        }catch (e: Exception) {
            LogUtils.logGGQ("error--->launchUI->${e.message}")
            if(TextUtils.isEmpty(e.message)) {
                defUI.onToast("请求异常,请稍后重试！！！")
            } else {
                defUI.onToast("${e.message}")
            }
            defUI.onDismissDialog()
            if(defUI.getUIState() == ViewState.LOADING) {
                defUI.showUIError()
            }
        }
    }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * UI事件
     */
    inner class UIChange {
        val toastEvent by lazy { SingleLiveEvent<String>() }

        fun onToast(msg: String?) {
            toastEvent.postValue(msg)
        }

        val showDialog by lazy { SingleLiveEvent<Any>() }
        fun onShowDialog() {
            showDialog.call()
        }

        val dismissDialog by lazy { SingleLiveEvent<Any>() }
        fun onDismissDialog () {
            dismissDialog.call()
        }

        //0 content --  1 loading --  2 empty --  3 error
        val stateViewEvent by lazy { MutableLiveData<ViewState>() }

        fun getUIState(): ViewState? {
            return stateViewEvent.value
        }

        fun showUIContent(){
            stateViewEvent.postValue(ViewState.SUCCESS)
        }

        fun showUILoading(){
            stateViewEvent.postValue(ViewState.LOADING)
        }

        fun showUIEmpty(){
            stateViewEvent.postValue(ViewState.EMPTY)
        }

        fun showUIError(){
            stateViewEvent.postValue(ViewState.ERROR)
        }
    }

    override fun onCleared() {
        super.onCleared()
        LogUtils.logGGQ("VM ->> onCleared")
    }
}