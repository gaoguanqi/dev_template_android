package com.maple.template.vm

import com.maple.baselib.manager.SingleLiveEvent
import com.maple.commonlib.base.BaseViewModel

class HomeViewModel: BaseViewModel(){


    val accountEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    fun onOpenAccount() {
        accountEvent.call()
    }
}