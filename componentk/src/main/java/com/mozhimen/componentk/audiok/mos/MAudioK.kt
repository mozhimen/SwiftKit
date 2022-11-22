package com.mozhimen.componentk.audiok.mos


/**
 * @ClassName MAudioK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 14:14
 * @Version 1.0
 */
data class MAudioK(
    val name: String,//名字
    val url: String,//地址/url
    val id: String = url.hashCode().toString()
)