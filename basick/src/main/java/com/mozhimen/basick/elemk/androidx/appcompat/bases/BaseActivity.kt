package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/20
 * @Version 1.0
 */
open class BaseActivity : AppCompatActivity(), IUtilK {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UtilKLogWrapper.vt(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        UtilKLogWrapper.vt(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        UtilKLogWrapper.vt(TAG, "onResume: ")
    }

    override fun onPause() {
        UtilKLogWrapper.vt(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        UtilKLogWrapper.vt(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        UtilKLogWrapper.vt(TAG, "onDestroy: ")
        super.onDestroy()
    }
}