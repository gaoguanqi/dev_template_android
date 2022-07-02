package com.maple.commonlib.widget.update

import androidx.databinding.ObservableField
import com.maple.baselib.manager.SingleLiveEvent
import com.maple.commonlib.base.BaseViewModel

class UpdateViewModule: BaseViewModel(){

    val updateTitle: ObservableField<String> = ObservableField("升级到V1.0.1版本")
    val updateInfo: ObservableField<String> = ObservableField("1,修复bug \n 2,修复bug \n 3,修复bug \n 4,修复bug ")

    val updateState: ObservableField<Boolean> = ObservableField(true)

    val ignoreState: ObservableField<Boolean> = ObservableField(true)

    val progressState: ObservableField<Boolean> = ObservableField(false)

    val ignoreEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    fun onIgnore(){
        ignoreEvent.call()
    }

    val updateEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    fun onUpdate(){
        updateEvent.call()
    }


}