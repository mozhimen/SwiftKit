package com.mozhimen.uicorek.layoutk.tab.top

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKRelative
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.fontStyle
import com.mozhimen.basick.utilk.exts.loadImage
import com.mozhimen.basick.utilk.exts.resizeSize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.tab.commons.ITabItem
import com.mozhimen.uicorek.layoutk.tab.top.cons.ETabTopType
import com.mozhimen.uicorek.layoutk.tab.top.mos.MTabTop

/**
 * @ClassName TabTopItem
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 22:23
 * @Version 1.0
 */
class TabTopItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayoutKRelative(context, attrs, defStyleAttr), ITabItem<MTabTop> {

    private var _tabTopItem: MTabTop? = null
    private lateinit var _tabImageView: ImageView
    private lateinit var _tabNameView: TextView
    private lateinit var _tabIndicator: View

    init {
        initView()
    }

    /**
     * 设置TabInfo
     * @param tabMo MTabTop
     */
    override fun setTabItem(tabMo: MTabTop) {
        this._tabTopItem = tabMo
        inflateInfo(false, true)
    }

    /**
     * 获取TabInfo
     * @return MTabTop?
     */
    fun getTabInfo(): MTabTop? = _tabTopItem

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
     * @param prevMo MTabTop?
     * @param nextMo MTabTop
     */
    override fun onTabItemSelected(index: Int, prevMo: MTabTop?, nextMo: MTabTop) {
        if (prevMo != _tabTopItem && nextMo != _tabTopItem || prevMo == nextMo) {
            return
        }
        if (prevMo == _tabTopItem) {
            inflateInfo(false, false)
        } else {
            inflateInfo(true, false)
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_tab_top, this)
        _tabImageView = findViewById(R.id.layoutk_tab_top_img)
        _tabNameView = findViewById(R.id.layoutk_tab_top_name)
        _tabIndicator = findViewById(R.id.layoutk_tab_top_indicator)
    }

    @Throws(Exception::class)
    private fun inflateInfo(selected: Boolean, init: Boolean) {
        requireNotNull(_tabTopItem) { "$TAG _tabTopItem must not be null!" }
        if (_tabTopItem!!.tabType == ETabTopType.TEXT) {
            if (init) {
                _tabImageView.visibility = GONE
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabTopItem!!.name)) {
                    _tabNameView.text = _tabTopItem!!.name
                }
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopItem!!.colorSelected!!)
                _tabNameView.setTextColor(_tabTopItem!!.colorSelected!!)
                _tabNameView.textSize = 17f
                _tabNameView.fontStyle(Typeface.BOLD)
            } else {
                _tabIndicator.visibility = GONE
                _tabNameView.setTextColor(_tabTopItem!!.colorDefault!!)
                _tabNameView.textSize = 16f
                _tabNameView.fontStyle(Typeface.NORMAL)
            }
        } else if (_tabTopItem!!.tabType == ETabTopType.IMAGE) {
            if (init) {
                _tabImageView.visibility = VISIBLE
                _tabNameView.visibility = GONE
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopItem!!.colorIndicator!!)
                _tabImageView.loadImage(_tabTopItem!!.bitmapSelected!!)
                _tabImageView.resizeSize(27f.dp2px())
            } else {
                _tabIndicator.visibility = GONE
                _tabImageView.loadImage(_tabTopItem!!.bitmapDefault!!)
                _tabImageView.resizeSize(26f.dp2px())
            }
        } else if (_tabTopItem!!.tabType == ETabTopType.IMAGE_TEXT) {
            if (init) {
                _tabImageView.visibility = VISIBLE
                _tabImageView.setPadding(0, 0, 4f.dp2px(), 0)
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabTopItem!!.name)) {
                    _tabNameView.text = _tabTopItem!!.name
                }
            }
            if (selected) {
                _tabIndicator.visibility = VISIBLE
                _tabIndicator.setBackgroundColor(_tabTopItem!!.colorSelected!!)
                _tabImageView.loadImage(_tabTopItem!!.bitmapSelected!!)
                _tabImageView.resizeSize(25f.dp2px())
                _tabNameView.setTextColor(_tabTopItem!!.colorSelected ?: _tabTopItem!!.colorDefault!!)
                _tabNameView.textSize = 17f
                _tabNameView.fontStyle(Typeface.BOLD)
            } else {
                _tabIndicator.visibility = GONE
                _tabImageView.loadImage(_tabTopItem!!.bitmapDefault!!)
                _tabImageView.resizeSize(24f.dp2px())
                _tabNameView.setTextColor(_tabTopItem!!.colorDefault!!)
                _tabNameView.textSize = 16f
                _tabNameView.fontStyle(Typeface.NORMAL)
            }
        }
    }
}