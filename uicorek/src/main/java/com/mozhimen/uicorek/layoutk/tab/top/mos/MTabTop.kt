package com.mozhimen.uicorek.layoutk.tab.top.mos

import androidx.fragment.app.Fragment
import com.mozhimen.basick.utilk.android.graphics.UtilKColor
import com.mozhimen.basick.utilk.kotlin.UtilKAnyFormat
import com.mozhimen.basick.utilk.kotlin.obj2intColor
import com.mozhimen.uicorek.layoutk.tab.top.cons.ETabTopType

/**
 * @ClassName MTabTop
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 22:16
 * @Version 1.0
 */
open class MTabTop {

    var name: String? = null
    var fragment: Class<out Fragment>? = null
    var bitmapDefault: Any? = null
    var bitmapSelected: Any? = null
    var colorDefault: Int? = null
    var colorSelected: Int? = null
    var colorIndicator: Int? = null
    var tabType: ETabTopType? = null

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
        this.colorIndicator = UtilKAnyFormat.obj2intColor(colorIndicator)
        this.tabType = ETabTopType.IMAGE
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
        this.colorDefault =UtilKAnyFormat.obj2intColor(colorDefault)
        this.colorSelected = UtilKAnyFormat.obj2intColor(colorSelected)
        this.tabType = ETabTopType.TEXT
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
        this.colorDefault = UtilKAnyFormat.obj2intColor(colorDefault)
        this.colorSelected = UtilKAnyFormat.obj2intColor(colorSelected)
        this.tabType = ETabTopType.IMAGE_TEXT
    }
}