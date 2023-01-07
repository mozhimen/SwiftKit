package com.mozhimen.uicorek.layoutk.tab.bottom

import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import com.mozhimen.uicorek.layoutk.commons.LayoutKFrame
import com.mozhimen.uicorek.layoutk.tab.bottom.helpers.TabBottomFragmentAdapter

/**
 * @ClassName TabBottomFragmentView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 21:07
 * @Version 1.0
 */
class TabBottomFragmentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    LayoutKFrame(context, attrs, defStyleAttr) {

    private var _adapter: TabBottomFragmentAdapter? = null
    private var _currentPosition = 0

    /**
     * 获取adapter
     * @return TabBottomFragmentAdapter?
     */
    fun getAdapter(): TabBottomFragmentAdapter? {
        return _adapter
    }

    /**
     * 设置adapter
     * @param adapter TabBottomFragmentAdapter?
     */
    fun setAdapter(adapter: TabBottomFragmentAdapter?) {
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
    @Throws(Exception::class)
    fun setCurrentItem(position: Int) {
        requireNotNull(_adapter) { "$TAG please call setAdapter first." }
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
    @Throws(Exception::class)
    fun getCurrentFragment(): Fragment? {
        requireNotNull(_adapter) { "$TAG please call setAdapter first." }
        return _adapter!!.getCurrentFragment()
    }
}