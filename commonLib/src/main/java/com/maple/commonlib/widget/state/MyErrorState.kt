package com.maple.commonlib.widget.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.maple.commonlib.R
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class MyErrorState: MultiState() {

    private var tvErrorMsg: TextView? = null
//    private var lottieError: LottieAnimationView? = null
    private var tvRetry: TextView? = null

    var retry: (() -> Unit)? = null

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.my_state_error, container, false)
    }


    override fun onMultiStateViewCreate(view: View) {
        tvErrorMsg = view.findViewById(R.id.tv_error_msg)
//        lottieError = view.findViewById(R.id.lottie_error)
        tvRetry = view.findViewById(R.id.tv_retry)
        tvRetry?.setOnClickListener { retry?.invoke() }
    }
}