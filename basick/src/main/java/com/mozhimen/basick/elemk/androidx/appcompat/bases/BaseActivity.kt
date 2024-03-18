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
        UtilKLogWrapper.v(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        UtilKLogWrapper.v(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        UtilKLogWrapper.v(TAG, "onResume: ")
    }

    override fun onPause() {
        UtilKLogWrapper.v(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        UtilKLogWrapper.v(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        UtilKLogWrapper.v(TAG, "onDestroy: ")
        super.onDestroy()
    }
}