package com.maple.template.ui.fragment

import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.maple.commonlib.base.BaseViewFragment
import com.maple.template.R
import com.maple.template.databinding.FragmentLoginBinding
import com.maple.template.vm.AccountViewModel
import com.maple.template.widget.view.LoadingButton

class LoginFragment : BaseViewFragment<FragmentLoginBinding, AccountViewModel>() {

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

    override fun onDestroy() {
        super.onDestroy()
    }
}