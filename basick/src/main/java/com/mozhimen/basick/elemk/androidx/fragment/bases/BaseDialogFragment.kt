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
        UtilKLogWrapper.v(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UtilKLogWrapper.v(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        UtilKLogWrapper.v(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        UtilKLogWrapper.v(TAG, "onResume")
    }

    override fun onPause() {
        UtilKLogWrapper.v(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        UtilKLogWrapper.v(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        UtilKLogWrapper.d(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        UtilKLogWrapper.v(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        UtilKLogWrapper.v(TAG, "onDetach")
        super.onDetach()
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        UtilKLogWrapper.v(TAG, "onHiddenChanged: hidden $hidden")
    }
}