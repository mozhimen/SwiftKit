package com.mozhimen.uicorek.tabk.bottom

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import com.mozhimen.basicsk.basek.BaseKLayoutFrame
import com.mozhimen.uicorek.tabk.bottom.helpers.TabKFragmentAdapter

/**
 * @ClassName TabKFragmentView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 21:07
 * @Version 1.0
 */
class TabKFragmentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    BaseKLayoutFrame(context, attrs, defStyleAttr) {

    private var _adapter: TabKFragmentAdapter? = null
    private var _currentPosition = 0

    /**
     * 获取adapter
     * @return TabKFragmentAdapter?
     */
    fun getAdapter(): TabKFragmentAdapter? {
        return _adapter
    }

    /**
     * 设置adapter
     * @param adapter TabKViewAdapter?
     */
    fun setAdapter(adapter: TabKFragmentAdapter?) {
        if (this._adapter != null || adapter == null) {
            return
        }
        this._adapter = adapter
        _currentPosition = -1
    }

    /**
     * 设置当前item
     * @param position Int
     */
    fun setCurrentItem(position: Int) {
        requireNotNull(this._adapter) { "please call setAdapter first." }
        if (position < 0 || position >= _adapter!!.getCount()) {
            return
        }
        if (_currentPosition != position) {
            _currentPosition = position
            _adapter!!.instantiateItem(this, position)
        }
    }

    /**
     * 获取当前item
     * @return Int
     */
    fun getCurrentItem(): Int = _currentPosition

    /**
     * 获取当前fragment
     * @return Fragment
     */
    fun getCurrentFragment(): Fragment? {
        requireNotNull(this._adapter) { "please call setAdapter first." }
        return _adapter!!.getCurrentFragment()
    }
}