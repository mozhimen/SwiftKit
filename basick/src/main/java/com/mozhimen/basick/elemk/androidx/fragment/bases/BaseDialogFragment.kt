package com.mozhimen.basick.elemk.androidx.fragment.bases

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.mozhimen.basick.utilk.bases.IUtilK

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
        Log.v(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume")
    }

    override fun onPause() {
        Log.v(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.v(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.v(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.v(TAG, "onDetach")
        super.onDetach()
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        Log.v(TAG, "onHiddenChanged: hidden $hidden")
    }
}