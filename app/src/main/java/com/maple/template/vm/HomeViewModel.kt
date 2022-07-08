package com.maple.template.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseViewModel
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo
import com.maple.template.model.entity.BannerEntity
import com.maple.template.model.entity.LoginEntity
import com.maple.template.model.repository.CommonRepository
import java.util.*

class HomeViewModel: BaseViewModel(){

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }

    val userInfoLiveData: MutableLiveData<UserInfo?> = MutableLiveData()

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        userInfoLiveData.value = DBHelper.getUser()
    }


    val bannerList: MutableLiveData<List<BannerEntity.Data.Banner>> = MutableLiveData()
    fun getBanner() {
        launchOnlyResult(isShowDialog = false, block = {
            repository.getBanner("",0,1, 10)
        }, success = {
            it.let { data ->
                val entity = GsonUtils.fromJson(GsonUtils.toJson(data), BannerEntity::class.java)
                LogUtils.logGGQ("==entity==>>>${entity.code}")
                entity?.data?.list?.let { list ->
                    if(list.isNotEmpty()) {
                        bannerList.postValue(list)
                    } else {
                        LogUtils.logGGQ("===banner=empty=>>>")
                    }
                }?: let {
                    LogUtils.logGGQ("===banner=error=>>>")
                }
            }
        })
    }
}