package com.mozhimen.basick.elemk.androidx.appcompat.bases.official

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.utilk.bases.IUtilK

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
        Log.v(TAG, "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.v(TAG, "onResume: ")
    }

    override fun onPause() {
        Log.v(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.v(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        Log.v(TAG, "onDestroy: ")
        super.onDestroy()
    }
}