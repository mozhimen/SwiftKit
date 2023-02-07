package com.mozhimen.uicorektest.adapterk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.uicorektest.databinding.ActivityAdapterkSpinnerBinding


/**
 * @ClassName AdapterKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/6 17:03
 * @Version 1.0
 */
class AdapterKActivity : BaseActivityVB<ActivityAdapterkSpinnerBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.adapterkSpinner.setEntries(listOf("GTA6", "唱跳", "Rap")).onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                vb.adapterkSpinner.getSelectItem().toString().showToast()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}