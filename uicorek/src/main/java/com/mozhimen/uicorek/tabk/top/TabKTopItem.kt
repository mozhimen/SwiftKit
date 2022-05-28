package com.mozhimen.uicorek.tabk.top

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.basek.BaseKLayoutRelative
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.fontStyle
import com.mozhimen.basick.extsk.load
import com.mozhimen.basick.extsk.resizeSize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.tabk.commons.ITabK
import com.mozhimen.uicorek.tabk.top.mos.TabKTopMo

/**
 * @ClassName TabKTop
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 22:23
 * @Version 1.0
 */
class TabKTopItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseKLayoutRelative(context, attrs, defStyleAttr), ITabK<TabKTopMo> {

    private var _tabTopMo: TabKTopMo? = null
    private lateinit var _tabImageView: ImageView
    private lateinit var _tabNameView: TextView
    private lateinit var _tabIndicator: View

    init {
        initView()
    }

    /**
     * 设置TabInfo
     * @param tabMo TabKTopInfo
     */
    override fun setTabInfo(tabMo: TabKTopMo) {
        this._tabTopMo = tabMo
        inflateInfo(false, true)
    }

    /**
     * 获取TabInfo
     * @return TabKTopInfo?
     */
    fun getTabKInfo(): TabKTopMo? = _tabTopMo

    /**
     * 获取imageView
     * @return ImageView
     */
    fun getTabImageView(): ImageView = _tabImageView

    /**
     * 获取TitleView
     * @return TextView
     */
    fun getTabNameView(): TextView = _tabNameView

    /**
     * 获取indicator
     * @return View
     */
    fun getIndicator(): View = _tabIndicator

    /**
     * 改变某个Tab的高度
     * @param height Int
     */
    override fun resetTabHeight(height: Int) {
        val layoutParams = layoutParams
        layoutParams.height = height
        setLayoutParams(layoutParams)
        getTabNameView().visibility = GONE
    }

    /**
     *
     * @param index Int
     * @param prevMo TabKTopInfo?
     * @param nextMo TabKTopInfo
     */
    override fun onTabSelected(index: Int, prevMo: TabKTopMo?, nextMo: TabKTopMo) {
        if (prevMo != _tabTopMo && nextMo != _tabTopMo || prevMo == nextMo) {
            return
        }
        if (prevMo == _tabTopMo) {
            inflateInfo(false, false)
        } else {
            inflateInfo(true, false)
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.tabk_top, this)
        _tabImageView = findViewById(R.id.tabk_top_img)
        _tabNameView = findViewById(R.id.tabk_top_name)
        _tabIndicator = findViewById(R.id.tabk_top_indicator)
    }

    private fun inflateInfo(selected: Boolean, init: Boolean) {
        requireNotNull(_tabTopMo) { "tabInfo must not be null!" }
        if (_tabTopMo!!.tabKType == TabKTopMo.TabKTopType.TEXT) {
            if (init) {
                _tabImageView.visibility = GONE
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabTopMo!!.name)) {
                    _tabNameView.text = _tabTopMo!!.name
                }
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopMo!!.colorSelected!!)
                _tabNameView.setTextColor(_tabTopMo!!.colorSelected!!)
                _tabNameView.textSize = 17f
                _tabNameView.fontStyle(Typeface.BOLD)
            } else {
                _tabIndicator.visibility = GONE
                _tabNameView.setTextColor(_tabTopMo!!.colorDefault!!)
                _tabNameView.textSize = 16f
                _tabNameView.fontStyle(Typeface.NORMAL)
            }
        } else if (_tabTopMo!!.tabKType == TabKTopMo.TabKTopType.IMAGE) {
            if (init) {
                _tabImageView.visibility = VISIBLE
                _tabNameView.visibility = GONE
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopMo!!.colorIndicator!!)
                _tabImageView.load(_tabTopMo!!.bitmapSelected!!)
                _tabImageView.resizeSize(27f.dp2px())
            } else {
                _tabIndicator.visibility = GONE
                _tabImageView.load(_tabTopMo!!.bitmapDefault!!)
                _tabImageView.resizeSize(26f.dp2px())
            }
        } else if (_tabTopMo!!.tabKType == TabKTopMo.TabKTopType.IMAGE_TEXT) {
            if (init) {
                _tabImageView.visibility = VISIBLE
                _tabImageView.setPadding(0, 0, 4f.dp2px(), 0)
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabTopMo!!.name)) {
                    _tabNameView.text = _tabTopMo!!.name
                }
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopMo!!.colorSelected!!)
                _tabImageView.load(_tabTopMo!!.bitmapSelected!!)
                _tabImageView.resizeSize(25f.dp2px())
                _tabNameView.setTextColor(_tabTopMo!!.colorSelected ?: _tabTopMo!!.colorDefault!!)
                _tabNameView.textSize = 17f
                _tabNameView.fontStyle(Typeface.BOLD)
            } else {
                _tabIndicator.visibility = GONE
                _tabImageView.load(_tabTopMo!!.bitmapDefault!!)
                _tabImageView.resizeSize(24f.dp2px())
                _tabNameView.setTextColor(_tabTopMo!!.colorDefault!!)
                _tabNameView.textSize = 16f
                _tabNameView.fontStyle(Typeface.NORMAL)
            }
        }
    }
}