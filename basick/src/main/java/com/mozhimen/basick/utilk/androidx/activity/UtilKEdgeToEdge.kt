package com.mozhimen.basick.utilk.androidx.activity

import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

/**
 * @ClassName UtilKEdgeToEdge
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/22
 * @Version 1.0
 */
object UtilKEdgeToEdge {
    /**
     * enableEdgeToEdge()
     * setContentView(R.layout.activity_main)
     */
    @JvmStatic
    fun enableEdgeToEdge(componentActivity: ComponentActivity) {
        componentActivity.enableEdgeToEdge()
    }
}