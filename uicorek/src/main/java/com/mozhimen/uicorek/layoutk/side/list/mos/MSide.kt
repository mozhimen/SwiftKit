package com.mozhimen.uicorek.layoutk.side.list.mos

import java.io.Serializable

/**
 * @ClassName MSide
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/14 17:03
 * @Version 1.0
 */
data class MSide(
    val menus: List<MSideMenu>
) : Serializable

data class MSideMenu(
    val menuId: String,
    val menuName: String,
    val menuSubs: List<MSideSubMenu>
) : Serializable

data class MSideSubMenu(
    val subId: String,
    val fatherId: String,
    val subName: String,
    val subContents: List<MSideSubContent>
) : Serializable

data class MSideSubContent(
    val contentId: String,
    val fatherId: String,
    val contentName: String,
    val contentImageUrl: String,
    val contentImageUrlPlaceholder: String
) : Serializable