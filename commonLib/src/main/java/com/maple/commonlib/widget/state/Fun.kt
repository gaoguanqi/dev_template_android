package com.maple.commonlib.widget.state

import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.state.SuccessState


fun MultiStateContainer.showSuccess(callBack: () -> Unit = {}) {
    show<SuccessState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showError(callBack: (MyErrorState) -> Unit = {}) {
    show<MyErrorState> {
        callBack.invoke(it)
    }
}

fun MultiStateContainer.showEmpty(callBack: () -> Unit = {}) {
    show<MyEmptyState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showLoading(callBack: () -> Unit = {}) {
    show<MyLoadingState> {
        callBack.invoke()
    }
}
