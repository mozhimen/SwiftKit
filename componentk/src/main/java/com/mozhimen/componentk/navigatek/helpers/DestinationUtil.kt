package com.mozhimen.componentk.navigatek.helpers

import android.annotation.SuppressLint
import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mozhimen.basick.utilk.android.util.wt
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName DestinationUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/25 15:58
 * @Version 1.0
 */
fun Class<*>.getDestinationId(): Int =
    DestinationUtil.getId(this)

fun NavController.startDestinationId(destinationId: Int) {
    DestinationUtil.startId(this, destinationId)
}

fun NavController.findNavigationId(destinationId: Int): Boolean =
    DestinationUtil.findId(this, destinationId)

fun NavController.getCurrentDestinationId(): Int? =
    DestinationUtil.getCurrentId(this)

object DestinationUtil : IUtilK {
    @JvmStatic
    fun getId(activity: Activity): Int =
        getId(activity::class.java)

    @JvmStatic
    fun getId(fragment: Fragment): Int =
        getId(fragment::class.java)

    @JvmStatic
    fun getId(clazz: Class<*>): Int =
        kotlin.math.abs(clazz.name.hashCode())

    @JvmStatic
    fun startId(navController: NavController, destinationId: Int) {
        if (!findId(navController, destinationId)) {
            "startId don't find thid id".wt(TAG)
            return
        }
        if (getCurrentId(navController) == destinationId) {
            "startId current id is this id".wt(TAG)
            return
        }
        navController.navigate(destinationId)
    }

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun findId(navController: NavController, destinationId: Int): Boolean =
        navController.findDestination(destinationId) != null

    @JvmStatic
    fun getCurrentId(navController: NavController): Int? =
        navController.currentDestination?.id
}