package com.maple.commonlib.widget.dialog

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.dialog.BaseCenterDialog
import com.maple.commonlib.R
import com.maple.commonlib.utils.MySpannableString

class UsedDialog(context: Context, isCancelable: Boolean = false, private val listener: OnClickListener) :
    BaseCenterDialog(
        context,
        (ScreenUtils.getScreenWidth() * 0.8).toInt(),
        (ScreenUtils.getScreenHeight() * 0.42).toInt(),
        isCancelable = isCancelable, style = R.style.CommonDialogStyle
    ) {

    private var tvContent: TextView? = null

    //协议内容
    private val content: String by lazy { UIUtils.getString(R.string.common_used_content) }

    override fun getLayoutId(): Int = R.layout.dialog_used

    override fun initData(savedInstanceState: Bundle?) {
        tvContent = findViewById(R.id.tv_content)
        tvContent?.let {
            val mss = MySpannableString(context, content)
                .first("《隐私政策》").onClick(it) {
                    listener.onPolicyClick()
                }.textColor(R.color.common_used_protocol)
                .first("《用户协议》").onClick(it) {
                    listener.onAgreementClick()
                }.textColor(R.color.common_used_protocol)
            it.text = mss
        }


        findViewById<Button>(R.id.btn_cancel)?.let {
            it.setOnClickListener {
                this.onCancel()
                listener.onCancelClick()
            }
        }
        findViewById<Button>(R.id.btn_confirm)?.let {
            it.setOnClickListener {
                this.onCancel()
                listener.onConfirmClick()
            }
        }

    }

    interface OnClickListener {
        fun onCancelClick()
        fun onConfirmClick()
        fun onPolicyClick()
        fun onAgreementClick()
    }
}