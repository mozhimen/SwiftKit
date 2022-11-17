package com.mozhimen.uicorek.layoutk.tab.bottom

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.mozhimen.uicorek.layoutk.commons.LayoutKRelative
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.view.font
import com.mozhimen.basick.extsk.view.resizeSize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.tab.bottom.cons.ETabBottomType
import com.mozhimen.uicorek.layoutk.tab.bottom.mos.MTabBottom
import com.mozhimen.uicorek.layoutk.tab.commons.ITabItem

/**
 * @ClassName TabBottomItem
 * @Description
 * 用法:
 * override fun onCreate(savedInstanceState: Bundle?) {
 *  ...
 *  setContentView(R.layout.activity_main)
 *  val tabBottom = findViewById<TabBottom>(R.id.main_tab)
 *  val homeInfo = TabBottomInfo("首页","fonts/iconfont.ttf",
 *      getString(R.string.icon_home),null,"#ff656667","#ffd44949")
 *  tabBottom._tabBottomInfo = homeInfo;
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:22
 * @Version 1.0
 */
class TabBottomItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LayoutKRelative(context, attrs, defStyleAttr), ITabItem<MTabBottom> {
    private lateinit var _tabImageView: ImageView
    private lateinit var _tabIconView: TextView
    private lateinit var _tabNameView: TextView
    private lateinit var _tabContainer: LinearLayout
    private var _tabBottomItem: MTabBottom? = null

    init {
        initView()
    }

    /**
     * 设置tabInfo
     * @param tabMo MTabBottom<*>
     */
    override fun setTabItem(tabMo: MTabBottom) {
        this._tabBottomItem = tabMo
        inflateInfo(selected = false, init = true)
    }

    /**
     * 获取tabInfo
     * @return MTabBottom<*>?
     */
    fun getTabInfo(): MTabBottom? = _tabBottomItem

    /**
     * 获取图片布局
     * @return ImageView
     */
    fun getTabImageView(): ImageView = _tabImageView

    /**
     * 获取icon布局
     * @return TextView
     */
    fun getTabIconView(): TextView = _tabIconView

    /**
     * 获取title布局
     * @return TextView
     */
    fun getTabNameView(): TextView = _tabNameView

    /**
     * 获取容器布局
     * @return LinearLayout
     */
    fun getContainer(): LinearLayout = _tabContainer

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
     * @param prevItem MTabBottom<*>
     * @param currentItem MTabBottom<*>
     */
    override fun onTabItemSelected(index: Int, prevItem: MTabBottom?, currentItem: MTabBottom) {
        if (prevItem != _tabBottomItem && currentItem != _tabBottomItem || prevItem == currentItem) {
            return
        }
        if (prevItem == _tabBottomItem) {
            inflateInfo(selected = false, init = false)
        } else {
            inflateInfo(selected = true, init = false)
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_tab_bottom, this)
        _tabContainer = findViewById(R.id.layoutk_tab_bottom_container)
        _tabImageView = findViewById(R.id.layoutk_tab_bottom_img)
        _tabIconView = findViewById(R.id.layoutk_tab_bottom_icon)
        _tabNameView = findViewById(R.id.layoutk_tab_bottom_name)
    }

    private fun inflateInfo(selected: Boolean, init: Boolean) {
        require(_tabBottomItem != null) { "_tabBottomInfo must not be null!" }
        if (_tabBottomItem!!.tabType == ETabBottomType.ICONFONT_TEXT) {
            if (init) {
                _tabImageView.visibility = GONE
                _tabIconView.visibility = VISIBLE
                _tabNameView.visibility = VISIBLE
                _tabIconView.font(_tabBottomItem!!.iconFont!!)
                if (!TextUtils.isEmpty(_tabBottomItem!!.name)) {
                    _tabNameView.text = _tabBottomItem!!.name
                }
            }
            if (selected) {
                _tabIconView.text =
                    if (TextUtils.isEmpty(_tabBottomItem!!.iconNameSelected)) _tabBottomItem!!.iconNameDefault else _tabBottomItem!!.iconNameSelected
                _tabIconView.setTextColor(_tabBottomItem!!.iconColorSelected!!)
                _tabNameView.setTextColor(_tabBottomItem!!.iconColorSelected!!)
            } else {
                _tabIconView.text = _tabBottomItem!!.iconNameDefault
                _tabIconView.setTextColor(_tabBottomItem!!.iconColorDefault!!)
                _tabNameView.setTextColor(_tabBottomItem!!.iconColorDefault!!)
            }
        } else if (_tabBottomItem!!.tabType == ETabBottomType.IMAGE) {
            if (init) {
                _tabNameView.visibility = GONE
                _tabImageView.visibility = VISIBLE
                _tabIconView.visibility = GONE
            }
            if (selected) {
                _tabImageView.load(_tabBottomItem!!.bitmapSelected!!)
                _tabImageView.resizeSize(58f.dp2px())
            } else {
                _tabImageView.load(_tabBottomItem!!.bitmapDefault!!)
                _tabImageView.resizeSize(56f.dp2px())
            }
        } else if (_tabBottomItem!!.tabType == ETabBottomType.IMAGE_TEXT) {
            if (init) {
                _tabIconView.visibility = GONE
                _tabImageView.visibility = VISIBLE
                _tabImageView.resizeSize(32f.dp2px())
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabBottomItem!!.name)) {
                    _tabNameView.text = _tabBottomItem!!.name
                }
            }
            if (selected) {
                _tabImageView.load(_tabBottomItem!!.bitmapSelected!!)
                _tabNameView.setTextColor(_tabBottomItem!!.iconColorSelected!!)
            } else {
                _tabImageView.load(_tabBottomItem!!.bitmapDefault!!)
                _tabNameView.setTextColor(_tabBottomItem!!.iconColorDefault!!)
            }
        }
    }

}