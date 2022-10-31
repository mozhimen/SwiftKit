package com.mozhimen.uicorek.tabk.top

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKScreen.getScreenWidth
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import com.mozhimen.uicorek.tabk.top.mos.MTabKTop
import kotlin.math.abs

/**
 * @ClassName TabKTopLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 23:36
 * @Version 1.0
 */
class TabKTopLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr), ITabKLayout<TabKTopItem, MTabKTop> {
    private val _tabSelectedChangeListeners: ArrayList<ITabKLayout.TabSelectedListener<MTabKTop>> = ArrayList()
    private var _selectedMo: MTabKTop? = null
    private var _moList: List<MTabKTop>? = null
    private var _tabTopWidth: Int = 0
    private var _tabTopHeight = 40f.dp2px()//TabBottom高度

    init {
        isVerticalScrollBarEnabled = false
    }

    /**
     * 设置顶部高度
     * @param tabHeight Int
     */
    fun setTabTopHeight(tabHeight: Int) {
        this._tabTopHeight = tabHeight
    }

    /**
     * 设置背景颜色
     * @param color Int
     */
    fun setTabTopBackground(color: Int) {
        this.setBackgroundColor(color)
    }

    override fun findTab(info: MTabKTop): TabKTopItem? {
        val rootView: ViewGroup = getRootLayout(false)
        for (i in 0 until rootView.childCount) {
            val child = rootView.getChildAt(i)
            if (child is TabKTopItem) {
                if (child.getTabKInfo() == info) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: ITabKLayout.TabSelectedListener<MTabKTop>) {
        _tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: MTabKTop) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<MTabKTop>) {
        if (infoList.isEmpty()) {
            return
        }
        this._moList = infoList
        val container = getRootLayout(true)
        _selectedMo = null
        //清除之前添加的TabKTop listener,Tips: Java foreach remove问题
        val iterator: MutableIterator<ITabKLayout.TabSelectedListener<MTabKTop>> =
            _tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is TabKTopItem) {
                iterator.remove()
            }
        }
        for (i in infoList.indices) {
            val info = infoList[i]
            val tab = TabKTopItem(context)
            _tabSelectedChangeListeners.add(tab)
            tab.setTabInfo(info)
            container.addView(tab)
            tab.setOnClickListener { onSelected(info) }
        }
    }

    private fun onSelected(nextMo: MTabKTop) {
        require(_moList != null) { "infoList must not be null!" }
        for (listener in _tabSelectedChangeListeners) {
            listener.onTabSelected(_moList!!.indexOf(nextMo), _selectedMo, nextMo)
        }
        this._selectedMo = nextMo
        autoScroll(nextMo)
    }

    /**
     * 自动滚动,实现点击的位置能够自动滚动以展示前后2个
     * @param nextMo TabKTopInfo
     */
    private fun autoScroll(nextMo: MTabKTop) {
        val tabKTop = findTab(nextMo) ?: return
        val index: Int = _moList!!.indexOf(nextMo)
        val location = IntArray(2)
        //获取点击控件在屏幕的位置
        tabKTop.getLocationInWindow(location)
        if (_tabTopWidth == 0) {
            _tabTopWidth = tabKTop.width
        }
        //判断点击了屏幕左侧还是右侧
        val scrollWidth: Int = if ((location[0] + _tabTopWidth / 2) > getScreenWidth() / 2) {
            rangeScrollWidth(index, 2)
        } else {
            rangeScrollWidth(index, -2)
        }
        smoothScrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动的范围
     * @param index Int 从第几个开始
     * @param range Int 向前后的范围
     * @return Int
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth = 0
        for (i in 0..abs(range)) {
            val next: Int = if (range < 0) {
                range + i + index
            } else {
                range - i + index
            }
            if (next >= 0 && next < _moList!!.size) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false)
                } else {
                    scrollWidth += scrollWidth(next, true)
                }
            }
        }
        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     * @param index Int 指定位置的控件
     * @param toRight Boolean 是否是点击了屏幕右侧
     * @return Int 可滚动的距离
     */
    private fun scrollWidth(index: Int, toRight: Boolean): Int {
        val target = findTab(_moList!![index]) ?: return 0
        val rect = Rect()
        target.getLocalVisibleRect(rect)
        return if (toRight) { //点击了屏幕右侧
            if (rect.right > _tabTopWidth) { //right坐标大于控件的宽度时,说明完全没有显示
                _tabTopWidth
            } else { //显示部分,减去已显示的宽度
                _tabTopWidth - rect.right
            }
        } else {
            if (rect.left <= -_tabTopWidth) { //left坐标小于等于-控件的宽度,说明完全没有显示
                return _tabTopWidth
            } else if (rect.left > 0) { //显示部分
                return rect.left
            }
            0
        }
    }

    private fun getRootLayout(clear: Boolean): LinearLayout {
        var rootView: LinearLayout? = getChildAt(0) as LinearLayout?
        if (rootView == null) {
            rootView = LinearLayout(context)
            rootView.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                _tabTopHeight
            )
            addView(rootView, params)
        } else if (clear) {
            rootView.removeAllViews()
        }
        return rootView
    }
}