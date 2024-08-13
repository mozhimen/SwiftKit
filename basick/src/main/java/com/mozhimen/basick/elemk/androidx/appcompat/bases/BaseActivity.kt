package com.mozhimen.basick.elemk.androidx.appcompat.bases

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
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

    //////////////////////////////////////////////////////////////////////////

    override fun onRestart() {
        UtilKLogWrapper.d(TAG, "onRestart: ")
        super.onRestart()
    }

    override fun onNewIntent(intent: Intent?) {
        UtilKLogWrapper.d(TAG, "onNewIntent: intent $intent")
        super.onNewIntent(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        UtilKLogWrapper.d(TAG, "onBackPressed: ")
        super.onBackPressed()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        UtilKLogWrapper.d(TAG, "onActivityResult: requestCode $requestCode resultCode $resultCode data $data")
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        UtilKLogWrapper.d(TAG, "onActivityResult: requestCode $requestCode permissions $permissions grantResults $grantResults")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        UtilKLogWrapper.d(TAG, "onConfigurationChanged: newConfig $newConfig")
        super.onConfigurationChanged(newConfig)
    }

    //////////////////////////////////////////////////////////////////////////

    override fun finish() {
        UtilKLogWrapper.d(TAG, "finish: ")
        super.finish()
    }

    override fun startActivity(intent: Intent?) {
        UtilKLogWrapper.d(TAG, "startActivity: intent $intent")
        super.startActivity(intent)
    }
}