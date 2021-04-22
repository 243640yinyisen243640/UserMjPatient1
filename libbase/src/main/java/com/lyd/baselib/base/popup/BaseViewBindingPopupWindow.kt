package com.lyd.baselib.base.popup

import android.content.Context
import android.view.View
import androidx.viewbinding.ViewBinding
import com.lyd.baselib.base.viewbinding.inflateBindingWithGeneric
import razerdp.basepopup.BasePopupWindow

/**
 * 描述: 基于ViewBinding封装的BasePopupWindow
 * 作者: LYD
 * 创建日期: 2021/1/23
 */
abstract class BaseViewBindingPopupWindow<VB : ViewBinding>(context: Context) : BasePopupWindow(context) {

    lateinit var binding: VB

    override fun onCreateContentView(): View {
        binding = inflateBindingWithGeneric(context.layoutInflater)
        return binding.root
    }

}