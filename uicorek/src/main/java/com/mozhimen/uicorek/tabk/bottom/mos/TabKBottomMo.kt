package com.mozhimen.uicorek.tabk.bottom.mos

import androidx.fragment.app.Fragment
import com.mozhimen.basicsk.extsk.asColorTone
import com.mozhimen.basicsk.extsk.string2Unicode
import java.io.Serializable

/**
 * @ClassName TabKBottomInfo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:24
 * @Version 1.0
 */
class TabKBottomMo : Serializable {
    /**
     * IMAGE: 带Image的Tab, BITMAP: 纯ImageTab, ICON: IconFont的Tab
     */
    enum class TabKBottomType {
        IMAGE_TEXT,
        IMAGE,
        ICONFONT_TEXT
    }

    var fragment: Class<out Fragment>? = null
    var name: String? = null
    var bitmapDefault: Any? = null
    var bitmapSelected: Any? = null
    var iconFont: String? = null//在Java代码中直接设置iconFont字符串无效,需要定义在string.xml中
    var iconNameDefault: String? = null
    var iconNameSelected: String? = null
    var iconColorDefault: Int? = null
    var iconColorSelected: Int? = null
    var tabKType: TabKBottomType? = null

    /**
     * 纯图片Bitmap
     * @param name String
     * @param bitmapDefault Any
     * @param bitmapSelected Any
     * @constructor
     */
    constructor(
        name: String,
        bitmapDefault: Any,
        bitmapSelected: Any
    ) {
        this.name = name
        this.bitmapDefault = bitmapDefault
        this.bitmapSelected = bitmapSelected
        this.tabKType = TabKBottomType.IMAGE
    }

    /**
     * 图片标题
     * @param name String
     * @param bitmapDefault Any
     * @param bitmapSelected Any
     * @param iconColorDefault Any
     * @param iconColorSelected Any
     * @constructor
     */
    constructor(
        name: String,
        bitmapDefault: Any,
        bitmapSelected: Any,
        iconColorDefault: Any,
        iconColorSelected: Any
    ) {
        this.name = name
        this.bitmapDefault = bitmapDefault
        this.bitmapSelected = bitmapSelected
        this.iconColorDefault = iconColorDefault.asColorTone()
        this.iconColorSelected = iconColorSelected.asColorTone()
        this.tabKType = TabKBottomType.IMAGE_TEXT
    }

    /**
     * iconfont格式
     * @param name String
     * @param iconFont String
     * @param iconNameDefault String
     * @param iconNameSelected String
     * @param iconColorDefault Any
     * @param iconColorSelected Any
     * @constructor
     */
    constructor(
        name: String,
        iconFont: String,
        iconNameDefault: String,
        iconNameSelected: String,
        iconColorDefault: Any,
        iconColorSelected: Any
    ) {
        this.name = name
        this.iconFont = iconFont
        this.iconNameDefault = iconNameDefault.string2Unicode()
        this.iconNameSelected = iconNameSelected.string2Unicode()
        this.iconColorDefault = iconColorDefault.asColorTone()
        this.iconColorSelected = iconColorSelected.asColorTone()
        this.tabKType = TabKBottomType.ICONFONT_TEXT
    }
}

