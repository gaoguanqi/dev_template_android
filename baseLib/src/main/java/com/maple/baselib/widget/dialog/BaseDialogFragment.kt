package com.maple.baselib.widget.dialog

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maple.baselib.utils.LogUtils


/**
 * BaseDialogFragment
 */
abstract class BaseDialogFragment<VB : ViewDataBinding>(
    private val mWidth: Int = (ScreenUtils.getScreenWidth() * 0.9f).toInt(),
    private val mHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    private val mGravity: Int = Gravity.CENTER
) : DialogFragment(),
    CoroutineScope by MainScope() {

    protected lateinit var binding: VB
    protected var savedState = false

    inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> {
        return lazy {
            ViewModelProvider(requireActivity()).get(VM::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Material_Dialog_Alert)
        dialog?.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
//            it.setWindowAnimations(R.style.DialogPushInOutAnimation)
        }

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        savedState = true
        super.onSaveInstanceState(outState)
    }

    open fun showAllowStateLoss(manager: FragmentManager, tag: String) {
        LogUtils.logGGQ("====savedState=====>>>>${savedState}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.isStateSaved) return
        }
        if (savedState) return
        show(manager, tag)
    }

    override fun onStart() {
        super.onStart()
        val attrs = dialog?.window?.attributes?.apply {
            this.width = mWidth
            this.height = mHeight
            this.gravity = mGravity
        }

        dialog?.setCancelable(getCancelable())
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(0))
            attributes = attrs
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        bindViewModel()
        initFragment(view, savedInstanceState)
    }

    open fun bindViewModel() {}
    override fun onDestroy() {
        super.onDestroy()
        cancel()
        binding.unbind()
    }

    abstract fun getLayoutId(): Int

    abstract fun initFragment(view: View, savedInstanceState: Bundle?)

    /**
     * 是否点击外部可关闭dialog
     * true 可关闭
     * false 不可关闭
     */
    open fun getCancelable():Boolean{
        return false
    }

    override fun show(manager: FragmentManager, tag: String?) {
//        super.show(manager, tag)
        //避免重复添加的异常 java.lang.IllegalStateException: Fragment already added
        val fragment: Fragment? = manager.findFragmentByTag(tag)
        if(fragment != null) {
            val fragmentTransaction: FragmentTransaction = manager.beginTransaction()
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
        //避免状态丢失的异常 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        try {
            super.show(manager, tag)
        }catch (e: Exception) {
            e.printStackTrace()
            LogUtils.logGGQ("====catch==BaseDialogFragment=>>>${e.message}")
        }
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onReset()
    }


    open fun onReset(){
        savedState = false
    }
}