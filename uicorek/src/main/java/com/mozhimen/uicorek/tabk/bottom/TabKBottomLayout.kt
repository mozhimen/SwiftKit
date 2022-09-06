package com.mozhimen.uicorek.tabk.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKScreen.getScreenWidth
import com.mozhimen.basick.utilk.UtilKView.findTypeChildView
import com.mozhimen.basick.basek.BaseKLayoutFrame
import com.mozhimen.basick.extsk.asColorTone
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorek.tabk.commons.ITabKLayout

/**
 * @ClassName TabKBottomLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:19
 * @Version 1.0
 */
class TabKBottomLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : BaseKLayoutFrame(context, attrs, defAttrStyle), ITabKLayout<TabKBottomItem, TabKBottomMo> {

    companion object {
        private const val TAG_TABK_BOTTOM_LAYOUT = "TAG_TABK_BOTTOM_LAYOUT"
    }

    private val _tabSelectedChangeListeners: ArrayList<ITabKLayout.TabSelectedListener<TabKBottomMo>> =
        ArrayList()
    private var _selectedMo: TabKBottomMo? = null
    private var _moList: List<TabKBottomMo>? = null
    private var _tabBottomAlpha = 1f
    private var _tabBottomHeight = 50f.dp2px()//TabBottom高度
    private var _tabBottomLineHeight = 0.5f.dp2px()//TabBottom的头部线条高度
    private var _tabBottomLineColor = 0xdfe0e1//TabBottom的头部线条颜色
    private var _tabBottomContainer: FrameLayout? = null

    /**
     * 设置底部透明度
     * @param alpha Float
     */
    fun setTabBottomAlpha(alpha: Float) {
        this._tabBottomAlpha = alpha
    }

    /**
     * 设置底部高度
     * @param tabHeight Float
     */
    fun setTabBottomHeight(tabHeight: Int) {
        this._tabBottomHeight = tabHeight
    }

    /**
     * 设置底部线高度
     * @param bottomLineHeight Float
     */
    fun setTabBottomLineHeight(bottomLineHeight: Int) {
        this._tabBottomLineHeight = bottomLineHeight
    }

    /**
     * 设置底部线颜色
     * @param bottomLineColor String
     */
    fun setTabBottomLineColor(bottomLineColor: Any) {
        this._tabBottomLineColor = bottomLineColor.asColorTone()
    }

    /**
     * 设置文字或图片距底部边距
     * @param paddingBottom Int
     */
    fun setTabBottomPadding(paddingBottom: Int) {
        _tabBottomContainer?.setPadding(0, 0, 0, paddingBottom)
    }

    /**
     * 裁剪底部距离
     * @param targetView ViewGroup?
     */
    fun clipBottomPadding(targetView: ViewGroup?) {
        if (targetView != null) {
            targetView.setPadding(
                0, 0, 0, _tabBottomHeight
            )
            targetView.clipToPadding = false
        }
    }

    /**
     * 重置底部Tab
     */
    fun resizeTabBottomLayout() {
        requireNotNull(_moList) { "infoList must not be null!" }
        val width: Int = getScreenWidth() / _moList!!.size
        val frameLayout = getChildAt(childCount - 1) as ViewGroup
        val childCount = frameLayout.childCount
        for (i in 0 until childCount) {
            val button = frameLayout.getChildAt(i)
            val params = button.layoutParams as LayoutParams
            params.width = width
            params.leftMargin = i * width
            button.layoutParams = params
        }
    }

    override fun findTab(mo: TabKBottomMo): TabKBottomItem? {
        val tabKBottom = findViewWithTag<ViewGroup>(TAG_TABK_BOTTOM_LAYOUT)
        for (i in 0 until tabKBottom.childCount) {
            val child = tabKBottom.getChildAt(i)
            if (child is TabKBottomItem) {
                if (child.getTabInfo() == mo) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: ITabKLayout.TabSelectedListener<TabKBottomMo>) {
        _tabSelectedChangeListeners.add(listener)
    }


    override fun defaultSelected(defaultMo: TabKBottomMo) {
        onSelected(defaultMo)
    }

    override fun inflateInfo(moList: List<TabKBottomMo>) {
        if (moList.isEmpty()) {
            return
        }
        this._moList = moList
        //移除之前已经添加的View
        for (i in childCount - 1 downTo 1) {
            removeViewAt(i)
        }
        _selectedMo = null
        addBackground()
        //清除之前添加的TabkBottom listener,同时遍历和删除问题,采用迭代器
        val iterator: MutableIterator<ITabKLayout.TabSelectedListener<TabKBottomMo>> =
            _tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is TabKBottomItem) {
                iterator.remove()
            }
        }
        val width = getScreenWidth() / moList.size
        val height = _tabBottomHeight
        //不用LinearLayout的原因: 当动态改变child大小后Gravity.Bottom会失效.
        _tabBottomContainer = FrameLayout(context)
        _tabBottomContainer!!.tag = TAG_TABK_BOTTOM_LAYOUT
        for (i in moList.indices) {
            val info = moList[i]
            val itemLP = LayoutParams(width, height)
            itemLP.gravity = Gravity.BOTTOM
            itemLP.leftMargin = i * width
            val tabKBottom = TabKBottomItem(context)
            _tabSelectedChangeListeners.add(tabKBottom)
            tabKBottom.setTabInfo(info)
            _tabBottomContainer!!.addView(tabKBottom, itemLP)
            tabKBottom.setOnClickListener { onSelected(info) }
        }
        val containerLP = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        containerLP.gravity = Gravity.BOTTOM
        addBottomLine()
        addView(_tabBottomContainer!!, containerLP)
        fixContentView()
    }

    private fun onSelected(nextMo: TabKBottomMo) {
        require(_moList != null) { "infoList must not be null!" }
        for (listener in _tabSelectedChangeListeners) {
            listener.onTabSelected(_moList!!.indexOf(nextMo), _selectedMo, nextMo)
        }
        this._selectedMo = nextMo
    }

    private fun addBackground() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tabk_bottom_layout, null)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, _tabBottomHeight)
        layoutParams.gravity = Gravity.BOTTOM
        addView(view, layoutParams)
        view.alpha = _tabBottomAlpha
    }

    private fun addBottomLine() {
        val bottomLine = View(context)
        bottomLine.setBackgroundColor(_tabBottomLineColor)
        val bottomLineLP = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, _tabBottomLineHeight)
        bottomLineLP.gravity = Gravity.BOTTOM
        bottomLineLP.bottomMargin = _tabBottomHeight - _tabBottomLineHeight
        addView(bottomLine, bottomLineLP)
        bottomLine.alpha = _tabBottomAlpha
    }

    /**
     * 修复内容区域的底部padding
     */
    private fun fixContentView() {
        if (getChildAt(0) !is ViewGroup) {
            return
        }
        val rootView = getChildAt(0) as ViewGroup
        var targetView: ViewGroup? = findTypeChildView(rootView, RecyclerView::class.java)
        if (targetView == null) {
            targetView = findTypeChildView(rootView, ScrollView::class.java)
        }
        if (targetView == null) {
            targetView = findTypeChildView(rootView, AbsListView::class.java)
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, _tabBottomHeight)
            targetView.clipToPadding = false
        }
    }
}