package com.mozhimen.basick.utilk.androidx.appcompat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContainer

/**
 * @ClassName UtilKActionBarContainer
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/25
 * @Version 1.0
 */
@SuppressLint("RestrictedApi")
fun AppCompatActivity.getActionBarContainer(): ActionBarContainer? =
    UtilKActionBarContainer.get(this)

object UtilKActionBarContainer {
    @JvmStatic
    fun get(activity: AppCompatActivity): ActionBarContainer? =
        activity.findViewById(androidx.appcompat.R.id.action_bar_container)

}