package com.mozhimen.uicorektest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.btnk.BtnKActivity
import com.mozhimen.uicorektest.databinding.ActivityUicorekBinding
import com.mozhimen.uicorektest.dialogk.DialogKActivity
import com.mozhimen.uicorektest.layoutk.LayoutKActivity
import com.mozhimen.uicorektest.recyclerk.RecyclerKActivity
import com.mozhimen.uicorektest.textk.TextKActivity
import com.mozhimen.uicorektest.viewk.ViewKActivity

class UicoreKActivity : BaseKActivityVB<ActivityUicorekBinding>() {

    fun goBtnK(view: View) {
        start<BtnKActivity>()
    }

    fun goDialogK(view: View) {
        start<DialogKActivity>()
    }

    fun goLayoutK(view: View) {
        start<LayoutKActivity>()
    }

    fun goRecyclerK(view: View) {
        start<RecyclerKActivity>()
    }

    fun goTextK(view: View) {
        start<TextKActivity>()
    }

    fun goViewK(view: View) {
        start<ViewKActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}