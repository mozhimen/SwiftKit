package com.mozhimen.uicorektest

import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
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

    fun goBtnK(view: View) {
        start<BtnKActivity>()
    }

    fun goDialogK(view: View) {
        start<DialogKActivity>()
    }

    fun goDrawableK(view: View) {
        start<DrawableKActivity>()
    }

    fun goImageK(view: View) {
        start<ImageKActivity>()
    }

    fun goLayoutK(view: View) {
        start<LayoutKActivity>()
    }

    fun goPopwinK(view: View) {
        start<PopwinKActivity>()
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

}