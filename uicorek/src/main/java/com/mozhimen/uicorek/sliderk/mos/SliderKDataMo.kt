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
    val menuContents: List<SliderKContentMo>
) : Serializable

data class SliderKContentMo(
    val contentId: String,
    val contentName: String,
    val contentImageUrl: String,
    val contentImageUrlPlaceholder: String
) : Serializable