package com.mozhimen.uicorektest.adapterk

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.basick.utilk.view.bar.showToastLong
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB2
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.BR
import com.mozhimen.uicorektest.databinding.ActivityAdapterkRecyclerVb2Binding
import com.mozhimen.uicorektest.databinding.ItemAdapterkRecyclerVb2Binding


/**
 * @ClassName AdapterKRecyclerVB2Activity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/6 15:20
 * @Version 1.0
 */
class AdapterKRecyclerVB2Activity : BaseActivityVB<ActivityAdapterkRecyclerVb2Binding>() {
    private val _datas by lazy {
        listOf(
            MKey("0", "xxx"),
            MKey("1","xxx"),
            MKey("2","jjj"),
            MKey("3","xxx"),
            MKey("4","xxx"),
            MKey("5","xxx"),
            MKey("6","xxx"),
            MKey("7","xxx"),
            MKey("8","xxx"),
        )
    }
    private lateinit var _adapterRecyclerVb2: AdapterKRecyclerVB2<MKey, ItemAdapterkRecyclerVb2Binding>

    override fun initView(savedInstanceState: Bundle?) {
        _adapterRecyclerVb2 = AdapterKRecyclerVB2(
            _datas,
            R.layout.item_adapterk_recycler_vb2,
            BR.item_adapterk_recycler_vb2
        ) { holder: VHKRecyclerVB<ItemAdapterkRecyclerVb2Binding>, data: MKey, position: Int, _: Int ->
            holder.vb.itemAdapterkRecyclerVb2Name.setOnClickListener {
                "${position}: data:${data}".showToastLong()
            }
        }
        vb.adapterkRecyclerVb2Recycler.apply {
            layoutManager = LinearLayoutManager(this@AdapterKRecyclerVB2Activity)
            adapter = _adapterRecyclerVb2
        }
    }
}