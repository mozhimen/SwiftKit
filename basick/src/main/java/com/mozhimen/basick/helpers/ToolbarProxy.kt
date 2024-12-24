package com.mozhimen.basick.helpers

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.elemk.commons.IExt_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.android.view.UtilKViewGroupWrapper
import com.mozhimen.kotlin.utilk.android.view.removeView_ofParent
import com.mozhimen.kotlin.utilk.android.widget.UtilKTextViewWrapper
import com.mozhimen.kotlin.utilk.android.widget.applyTypeface
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKActionBarContainer
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKToolbar

/**
 * @ClassName ToolbarProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/20
 * @Version 1.0
 */
interface IToolbarProxyProvider {
    fun getActionBarElevation(): Float = 0f
    fun getToolbarNavigationIconRes(): Int? = android.R.drawable.arrow_up_float//获取返回图标
    fun getToolbarText(): CharSequence? = null
    fun getToolbarTextViewGenerator(): IExt_Listener<TextView> = { setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);applyTypeface(Typeface.BOLD) }
    fun <A> getToolbarNavigationOnClickListener(): IA_Listener<A>? where A : AppCompatActivity, A : IToolbarProxyProvider = { provider -> provider.onBackPressed() }
}

interface IToolbarProxy {
    //    fun setToolbarBackgroundRes(@DrawableRes intResDrawable: Int)
    fun setToolbarNavigationIconRes(@DrawableRes intResDrawable: Int)
    fun <A> setToolbarNavigationOnClickListener(provider: A, listener: IA_Listener<A>) where A : AppCompatActivity, A : IToolbarProxyProvider
    fun setToolbarBackground(drawable: Drawable)
    fun setToolbarBackgroundColor(@ColorInt intColor: Int)
    fun setToolbarBackgroundRes(@DrawableRes intResDrawable: Int)
    fun setToolbarText(strText: CharSequence)
    fun setToolbarTextRes(@StringRes intStrRes: Int)
    fun setToolbarCustomView(customView: View)
}

@OApiCall_BindViewLifecycle
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class ToolbarProxy<A> constructor() : BaseWakeBefDestroyLifecycleObserver(), IToolbarProxy where A : AppCompatActivity, A : IToolbarProxyProvider {
    private var _toolbar: Toolbar? = null
    private var _toolbar_view_custom: View? = null
    private var _toolbar_view_text: TextView? = null
//    private var _strText: CharSequence? = null

    /////////////////////////////////////////////////////////////////////

    fun initToolbar(provider: A, actionBar: ActionBar) {
        actionBar.setDisplayShowTitleEnabled(false)
        if (UtilKBuildVersion.isAfterV_21_5_L()) //用来隐藏Toolbar的阴影效果
            UtilKActionBarContainer.get(provider)?.elevation = provider.getActionBarElevation()

        _toolbar = UtilKToolbar.get(provider)
        _toolbar?.let {
            if (_toolbar_view_custom == null) { //如果自定义标题为空，初始化通用的标题，否则初始话自定义标题
                generateTextView(provider, it)
            } else {
                generateCustomView(it, _toolbar_view_custom!!)
            }
        }
        UtilKViewGroupWrapper.getAllViews(_toolbar!!)
    }

//    override fun setToolbarBackgroundRes(@DrawableRes intResDrawable: Int) {
//        _toolbar?.setBackgroundResource(intResDrawable)
//    }

    override fun setToolbarNavigationIconRes(@DrawableRes intResDrawable: Int) {
        _toolbar?.setNavigationIcon(intResDrawable)
    }

    override fun <A> setToolbarNavigationOnClickListener(provider: A, listener: IA_Listener<A>) where A : AppCompatActivity, A : IToolbarProxyProvider {
        _toolbar?.setNavigationOnClickListener { listener.invoke(provider) }
    }

    override fun setToolbarBackground(drawable: Drawable) {
        _toolbar?.setBackground(drawable)
    }

    override fun setToolbarBackgroundColor(@ColorInt intColor: Int) {
        _toolbar?.setBackgroundColor(intColor)
    }

    override fun setToolbarBackgroundRes(@DrawableRes intResDrawable: Int) {
        _toolbar?.setBackgroundResource(intResDrawable)
    }

    override fun setToolbarText(strText: CharSequence) {
        _toolbar_view_text?.text = strText
    }

    override fun setToolbarTextRes(@StringRes intStrRes: Int) {
        _toolbar_view_text?.setText(intStrRes)
    }

    //设置自定义标题
    override fun setToolbarCustomView(customView: View) {
        _toolbar_view_custom = if (_toolbar != null) {//如果当前标题已经初始化，设置自定义标题，如果当前标题没有初始化，记录下//自定义标题，在初始化的时候设置上
            generateCustomView(_toolbar!!, customView)
            null
        } else
            customView
    }

    /////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        _toolbar = null
        _toolbar_view_text = null
        _toolbar_view_custom = null
        UtilKLogWrapper.e(TAG, "onDestroy release")
        super.onDestroy(owner)
    }

    /////////////////////////////////////////////////////////////////////

    private fun generateTextView(provider: A, toolbar: Toolbar) {
        if (_toolbar_view_text == null) {//如果当前的TextView为null，则创建一个
            _toolbar_view_text = UtilKTextViewWrapper.get(provider).apply { provider.getToolbarTextViewGenerator().invoke(this) }
        } else {
            _toolbar_view_text!!.removeView_ofParent()
        }
        //layoutParams
        _toolbar_view_text?.layoutParams = generateToolbarLayoutParams_text(_toolbar_view_text!!)
        provider.getToolbarText()?.let { setToolbarText(it) }
        provider.getToolbarNavigationIconRes()?.let { setToolbarNavigationIconRes(it) }
        provider.getToolbarNavigationOnClickListener<A>()?.let { setToolbarNavigationOnClickListener(provider, it) }
        toolbar.navigationContentDescription = null
        toolbar.addView(_toolbar_view_text)
    }

    private fun generateCustomView(toolbar: Toolbar, customView: View) {
        toolbar.removeAllViews()//移除掉所有的子View
        toolbar.setContentInsetsRelative(0, 0)//设置左右两边的边距为0
        toolbar.setContentInsetsAbsolute(0, 0)
        //layoutParams
        customView.layoutParams = generateToolbarLayoutParams_custom(customView)//layoutParams
        toolbar.navigationIcon = null
        toolbar.addView(customView)
    }

    private fun generateToolbarLayoutParams_text(view: View): Toolbar.LayoutParams {
        val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        return layoutParams
    }

    private fun generateToolbarLayoutParams_custom(view: View): Toolbar.LayoutParams {
        var layoutParams = view.layoutParams//没有设置LayoutParams 创建一个占满父控件的LayoutParams
        if (layoutParams == null) {
            layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
        } else if (layoutParams !is Toolbar.LayoutParams) {
            layoutParams = Toolbar.LayoutParams(layoutParams)
        }
        return layoutParams
    }
}