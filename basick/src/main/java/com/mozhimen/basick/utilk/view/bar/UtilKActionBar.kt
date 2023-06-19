package com.mozhimen.basick.utilk.view.bar

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
    fun get(activity: AppCompatActivity): ActionBar? {
        return activity.supportActionBar
    }

    /**
     * enableBackIfActionBarExists
     * @param activity AppCompatActivity
     */
    @JvmStatic
    fun enableBackIfActionBarExists(activity: AppCompatActivity) {
        get(activity)?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * 隐藏ActionBar
     * @param activity AppCompatActivity
     */
    @JvmStatic
    fun hide(activity: AppCompatActivity) {
        get(activity)?.hide()
    }
}