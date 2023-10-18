package com.mozhimen.uicorek.layoutk.tab.bottom.mos

import androidx.fragment.app.Fragment
import com.mozhimen.basick.utilk.android.graphics.UtilKColor
import com.mozhimen.basick.utilk.kotlin.UtilKAnyFormat
import com.mozhimen.basick.utilk.kotlin.str2strUnicode
import com.mozhimen.uicorek.layoutk.tab.bottom.cons.ETabBottomType
import java.io.Serializable

/**
 * @ClassName MTabBottom
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:24
 * @Version 1.0
 */
class MTabBottom : Serializable {

    var fragment: Class<out Fragment>? = null
    var name: String? = null
    var bitmapDefault: Any? = null
    var bitmapSelected: Any? = null
    var iconFont: String? = null//在Java代码中直接设置iconFont字符串无效,需要定义在string.xml中
    var iconNameDefault: String? = null
    var iconNameSelected: String? = null
    var iconColorDefault: Int? = null
    var iconColorSelected: Int? = null
    var tabType: ETabBottomType? = null

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
        this.tabType = ETabBottomType.IMAGE
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
        this.iconColorDefault = UtilKAnyFormat.obj2intColor(iconColorDefault)
        this.iconColorSelected = UtilKAnyFormat.obj2intColor(iconColorSelected)
        this.tabType = ETabBottomType.IMAGE_TEXT
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
        this.iconNameDefault = iconNameDefault.str2strUnicode()
        this.iconNameSelected = iconNameSelected.str2strUnicode()
        this.iconColorDefault = UtilKAnyFormat.obj2intColor(iconColorDefault)
        this.iconColorSelected = UtilKAnyFormat.obj2intColor(iconColorSelected)
        this.tabType = ETabBottomType.ICONFONT_TEXT
    }
}

