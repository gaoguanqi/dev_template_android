package com.maple.commonlib.widget.update

import androidx.lifecycle.MutableLiveData
import com.maple.commonlib.base.BaseViewModel

class UpdateViewModel: BaseViewModel(){

    val infoLiveData: MutableLiveData<InfoData> = MutableLiveData()

}