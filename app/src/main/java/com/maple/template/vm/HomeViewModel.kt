package com.maple.template.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.maple.baselib.manager.SingleLiveEvent
import com.maple.baselib.utils.LogUtils
import com.maple.commonlib.base.BaseViewModel
import com.maple.template.db.DBHelper
import com.maple.template.db.UserInfo
import com.maple.template.model.entity.BannerEntity
import com.maple.template.model.entity.LoginEntity
import com.maple.template.model.entity.RecordPageEntity
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
        defUI.showUILoading()
        launchOnlyResult(isShowDialog = false, block = {
            repository.getBanner("",0,1, 10)
        }, success = {
            it.let { data ->
                val entity = GsonUtils.fromJson(GsonUtils.toJson(data), BannerEntity::class.java)
                LogUtils.logGGQ("==entity==>>>${entity.code}")
                entity?.data?.list?.let { list ->
                    if(list.isNotEmpty()) {
                        defUI.showUIContent()
                        bannerList.postValue(list)
                    } else {
                        defUI.showUIEmpty()
                    }
                }?: let {
                    defUI.showUIEmpty()
                }
            }
        }, error = {
            defUI.showUIError()
        })
    }

    val refreshEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    // 加载更多事件
    val loadMoreEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    val recordList: MutableLiveData<List<RecordPageEntity.Data.RecordList>> = MutableLiveData()
    val recordRefreshList: MutableLiveData<List<RecordPageEntity.Data.RecordList>> = MutableLiveData()
    val recordLoadMoreList: MutableLiveData<List<RecordPageEntity.Data.RecordList>> = MutableLiveData()

    fun getRecordList() {
        resetPage()
        launchOnlyResult(
            isShowDialog = false,
            block = {
                repository.getRecordList("",page, limit)
            }, success = {
                it.let { data ->
                    val entity = GsonUtils.fromJson(GsonUtils.toJson(data), RecordPageEntity::class.java)
                    entity?.data?.list?.let { list ->
                        if(list.isNotEmpty()) {
                            recordList.postValue(list)
                            setLoadMore(entity.data.current,entity.data.pages)
                        }
                    }
                }
            })
    }

    fun refreshRecordList() {
        resetPage()
        launchOnlyResult(
            isShowDialog = false,
            block = {
                repository.getRecordList("",page, limit)
            }, success = {
                it.let { data ->
                    val entity = GsonUtils.fromJson(GsonUtils.toJson(data), RecordPageEntity::class.java)
                    entity?.data?.list?.let { list ->
                        if(list.isNotEmpty()) {
                            recordRefreshList.postValue(list)
                            setLoadMore(entity.data.current,entity.data.pages)
                        }
                    }
                }
            }, complete = {
                refreshEvent.call()
            })
    }

    fun loadMoreRecordList() {
        launchOnlyResult(
            isShowDialog = false,
            block = {
                repository.getRecordList("",page, limit)
            }, success = {
                it.let { data ->
                    val entity = GsonUtils.fromJson(GsonUtils.toJson(data), RecordPageEntity::class.java)
                    entity?.data?.list?.let {
                        if (it.isNotEmpty()) {
                            recordLoadMoreList.postValue(it)
                            setLoadMore(entity.data.current,entity.data.pages)
                        }
                    }
                }
            }, complete = {
                loadMoreEvent.call()
            })
    }
}