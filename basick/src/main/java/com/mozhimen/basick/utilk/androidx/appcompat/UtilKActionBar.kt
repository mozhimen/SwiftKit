package com.mozhimen.basick.utilk.androidx.appcompat

import android.app.Activity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

/**
 * @ClassName UtilKActionBar
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 18:02
 * @Version 1.0
 */
object UtilKActionBar {

    @JvmStatic
    fun get(activity: AppCompatActivity): ActionBar? =
        activity.supportActionBar

    @JvmStatic
    fun hide(activity: Activity) {
        if (activity is AppCompatActivity)
            hide(activity)
    }

    @JvmStatic
    fun hide(activity: AppCompatActivity) {
        get(activity)?.hide()
    }

    /**
     * enableBackIfActionBarExists
     * @param activity AppCompatActivity
     */
    @JvmStatic
    fun applyDisplayHomeAsUpEnabled(activity: AppCompatActivity) {
        get(activity)?.setDisplayHomeAsUpEnabled(true)
    }
}