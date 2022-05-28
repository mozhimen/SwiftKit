package com.mozhimen.uicorek.refreshk.customs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.mozhimen.basick.extsk.startRotate
import com.mozhimen.basick.extsk.stopAnim
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.refreshk.commons.RefreshKOverView

/**
 * @ClassName TextOverView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/17 23:52
 * @Version 1.0
 */
class TextOverView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RefreshKOverView(context, attrs, defStyleAttr) {
    private lateinit var _titleView: TextView
    private lateinit var _animView: View

    override fun init() {
        LayoutInflater.from(context).inflate(R.layout.refreshk_overview_text, this, true)
        _titleView = findViewById(R.id.refreshk_overview_text_title)
        _animView = findViewById(R.id.refreshk_overview_text_img)
    }

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {}

    override fun onVisible() {
        _titleView.text = UtilKRes.getString(R.string.refreshk_visible)
    }

    override fun onOverflow() {
        _titleView.text = UtilKRes.getString(R.string.refreshk_overflow)
    }

    override fun onStartRefresh() {
        _titleView.text = UtilKRes.getString(R.string.refreshk_refreshing)
        _animView.startRotate()
    }

    override fun onFinish() {
        _animView.stopAnim()
    }
}