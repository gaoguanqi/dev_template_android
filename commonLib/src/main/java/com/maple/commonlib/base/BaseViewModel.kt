package com.maple.commonlib.base

import android.text.TextUtils
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.NetworkUtil
import com.maple.commonlib.http.error.ERROR
import com.maple.commonlib.http.error.ExceptionHandle
import com.maple.commonlib.http.error.ResponseThrowable
import com.maple.commonlib.http.resp.BaseResp
import com.maple.commonlib.http.resp.ResultCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import com.maple.baselib.base.BaseViewModel as B

open class BaseViewModel: B() {


    /**
     *  不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun launchGo(
        isShowDialog: Boolean = true,
        isShowToast: Boolean = true,
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {
            LogUtils.logGGQ("--isShowToast-->${isShowToast}")
            LogUtils.logGGQ("--error-->${it.errMsg}")
            if(isShowToast) defUI.onToast(it.errMsg)
        },
        complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        if (!NetworkUtil.isConnected()) {
            defUI.onToast(ERROR.NETWORD_UNCONNECTED.getValue())
            return
        }
        if (isShowDialog) defUI.onShowDialog()
        launchUI {
            handleException(
                withContext(Dispatchers.IO) { block },
                {
                    error(it)
                },
                {
                    complete()
                    if(isShowDialog) {
                        defUI.onDismissDialog()
                    }
                }
            )
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun launchOnlyResult(
        isShowDialog: Boolean = true,
        isShowToast: Boolean = true,
        block: suspend CoroutineScope.() -> BaseResp,
        success: (BaseResp) -> Unit,
        error: (ResponseThrowable) -> Unit = {
            LogUtils.logGGQ("--isShowToast--->${isShowToast}")
            LogUtils.logGGQ("--error--->${it.errMsg}")
            if(isShowToast) defUI.onToast(it.errMsg)
        },
        complete: () -> Unit = {}
    ) {
        if (!NetworkUtil.isConnected()) {
            defUI.onToast(ERROR.NETWORD_UNCONNECTED.getValue())
            return
        }
        if (isShowDialog) defUI.onShowDialog()
        launchUI {
            handleException<BaseResp>(
                { withContext(Dispatchers.IO) { block() } },
                { res ->
                    executeResponse(res) {
                        success(it)
                    }
                },
                {
                    error(it)
                },
                {
                    complete()
                    if(isShowDialog) {
                        defUI.onDismissDialog()
                    }
                }
            )
        }
    }

    /**
     * 请求结果过滤
     */
    private suspend fun executeResponse(
        response: BaseResp,
        success: suspend CoroutineScope.(BaseResp) -> Unit
    ) {
        coroutineScope {
            if (ResultCode.isSuccess(response.code)) {
                success(response)
            } else {
                if(TextUtils.isEmpty(response.msg)) {
                    response.msg = "操作失败！"
                }
                LogUtils.logGGQ("----http--->${response.msg}")
                throw ResponseThrowable(response.code, response.msg)
            }
        }
    }

    /**
     * 异常统一处理
     * 不过滤请求结果使用
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        try {
            coroutineScope {
                try {
                    block()
                    LogUtils.logGGQ("-->>-N-T-<<")
                } catch (e: ResponseThrowable) {
                    error(ExceptionHandle.handleException(e, e))
                } finally {
                    complete()
                }
            }
        } catch (e: ResponseThrowable) {
            e.fillInStackTrace()
            LogUtils.logGGQ("异常--e->${e.message}")
            error(ExceptionHandle.handleException(e, e))
        }
    }


    /**
     * 异常统一处理
     * 过滤请求结果使用
     */
    private suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> BaseResp,
        success: suspend CoroutineScope.(BaseResp) -> Unit,
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
        complete: suspend CoroutineScope.() -> Unit
    ) {
        try {
            coroutineScope {
                try {
                    success(block())
                    LogUtils.logGGQ("-->>-T-T-<<")
                } catch (e: ResponseThrowable) {
                    error(ExceptionHandle.handleException(e, e))
                } finally {
                    complete()
                }
            }
        } catch (e: ResponseThrowable) {
            e.fillInStackTrace()
            LogUtils.logGGQ("异常--e->${e.message}")
            error(ExceptionHandle.handleException(e, e))
        }
    }
}