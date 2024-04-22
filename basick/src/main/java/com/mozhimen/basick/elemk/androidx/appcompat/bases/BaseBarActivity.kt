package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.mozhimen.basick.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.widget.UtilKTextViewWrapper
import com.mozhimen.basick.utilk.android.widget.applyTypeface
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKToolbar
import com.mozhimen.basick.utilk.androidx.appcompat.applyCustomView
import com.mozhimen.basick.utilk.androidx.appcompat.getActionBarContainer

/**
 * @ClassName BaseBarActivityVB
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseBarActivity : BaseSaveStateActivity(), IActivity {

    //////////////////////////////////////////////////////////////////////////////
    //custom style
    //////////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        supportActionBar?.let {
            initToolbar(it)
        }
    }

    override fun setTitle(title: CharSequence) {
        _strTitle = title
        _titleView?.text = title
    }

    //////////////////////////////////////////////////////////////////////////////
    //navigation
    //////////////////////////////////////////////////////////////////////////////

    /**
     * 获取返回图标
     */
    @DrawableRes
    protected open fun getNavigationIcon(): Int {
        return android.R.drawable.arrow_up_float
    }

    protected open fun setNavigationIcon(@DrawableRes navigationIconId: Int) {
        _toolbar?.setNavigationIcon(navigationIconId)
    }

    /**
     * 设置自定义标题
     */
    protected open fun setCustomTitleView(customTitleView: View) {
        //如果当前标题已经初始化，设置自定义标题，如果当前标题没有初始化，记录下
        //自定义标题，在初始化的时候设置上
        _customView = if (null != _toolbar) {
            _toolbar!!.applyCustomView(customTitleView)
            null
        } else customTitleView
    }

    //////////////////////////////////////////////////////////////////////////////

    //region # private func
    private var _strTitle: CharSequence? = null
    private var _toolbar: Toolbar? = null
    private var _customView: View? = null
    private var _titleView: TextView? = null

    //////////////////////////////////////////////////////////////////////////////

    private fun initToolbar(actionBar: ActionBar) {
        actionBar.setDisplayShowTitleEnabled(false)
        if (UtilKBuildVersion.isAfterV_21_5_L()) //用来隐藏Toolbar的阴影效果
            getActionBarContainer()?.elevation = 0f

        _toolbar = UtilKToolbar.get(this)
        if (null == _customView) {//如果自定义标题为空，初始化通用的标题，否则初始话自定义标题
            _toolbar?.let {
                applyTitleView(it)
            }
        } else {
            _toolbar?.applyCustomView(_customView!!)
        }
        setTitle(title)
    }

    /**
     * 初始化通用标题 只有左边的返回按钮和中间的标题
     * @param toolbar 当前Toolbar
     */
    private fun applyTitleView(toolbar: Toolbar) {
        if (null == _titleView) {//如果当前的TextView为null，则创建一个
            _titleView = UtilKTextViewWrapper.get(this).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                applyTypeface(Typeface.BOLD)
            }
        } else {
            val parent: ViewParent? = _titleView!!.parent
            if (null != parent) {
                (parent as ViewGroup).removeView(_titleView)
            }
        }
        val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        _titleView?.layoutParams = layoutParams

        toolbar.addView(_titleView)

        if (!TextUtils.isEmpty(_strTitle)) {
            _titleView?.text = _strTitle
        }
        toolbar.setNavigationIcon(getNavigationIcon())
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
    //endregion
}