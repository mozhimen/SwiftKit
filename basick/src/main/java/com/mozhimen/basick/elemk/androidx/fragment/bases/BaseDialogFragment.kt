package com.mozhimen.basick.elemk.androidx.fragment.bases

import android.content.Context
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseDialogFragment
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/20
 * @Version 1.0
 */
open class BaseDialogFragment : DialogFragment(), IUtilK {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        UtilKLogWrapper.vt(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UtilKLogWrapper.vt(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        UtilKLogWrapper.vt(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        UtilKLogWrapper.vt(TAG, "onResume")
    }

    override fun onPause() {
        UtilKLogWrapper.vt(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        UtilKLogWrapper.vt(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        UtilKLogWrapper.dt(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        UtilKLogWrapper.vt(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        UtilKLogWrapper.vt(TAG, "onDetach")
        super.onDetach()
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        UtilKLogWrapper.vt(TAG, "onHiddenChanged: hidden $hidden")
    }
}