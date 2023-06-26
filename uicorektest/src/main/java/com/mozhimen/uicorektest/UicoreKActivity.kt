package com.mozhimen.uicorektest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.adapterk.AdapterKActivity
import com.mozhimen.uicorektest.btnk.BtnKActivity
import com.mozhimen.uicorektest.databinding.ActivityUicorekBinding
import com.mozhimen.uicorektest.dialogk.DialogKActivity
import com.mozhimen.uicorektest.drawablek.DrawableKActivity
import com.mozhimen.uicorektest.imagek.ImageKActivity
import com.mozhimen.uicorektest.layoutk.LayoutKActivity
import com.mozhimen.uicorektest.popwink.PopwinKActivity
import com.mozhimen.uicorektest.recyclerk.RecyclerKActivity
import com.mozhimen.uicorektest.textk.TextKActivity
import com.mozhimen.uicorektest.viewk.ViewKActivity

class UicoreKActivity : BaseActivityVB<ActivityUicorekBinding>() {

    fun goAdapterK(view: View) {
        startContext<AdapterKActivity>()
    }

    fun goBtnK(view: View) {
        startContext<BtnKActivity>()
    }

    fun goDialogK(view: View) {
        startContext<DialogKActivity>()
    }

    fun goDrawableK(view: View) {
        startContext<DrawableKActivity>()
    }

    fun goImageK(view: View) {
        startContext<ImageKActivity>()
    }

    fun goLayoutK(view: View) {
        startContext<LayoutKActivity>()
    }

    fun goPopwinK(view: View) {
        startContext<PopwinKActivity>()
    }

    fun goRecyclerK(view: View) {
        startContext<RecyclerKActivity>()
    }

    fun goTextK(view: View) {
        startContext<TextKActivity>()
    }

    fun goViewK(view: View) {
        startContext<ViewKActivity>()
    }

}