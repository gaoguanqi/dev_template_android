package com.maple.commonlib.widget.update

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.widget.dialog.BaseDialogFragment
import com.maple.commonlib.R
import com.maple.commonlib.databinding.DialogUpdateBinding

class UpdateDialog: BaseDialogFragment<DialogUpdateBinding>(mHeight = (ScreenUtils.getScreenHeight() * 0.92f).toInt()){

    override fun getLayoutId(): Int = R.layout.dialog_update

    override fun initFragment(view: View, savedInstanceState: Bundle?) {

    }
}