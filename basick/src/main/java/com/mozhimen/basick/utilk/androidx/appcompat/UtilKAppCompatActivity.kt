package com.mozhimen.basick.utilk.androidx.appcompat

import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKLifecycleOwner

/**
 * @ClassName UtilKAppCompatActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 10:25
 * @Version 1.0
 */
fun AppCompatActivity.runOnBackThread(block: I_Listener) {
    UtilKAppCompatActivity.runOnBackThread(this, block)
}

fun AppCompatActivity.runOnMainThread(block: I_Listener) {
    UtilKAppCompatActivity.runOnMainThread(this, block)
}

object UtilKAppCompatActivity {
    @JvmStatic
    fun runOnBackThread(appCompatActivity: AppCompatActivity, block: I_Listener) {
        UtilKLifecycleOwner.runOnBackThread(appCompatActivity, block)
    }

    @JvmStatic
    fun runOnMainThread(appCompatActivity: AppCompatActivity, block: I_Listener) {
        UtilKLifecycleOwner.runOnMainThread(appCompatActivity, block)
    }

    @JvmStatic
    fun supportRequestWindowFeature(appCompatActivity: AppCompatActivity, featureId: Int){
        appCompatActivity.supportRequestWindowFeature(featureId)
    }
}