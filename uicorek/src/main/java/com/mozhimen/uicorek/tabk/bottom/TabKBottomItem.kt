package com.mozhimen.uicorek.tabk.bottom

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.basick.basek.BaseKLayoutRelative
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.font
import com.mozhimen.basick.extsk.load
import com.mozhimen.basick.extsk.resizeSize
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorek.tabk.commons.ITabK

/**
 * @ClassName TabKBottom
 * @Description
 * 用法:
 * override fun onCreate(savedInstanceState: Bundle?) {
 *  ...
 *  setContentView(R.layout.activity_main)
 *  val tabBottom = findViewById<TabKBottom>(R.id.main_tab)
 *  val homeInfo = TabKBottomInfo("首页","fonts/iconfont.ttf",
 *      getString(R.string.icon_home),null,"#ff656667","#ffd44949")
 *  tabBottom._tabKBottomInfo = homeInfo;
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:22
 * @Version 1.0
 */
class TabKBottomItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseKLayoutRelative(context, attrs, defStyleAttr), ITabK<TabKBottomMo> {
    private lateinit var _tabImageView: ImageView
    private lateinit var _tabIconView: TextView
    private lateinit var _tabNameView: TextView
    private lateinit var _tabContainer: LinearLayout
    private var _tabKBottomMo: TabKBottomMo? = null

    init {
        initView()
    }

    /**
     * 设置tabInfo
     * @param tabMo TabKBottomInfo<*>
     */
    override fun setTabInfo(tabMo: TabKBottomMo) {
        this._tabKBottomMo = tabMo
        inflateInfo(false, true)
    }

    /**
     * 获取tabInfo
     * @return TabKBottomInfo<*>?
     */
    fun getTabInfo(): TabKBottomMo? = _tabKBottomMo

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
     * @param prevMo TabKBottomInfo<*>
     * @param nextMo TabKBottomInfo<*>
     */
    override fun onTabSelected(index: Int, prevMo: TabKBottomMo?, nextMo: TabKBottomMo) {
        if (prevMo != _tabKBottomMo && nextMo != _tabKBottomMo || prevMo == nextMo) {
            return
        }
        if (prevMo == _tabKBottomMo) {
            inflateInfo(false, false)
        } else {
            inflateInfo(true, false)
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.tabk_bottom, this)
        _tabContainer = findViewById(R.id.tabk_bottom_container)
        _tabImageView = findViewById(R.id.tabk_bottom_img)
        _tabIconView = findViewById(R.id.tabk_bottom_icon)
        _tabNameView = findViewById(R.id.tabk_bottom_name)
    }

    private fun inflateInfo(selected: Boolean, init: Boolean) {
        require(_tabKBottomMo != null) { "_tabKBottomInfo must not be null!" }
        if (_tabKBottomMo!!.tabKType == TabKBottomMo.TabKBottomType.ICONFONT_TEXT) {
            if (init) {
                _tabImageView.visibility = GONE
                _tabIconView.visibility = VISIBLE
                _tabNameView.visibility = VISIBLE
                _tabIconView.font(_tabKBottomMo!!.iconFont!!)
                if (!TextUtils.isEmpty(_tabKBottomMo!!.name)) {
                    _tabNameView.text = _tabKBottomMo!!.name
                }
            }
            if (selected) {
                _tabIconView.text =
                    if (TextUtils.isEmpty(_tabKBottomMo!!.iconNameSelected)) _tabKBottomMo!!.iconNameDefault else _tabKBottomMo!!.iconNameSelected
                _tabIconView.setTextColor(_tabKBottomMo!!.iconColorSelected!!)
                _tabNameView.setTextColor(_tabKBottomMo!!.iconColorSelected!!)
            } else {
                _tabIconView.text = _tabKBottomMo!!.iconNameDefault
                _tabIconView.setTextColor(_tabKBottomMo!!.iconColorDefault!!)
                _tabNameView.setTextColor(_tabKBottomMo!!.iconColorDefault!!)
            }
        } else if (_tabKBottomMo!!.tabKType == TabKBottomMo.TabKBottomType.IMAGE) {
            if (init) {
                _tabNameView.visibility = GONE
                _tabImageView.visibility = VISIBLE
                _tabIconView.visibility = GONE
            }
            if (selected) {
                _tabImageView.load(_tabKBottomMo!!.bitmapSelected!!)
                _tabImageView.resizeSize(58f.dp2px())
            } else {
                _tabImageView.load(_tabKBottomMo!!.bitmapDefault!!)
                _tabImageView.resizeSize(56f.dp2px())
            }
        } else if (_tabKBottomMo!!.tabKType == TabKBottomMo.TabKBottomType.IMAGE_TEXT) {
            if (init) {
                _tabIconView.visibility = GONE
                _tabImageView.visibility = VISIBLE
                _tabImageView.resizeSize(32f.dp2px())
                _tabNameView.visibility = VISIBLE
                if (!TextUtils.isEmpty(_tabKBottomMo!!.name)) {
                    _tabNameView.text = _tabKBottomMo!!.name
                }
            }
            if (selected) {
                _tabImageView.load(_tabKBottomMo!!.bitmapSelected!!)
                _tabNameView.setTextColor(_tabKBottomMo!!.iconColorSelected!!)
            } else {
                _tabImageView.load(_tabKBottomMo!!.bitmapDefault!!)
                _tabNameView.setTextColor(_tabKBottomMo!!.iconColorDefault!!)
            }
        }
    }

}