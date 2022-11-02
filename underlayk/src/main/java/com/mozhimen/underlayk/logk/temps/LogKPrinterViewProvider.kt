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
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.uicorek.datak.helpers.DataKAdapter
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.commons.ILogKPrinter
import com.mozhimen.underlayk.logk.commons.LogKConfig
import com.mozhimen.underlayk.logk.mos.MLogK

/**
 * @ClassName ViewPrinterProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 18:24
 * @Version 1.0
 */
class LogKPrinterViewProvider(
    private val _context: Context,
    private val _rootView: FrameLayout
) : ILogKPrinter {
    private companion object {
        private const val TAG_LOGK_CONTAINER_VIEW = "TAG_LOGK_CONTAINER_VIEW"
        private val TITLE_OPEN_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_open)
        private val TITLE_CLOSE_PANEL = UtilKRes.getString(R.string.logk_view_provider_title_close)
    }

    private var _adapter: DataKAdapter = DataKAdapter(_context)

    private var _recyclerView: RecyclerView? = null
        get() {
            if (field != null) return field
            val recyclerView = RecyclerView(_context)
            recyclerView.layoutManager = LinearLayoutManager(_context)
            recyclerView.adapter = _adapter
            recyclerView.setBackgroundColor(Color.BLACK)
            return recyclerView.also { field = it }
        }

    private var _titleView: TextView? = null
        get() {
            if (field != null) return field
            val textView = TextView(_context)
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
            titleLayoutParams.gravity = Gravity.TOP or Gravity.START
            containerView.addView(_titleView, titleLayoutParams)
            return containerView.also { field = it }
        }

    private var _isFold = false
        set(value) {
            _titleView!!.text = if (value) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            field = value
        }

    fun showLogView(isFold: Boolean) {
        if (_rootView.findViewWithTag<View?>(TAG_LOGK_CONTAINER_VIEW) != null) return
        if (isFold) foldLogView() else unfoldLogView()
        //add to root
        _rootView.addView(_containerView, getLayoutParams(isFold))
    }

    fun closeLogView() {
        if (_rootView.findViewWithTag<View?>(TAG_LOGK_CONTAINER_VIEW) == null) return
        _rootView.removeView(_containerView)
    }

    fun foldLogView() {
        if (_isFold) return
        _isFold = true
        _titleView!!.text = TITLE_OPEN_PANEL
        _recyclerView!!.visibility = View.GONE
        //transform
        _containerView!!.layoutParams = getLayoutParams(true)
    }

    fun unfoldLogView() {
        if (!_isFold) return
        _isFold = false
        _titleView!!.text = TITLE_CLOSE_PANEL
        _recyclerView!!.visibility = View.VISIBLE
        //transform
        _containerView!!.layoutParams = getLayoutParams(false)
    }

    override fun print(config: LogKConfig, level: Int, tag: String, printString: String) {
        //将log展示添加到recyclerView
        _adapter.addItem(PrinterViewItem(MLogK(System.currentTimeMillis(), level, tag, printString)), true)
        //滚动到对应的位置
        _recyclerView!!.smoothScrollToPosition(_adapter.itemCount - 1)
    }

    private fun getLayoutParams(isFold: Boolean): FrameLayout.LayoutParams {
        val layoutParams = (_containerView!!.layoutParams as? FrameLayout.LayoutParams?) ?: FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.START
        if (isFold) {
            if (_titleView!!.width == 0 || _titleView!!.height == 0) {
                _isFold = true
                return layoutParams
            }
            layoutParams.width = _titleView!!.width
            layoutParams.height = _titleView!!.height
        } else {
            layoutParams.width = UtilKScreen.getScreenWidth()
            layoutParams.height = UtilKScreen.getScreenHeight() / 3
        }
        return layoutParams
    }
}