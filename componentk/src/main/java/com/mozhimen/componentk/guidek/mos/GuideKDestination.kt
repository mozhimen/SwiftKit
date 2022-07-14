package com.mozhimen.componentk.guidek.mos

import java.io.Serializable

/**
 * @ClassName Destination
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/29 22:10
 * @Version 1.0
 */
internal data class GuideKDestination(
    val versionCode: Int,
    val indexDefault: Int,
    val pages: List<GuideKPage>
) : Serializable

internal data class GuideKPage(
    val enable: Boolean,
    val pageInfo: GuideKPageInfo,
    val tabConfig: GuideKTabConfig,
    val title: String
) : Serializable

data class GuideKPageInfo(
    val clazzName: String,
    val destType: String,
    val index: Int,
    val id: Int,
    val url: String
) : Serializable

internal data class GuideKTabConfig(
    val name: String,
    val bitmapDefault: String,
    val bitmapSelected: String,
    val iconColorDefault: String,
    val iconColorSelected: String,
    val iconFont: String,
    val iconNameDefault: String,
    val iconNameSelected: String,
    val type: String
) : Serializable