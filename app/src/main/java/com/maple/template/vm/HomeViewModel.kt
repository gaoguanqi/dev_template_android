package com.maple.template.vm

import androidx.lifecycle.MutableLiveData
import com.maple.commonlib.base.BaseViewModel
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo

class HomeViewModel: BaseViewModel(){

    val userInfoLiveData: MutableLiveData<UserInfo?> = MutableLiveData()

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        userInfoLiveData.value = DBHelper.getUser()
    }
}