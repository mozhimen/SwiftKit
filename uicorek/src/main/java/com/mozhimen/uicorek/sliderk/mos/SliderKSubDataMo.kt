package com.mozhimen.uicorek.sliderk.mos

import java.io.Serializable

/**
 * @ClassName SliderKDataMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/14 17:03
 * @Version 1.0
 */
data class SliderKDataMo(
    val menus: List<SliderKMenuMo>
) : Serializable

data class SliderKMenuMo(
    val menuId: String,
    val menuName: String,
    val menuSubs: List<SliderKSubMo>
) : Serializable

data class SliderKSubMo(
    val subId: String,
    val fatherId: String,
    val subName: String,
    val subContents: List<SliderKContentMo>
) : Serializable

data class SliderKContentMo(
    val contentId: String,
    val fatherId: String,
    val contentName: String,
    val contentImageUrl: String,
    val contentImageUrlPlaceholder: String
) : Serializable