package com.mozhimen.uicorektest.recyclerk

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerVB
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.BR
import com.mozhimen.uicorektest.databinding.ActivityRecyclerkLifecycleBinding
import com.mozhimen.uicorektest.databinding.ItemRecyclerkLifecycleBinding

class RecyclerKLifecycleActivity : BaseActivityVB<ActivityRecyclerkLifecycleBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        val list = listOf(MKey("1", "1"), MKey("2", "2"))
        vb.recyclerkLifecycle.bindLifecycle(this)
        vb.recyclerkLifecycle.layoutManager = LinearLayoutManager(this)
        vb.recyclerkLifecycle.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        vb.recyclerkLifecycle.adapter =
            AdapterKRecyclerVB<MKey, ItemRecyclerkLifecycleBinding>(list, R.layout.item_recyclerk_lifecycle, BR.item_recyclerk_lifecycle) { holder, _, position, _ ->
                holder.vb.itemRecyclerkLifecycleBox.setOnClickListener {
                    position.toString().showToast()
                }
            }.apply {
                onItemSelected(0)
            }
    }

}