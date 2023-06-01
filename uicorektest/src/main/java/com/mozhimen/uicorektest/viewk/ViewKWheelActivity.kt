package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorek.viewk.wheel.temps.ArrayWheelAdapter
import com.mozhimen.uicorektest.databinding.ActivityViewkWheelBinding

/**
 * @ClassName ViewKWheelActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/11 23:58
 * @Version 1.0
 */
class ViewKWheelActivity : BaseActivityVB<ActivityViewkWheelBinding>() {
    private val _items = listOf("item0", "item1", "item2", "item3", "item4")
    override fun initView(savedInstanceState: Bundle?) {
        vb.viewkWheel.setCyclic(false)
        vb.viewkWheel.adapter = ArrayWheelAdapter(_items)
        vb.viewkWheel.setItemSelectedListener {
            Log.v(TAG, "initView: selected $it")
        }
    }
}