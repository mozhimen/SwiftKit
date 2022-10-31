package com.mozhimen.uicorektest.sidek

import android.os.Bundle
import android.widget.Toast
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.uicorek.itemk.ItemKViewHolder
import com.mozhimen.uicorek.sidek.ISideKSubItemListener
import com.mozhimen.uicorek.sidek.mos.MSliderKContent
import com.mozhimen.uicorek.sidek.mos.SliderKDataMo
import com.mozhimen.uicorek.sidek.mos.MSliderKMenu
import com.mozhimen.uicorek.sidek.mos.MSliderKSub
import com.mozhimen.uicorektest.databinding.ActivitySidekBinding

class SideKActivity : BaseKActivityVB<ActivitySidekBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val mo = SliderKDataMo(
            listOf(
                MSliderKMenu(
                    "00", "女装", listOf(
                        MSliderKSub(
                            "0000", "00", "上装", listOf(
                                MSliderKContent(
                                    "000000",
                                    "0000",
                                    "经典款式",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000001",
                                    "0000",
                                    "新品上市",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000002",
                                    "0000",
                                    "工厂店",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000002",
                                    "0000",
                                    "小米有品",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000003",
                                    "0000",
                                    "网易优选",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000003",
                                    "0000",
                                    "天猫精品",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                )
                            )
                        ),
                        MSliderKSub(
                            "0001", "00", "裙子", listOf(
                                MSliderKContent(
                                    "000100",
                                    "0001",
                                    "经典款式",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000101",
                                    "0001",
                                    "新品上市",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000102",
                                    "0001",
                                    "工厂店",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000102",
                                    "0001",
                                    "小米有品",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000103",
                                    "0001",
                                    "网易优选",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "000103",
                                    "0001",
                                    "天猫精品",
                                    "https://images.pexels.com/photos/2709388/pexels-photo-2709388.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                )
                            )
                        )
                    )
                ),
                MSliderKMenu(
                    "01", "男装", listOf(
                        MSliderKSub(
                            "0100", "01", "上装", listOf(
                                MSliderKContent(
                                    "010000",
                                    "0100",
                                    "经典款式",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010001",
                                    "0100",
                                    "新品上市",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010002",
                                    "0100",
                                    "工厂店",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010002",
                                    "0100",
                                    "小米有品",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010003",
                                    "0100",
                                    "网易优选.............................",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010003",
                                    "0100",
                                    "天猫精品",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                )
                            )
                        ),
                        MSliderKSub(
                            "0101", "01", "裙子", listOf(
                                MSliderKContent(
                                    "010100",
                                    "0101",
                                    "经典款式",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010101",
                                    "0101",
                                    "新品上市",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010102",
                                    "0101",
                                    "工厂店",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010102",
                                    "0101",
                                    "小米有品",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010103",
                                    "0101",
                                    "网易优选",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                ),
                                MSliderKContent(
                                    "010103",
                                    "0101",
                                    "天猫精品",
                                    "https://images.pexels.com/photos/8721987/pexels-photo-8721987.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                                    ""
                                )
                            )
                        )
                    )
                )
            )
        )
        vb.sidekSidebar.bindData(mo, spanCount = 3, listener = object : ISideKSubItemListener {
            override fun invoke(holder: ItemKViewHolder, contentMo: MSliderKContent?) {
                "$contentMo".showToast(Toast.LENGTH_LONG)
            }
        })
    }
}