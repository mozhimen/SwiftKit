package com.mozhimen.debugk

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest

/**
 * @ClassName DebugK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/27 12:03
 * @Version 1.0
 */
object DebugK {

    fun toggleDialog(fragmentManager: FragmentManager){
        try {
            val clazz = Class.forName("com.mozhimen.debugk.base.uis.DebugKDialogFragment")
            val target = clazz.getConstructor().newInstance() as DialogFragment
            target.show(fragmentManager, "debugk_dialog")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}