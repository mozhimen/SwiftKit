package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.mok.MoKKey
import com.mozhimen.uicorektest.databinding.ActivityLayoutkChipGroupBinding

/**
 * @ClassName LayoutKChipGroupActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 23:41
 * @Version 1.0
 */
class LayoutKChipGroupActivity : BaseKActivityVB<ActivityLayoutkChipGroupBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkChips.bindKeys(
            arrayListOf(
                MoKKey("0", "赛博朋克2077"),
                MoKKey("1", "老头环"),
                MoKKey("2", "塞尔达"),
                MoKKey("3", "使命召唤19"),
                MoKKey("4", "全战三国"),
                MoKKey("5", "荒野大镖客"),
                MoKKey("6", "GTA6"),
                MoKKey("7", "文明6")
            )
        )
        vb.layoutkChips.setOnCheckedListener { _, i, dataKKey ->
            "index: $i dataKey: ${dataKKey.id} ${dataKKey.key}".showToast()
        }
        vb.layoutkChipsAdd.setOnClickListener {
            vb.layoutkChips.addKey(MoKKey("ss", "原神"))
        }
        vb.layoutkChipsRemove.setOnClickListener {
            vb.layoutkChips.removeKey(MoKKey("ss", "原神"))
        }
    }
}