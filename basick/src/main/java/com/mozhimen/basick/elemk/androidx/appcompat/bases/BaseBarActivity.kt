package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import com.mozhimen.basick.elemk.androidx.appcompat.IToolbarProxyProvider
import com.mozhimen.basick.elemk.androidx.appcompat.ToolbarProxy
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKActionBar
import com.mozhimen.basick.utilk.kotlin.UtilKLazyJVM.lazy_ofNone

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseBarActivity : BaseSaveStateActivity(), IActivity, IToolbarProxyProvider {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    private val _toolBarProxy by lazy_ofNone { ToolbarProxy<BaseBarActivity>().apply { bindLifecycle(this@BaseBarActivity) } }

    /////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    @CallSuper
    override fun initLayout() {
        UtilKActionBar.get_ofSupport(this)?.let {
            _toolBarProxy.initToolbar(this, it, title)
        }
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarTitle(strTitle: CharSequence) {
        _toolBarProxy.setToolbarTitle(strTitle)
    }

    @OptIn(OApiUse_BaseApplication::class, OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun setToolbarTitle(intResStr: Int) {
        _toolBarProxy.setToolbarTitle(getString(intResStr))
    }

    override fun getToolbarNavigationIcon(): Int =
        android.R.drawable.arrow_up_float

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarNavigationIcon(@DrawableRes intResDrawable: Int) {
        _toolBarProxy.setToolbarNavigationIcon(intResDrawable)
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarCustomView(customView: View) {
        _toolBarProxy.setToolbarCustomView(customView)
    }
}