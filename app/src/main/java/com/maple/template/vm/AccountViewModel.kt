package com.maple.template.vm

import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseViewModel
import com.maple.commonlib.http.resp.BaseResp
import com.maple.template.model.repository.CommonRepository
import java.util.*

class AccountViewModel: BaseViewModel(){

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    fun onLogin(account: String, password: String) {
        LogUtils.logGGQ("account--->${account}")
        LogUtils.logGGQ("password--->${password}")
        launchOnlyResult(isShowDialog = false, block = {
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("username", account)
            params.put("password", password)
            repository.loginPhone(params)
        }, success = {
            it.let { data ->
                val loginEntity = GsonUtils.fromJson(GsonUtils.toJson(data), BaseResp::class.java)
            }
        })
    }


}