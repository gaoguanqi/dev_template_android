package com.maple.commonlib.widget.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.maple.commonlib.R
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class MyLoadingState: MultiState() {

//    private var lottieLoading: LottieAnimationView? = null


    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.my_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
//        lottieLoading = view.findViewById(R.id.lottie_loading)
    }
}