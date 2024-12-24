package com.mozhimen.basick.bases

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.helpers.IToolbarProxy
import com.mozhimen.basick.helpers.IToolbarProxyProvider
import com.mozhimen.basick.helpers.ToolbarProxy
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.BaseSaveStateActivity
import com.mozhimen.kotlin.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKActionBar
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
abstract class BaseBarActivity : BaseSaveStateActivity, IActivity, IToolbarProxyProvider, IToolbarProxy {
    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : super()

    /////////////////////////////////////////////////////////////////////

    protected val toolBarProxy by lazy_ofNone { ToolbarProxy<BaseBarActivity>().apply { bindLifecycle(this@BaseBarActivity) } }

    /////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        UtilKActionBar.get_ofSupport(this)?.let {
            toolBarProxy.initToolbar(this, it)
        }
    }

    /////////////////////////////////////////////////////////////////////

    override fun setToolbarNavigationIconRes(intResDrawable: Int) {
        toolBarProxy.setToolbarNavigationIconRes(intResDrawable)
    }

    override fun <A> setToolbarNavigationOnClickListener(provider: A, listener: IA_Listener<A>) where A : AppCompatActivity, A : IToolbarProxyProvider {
        toolBarProxy.setToolbarNavigationOnClickListener(provider, listener)
    }

    override fun setToolbarBackground(drawable: Drawable) {
        toolBarProxy.setToolbarBackground(drawable)
    }

    override fun setToolbarBackgroundColor(intColor: Int) {
        toolBarProxy.setToolbarBackgroundColor(intColor)
    }

    override fun setToolbarBackgroundRes(intResDrawable: Int) {
        toolBarProxy.setToolbarBackgroundRes(intResDrawable)
    }

    override fun setToolbarText(strText: CharSequence) {
        toolBarProxy.setToolbarText(strText)
    }

    override fun setToolbarTextRes(intStrRes: Int) {
        toolBarProxy.setToolbarTextRes(intStrRes)
    }

    override fun setToolbarCustomView(customView: View) {
        toolBarProxy.setToolbarCustomView(customView)
    }
}