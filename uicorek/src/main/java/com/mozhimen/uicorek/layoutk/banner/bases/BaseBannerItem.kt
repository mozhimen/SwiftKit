package com.mozhimen.uicorek.layoutk.banner.bases

/**
 * @ClassName MBannerItem
 * @Description LayoutKBanner的实体类,继承该实体类来实现自己的Mo
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 23:08
 * @Version 1.0
 */
open class BaseBannerItem {
    open var name: String = ""
    open var url: String = ""

    constructor(url: String) : this("", url)

    constructor(name: String, url: String) {
        this.name = name
        this.url = url
    }
}