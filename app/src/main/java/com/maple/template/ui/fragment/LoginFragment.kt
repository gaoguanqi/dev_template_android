package com.maple.template.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.maple.baselib.ext.afterTextChanged
import com.maple.baselib.ext.toGone
import com.maple.baselib.ext.toVisible
import com.maple.baselib.utils.StringUtils
import com.maple.baselib.utils.UIUtils
import com.maple.commonlib.base.BaseViewFragment
import com.maple.commonlib.utils.MySpannableString
import com.maple.template.R
import com.maple.template.databinding.FragmentLoginBinding
import com.maple.template.vm.AccountViewModel
import com.maple.template.widget.view.LoadingButton

class LoginFragment : BaseViewFragment<FragmentLoginBinding, AccountViewModel>() {

    private val eyeShowDrawable by lazy {
        UIUtils.getDrawable(R.drawable.ic_eye_show)
    }
    private val eyeHideDrawable by lazy {
        UIUtils.getDrawable(R.drawable.ic_eye_hide)
    }
    //使用协议
    private val usedContent: String by lazy { UIUtils.getString(R.string.used_agree) }


    override fun hasNavController(): Boolean = true

    override fun hasStatusBarMode(): Boolean = true

    private val viewModel by viewModels<AccountViewModel>()

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initData(savedInstanceState: Bundle?) {
        this.binding.let { bd ->
            val rawSource =  RawResourceDataSource(requireContext())
            rawSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.video_login_hd)))
            val player = ExoPlayer.Builder(requireContext()).build()
            bd.player.player = player
            // ExoPlayer兼容所有类型的视频宽高比
            bd.player.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player.repeatMode = Player.REPEAT_MODE_ALL
            player.setMediaItem(MediaItem.fromUri(rawSource.uri!!))
            player.prepare()
            player.play()

            bd.ibtnClose.setOnClickListener {
                showToast("关闭")
            }
            bd.btnHelp.setOnClickListener {
                showToast("帮助")
            }

            bd.etAccount.afterTextChanged {
                if(!TextUtils.isEmpty(it)) {
                    bd.ibtnAccountClear.toVisible()
                    setLoginState(StringUtils.isNotEmpty(bd.etPassword.text.toString().trim()))
                } else {
                    bd.ibtnAccountClear.toGone()
                    setLoginState(true)
                }
            }

            bd.etPassword.afterTextChanged {
                if(!TextUtils.isEmpty(it)) {
                    setLoginState(StringUtils.isNotEmpty(bd.etAccount.text.toString().trim()))
                } else {
                    setLoginState(false)
                }
            }

            bd.lbtnLogin.setListener(object : LoadingButton.OnListener{
                override fun onStart() {
                    bd.lbtnLogin.onLoading()
                    showToast("点击")
                }
            })

            bd.btnRegister.setOnClickListener {
                openRegister()
            }
            bd.btnForget.setOnClickListener {
                openForget()
            }

            bd.ibtnAccountClear.setOnClickListener {
                bd.etAccount.setText("")
                setLoginState(false)
            }

            bd.ibtnPasswordEye.setOnClickListener {
                UIUtils.editTransformation(bd.etPassword,it as ImageView,eyeShowDrawable,eyeHideDrawable)
            }

            bd.tvAgree.let {
                val mss = MySpannableString(requireContext(), usedContent)
                    .first("《用户使用协议》").onClick(it) {
                        showToast("用户使用协议")
                    }.textColor(R.color.common_agree_text)
                    .first("《隐私条款》").onClick(it) {
                        showToast("隐私条款")
                    }.textColor(R.color.common_agree_text)
                it.text = mss
            }
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun setStatusBarMode(color: Int, fitWindow: Boolean) {
        super.setStatusBarMode(R.color.common_trans, false)
    }



    private fun openRegister() {
        navController?.navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun openForget() {
        navController?.navigate(R.id.action_loginFragment_to_forgotPwdFragment)
    }

    private fun setLoginState(isActive: Boolean) {
        if(isActive) {
            this.binding.lbtnLogin.setActiveState()
        } else {
            this.binding.lbtnLogin.setEnableState()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}