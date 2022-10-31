package com.mozhimen.uicorek.tabk.top.mos

import androidx.fragment.app.Fragment
import com.mozhimen.basick.extsk.asColorTone
import com.mozhimen.uicorek.tabk.top.cons.ETabKTopType

/**
 * @ClassName TabKTopInfo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 22:16
 * @Version 1.0
 */
class MTabKTop {

    var name: String? = null
    var fragment: Class<out Fragment>? = null
    var bitmapDefault: Any? = null
    var bitmapSelected: Any? = null
    var colorDefault: Int? = null
    var colorSelected: Int? = null
    var colorIndicator: Int? = null
    var tabKType: ETabKTopType? = null

    /**
     * 纯图片
     * @param name String
     * @param bitmapDefault Any
     * @param bitmapSelected Any
     * @param colorIndicator Any
     * @constructor
     */
    constructor(
        name: String,
        bitmapDefault: Any,
        bitmapSelected: Any,
        colorIndicator: Any,
    ) {
        this.name = name
        this.bitmapDefault = bitmapDefault
        this.bitmapSelected = bitmapSelected
        this.colorIndicator = colorIndicator.asColorTone()
        this.tabKType = ETabKTopType.IMAGE
    }

    /**
     * 纯文本
     * @param name String
     * @param colorDefault Any
     * @param colorSelected Any
     * @constructor
     */
    constructor(
        name: String,
        colorDefault: Any,
        colorSelected: Any
    ) {
        this.name = name
        this.colorDefault = colorDefault.asColorTone()
        this.colorSelected = colorSelected.asColorTone()
        this.tabKType = ETabKTopType.TEXT
    }

    /**
     * 图片文字
     * @param name String
     * @param bitmapDefault Any
     * @param bitmapSelected Any
     * @param colorDefault Any
     * @param colorSelected Any
     * @constructor
     */
    constructor(
        name: String,
        bitmapDefault: Any,
        bitmapSelected: Any,
        colorDefault: Any,
        colorSelected: Any
    ) {
        this.name = name
        this.bitmapDefault = bitmapDefault
        this.bitmapSelected = bitmapSelected
        this.colorDefault = colorDefault.asColorTone()
        this.colorSelected = colorSelected.asColorTone()
        this.tabKType = ETabKTopType.IMAGE_TEXT
    }
}