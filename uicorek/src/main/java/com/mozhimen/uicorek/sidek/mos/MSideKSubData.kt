package com.mozhimen.uicorek.sidek.mos

import java.io.Serializable

/**
 * @ClassName SliderKDataMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/14 17:03
 * @Version 1.0
 */
data class MSideKSubData(
    val menus: List<MSliderKMenu>
) : Serializable

data class MSliderKMenu(
    val menuId: String,
    val menuName: String,
    val menuSubs: List<MSliderKSub>
) : Serializable

data class MSliderKSub(
    val subId: String,
    val fatherId: String,
    val subName: String,
    val subContents: List<MSliderKContent>
) : Serializable

data class MSliderKContent(
    val contentId: String,
    val fatherId: String,
    val contentName: String,
    val contentImageUrl: String,
    val contentImageUrlPlaceholder: String
) : Serializable