package com.mozhimen.uicorektest.textk

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.uicorek.textk.edit.bar.TextKEditBarActivity
import com.mozhimen.uicorek.textk.edit.bar.commons.ITextKEditBarListener
import com.mozhimen.uicorektest.databinding.ActivityTextkEditBinding

class TextKEditActivity : BaseActivityVB<ActivityTextkEditBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    fun goTextKEditBar(view: View) {
        TextKEditBarActivity.openEditBarDefault(this, object : ITextKEditBarListener {
            override fun onCancel() {
            }

            override fun onIllegal() {
            }

            override fun onSubmit(content: String?) {
                content?.showToast()
            }

            override fun onAttached(rootView: ViewGroup?) {
            }

        }, null)
    }
}