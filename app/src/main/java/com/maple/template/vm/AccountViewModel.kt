package com.maple.template.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.manager.SingleLiveEvent
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseViewModel
import com.maple.commonlib.http.resp.BaseResp
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo
import com.maple.template.model.entity.LoginEntity
import com.maple.template.model.repository.CommonRepository
import java.util.*

class AccountViewModel: BaseViewModel(){

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val loginEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val userInfoLiveData: MutableLiveData<UserInfo> = MutableLiveData()

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
                val entity = GsonUtils.fromJson(GsonUtils.toJson(data), LoginEntity::class.java)
                LogUtils.logGGQ("==entity==>>>${entity.code}")
                entity?.data?.user?.let { user ->
                    LogUtils.logGGQ("--user-->>${user.userId}")
                    val userInfo: UserInfo = UserInfo().apply {
                        this.userId = user.userId
                        this.userName = user.username
                        this.nickName = user.nickname
                        this.phone = user.phone
                        this.token = user.token
                        this.avatarUrl = user.avatarUrl
                        this.gender = user.gender
                        this.email = user.email
                    }
                    if(DBHelper.saveUser(userInfo)) {
                        userInfoLiveData.postValue(userInfo)
                    } else {
                        defUI.onToast("登录失败！")
                    }
                }
            }
        }, complete = {
            loginEvent.call()
        })
    }


}