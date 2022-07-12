package com.maple.template.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseViewModel
import com.maple.template.model.entity.AppInfoEntity
import com.maple.template.model.repository.CommonRepository

class AboutViewModel: BaseViewModel() {

    private val repository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { CommonRepository() }


    val appInfoLiveData: MutableLiveData<AppInfoEntity.Data.AppInfo> = MutableLiveData()
    fun checkVersion() {
        launchOnlyResult(block = {
            repository.checkVersion("com.maple.template","0")
        }, success = {
            it.let { data ->
                val entity = GsonUtils.fromJson(GsonUtils.toJson(data), AppInfoEntity::class.java)
                LogUtils.logGGQ("==entity==>>>${entity.code}")
                entity?.data?.appInfo?.let { info ->
                    appInfoLiveData.postValue(info)
                }
            }
        })
    }
}