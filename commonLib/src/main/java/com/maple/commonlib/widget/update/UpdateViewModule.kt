package com.maple.commonlib.widget.update

import androidx.databinding.ObservableField
import com.maple.commonlib.base.BaseViewModel

class UpdateViewModule: BaseViewModel(){

    val updateTitle: ObservableField<String> = ObservableField("")
    val updateInfo: ObservableField<String> = ObservableField("")

    val updateState: ObservableField<Boolean> = ObservableField(false)

    val ignoreState: ObservableField<Boolean> = ObservableField(false)

    val progressState: ObservableField<Boolean> = ObservableField(false)

    val progressSpeed: ObservableField<Int> = ObservableField(0)




}