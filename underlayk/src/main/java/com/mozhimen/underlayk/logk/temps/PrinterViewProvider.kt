package com.mozhimen.underlayk.logk.temps

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.underlayk.R
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.underlayk.logk.commons.IPrinter
import com.mozhimen.underlayk.logk.mos.LogKConfig
import com.mozhimen.underlayk.logk.mos.LogKMo

/**
 * @ClassName ViewPrinterProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:24
 * @Version 1.0
 */
class PrinterViewProvider(
    private val _context: Context,
    private val _rootView: FrameLayout
) : IPrinter {
    private companion object {
        private const val TAG_LOGK_CONTAINER_VIEW = "TAG_LOGK_CONTAINER_VIEW"
        private const val TAG_LOGK_TITLE_VIEW = "TAG_LOGK_TITLE_VIEW"
        private const val TAG_LOGK_RECYCLER_VIEW = "TAG_LOGK_RECYCLER_VIEW"
        private val TITLE_OPEN_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_open)
        private val TITLE_CLOSE_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_close)
        private val LAYOUT_PARAMS_PANEL_OPEN = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UtilKScreen.getScreenHeight() / 3)
    }

    private var _adapter: DataKAdapter = DataKAdapter(_context)
    private var _isFold = false

    private var _recyclerView: RecyclerView? = null
        get() {
            if (field != null) return field

            val recyclerView = RecyclerView(_context)
            recyclerView.tag = TAG_LOGK_RECYCLER_VIEW
            recyclerView.layoutManager = LinearLayoutManager(_context)
            recyclerView.adapter = _adapter
            recyclerView.setBackgroundColor(Color.BLACK)
            return recyclerView.also { field = it }
        }

    private var _titleView: TextView? = null
        get() {
            if (field != null) return field

            val textView = TextView(_context)
            textView.tag = TAG_LOGK_TITLE_VIEW
            textView.gravity = Gravity.END
            textView.text = if (_isFold) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            textView.setTextColor(Color.WHITE)
            textView.setBackgroundColor(Color.BLACK)
            textView.setOnClickListener { if (_isFold) unfoldLogView() else foldLogView() }
            return textView.also { field = it }
        }

    private var _containerView: FrameLayout? = null
        get() {
            if (field != null) return field

            val containerView = FrameLayout(_context)
            containerView.tag = TAG_LOGK_CONTAINER_VIEW
            containerView.setBackgroundColor(Color.BLACK)
            val recyclerLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            containerView.addView(_recyclerView, recyclerLayoutParams)
            val titleLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            titleLayoutParams.gravity = Gravity.TOP or Gravity.END
            containerView.addView(_titleView, titleLayoutParams)
            return containerView.also { field = it }
        }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        //将log展示添加到recyclerView
        _adapter.addItem(PrinterViewItem(LogKMo(System.currentTimeMillis(), level, tag, printString)), true)
        //滚动到对应的位置
        _recyclerView!!.smoothScrollToPosition(_adapter.itemCount - 1)
    }

    fun showLogView() {
        if (_rootView.findViewWithTag<View?>(TAG_LOGK_CONTAINER_VIEW) != null) return
        //add to root
        val layoutParams = LAYOUT_PARAMS_PANEL_OPEN
        layoutParams.width = UtilKScreen.getScreenWidth()
        layoutParams.height = UtilKScreen.getScreenHeight() / 3
        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        _rootView.addView(_containerView, layoutParams)
    }

    fun closeLogView() {
        _rootView.removeView(_containerView)
    }

    fun foldLogView() {
        if (_containerView?.findViewWithTag<View?>(TAG_LOGK_RECYCLER_VIEW) == null || _isFold) return
        _isFold = true
        _titleView!!.text = TITLE_OPEN_PANEL
        _recyclerView!!.visibility = View.GONE
        //transform
        val layoutParams = _containerView!!.layoutParams as FrameLayout.LayoutParams
        layoutParams.width = _titleView!!.width
        layoutParams.height = _titleView!!.height
        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        _containerView!!.layoutParams = layoutParams
    }

    fun unfoldLogView() {
        if (_containerView?.findViewWithTag<View?>(TAG_LOGK_RECYCLER_VIEW) == null || !_isFold) return
        _isFold = false
        _titleView!!.text = TITLE_CLOSE_PANEL
        _recyclerView!!.visibility = View.VISIBLE
        //transform
        val layoutParams = _containerView!!.layoutParams as FrameLayout.LayoutParams
        layoutParams.width = UtilKScreen.getScreenWidth()
        layoutParams.height = UtilKScreen.getScreenHeight() / 3
        layoutParams.gravity = Gravity.BOTTOM or Gravity.END
        _containerView!!.layoutParams = layoutParams
    }
}