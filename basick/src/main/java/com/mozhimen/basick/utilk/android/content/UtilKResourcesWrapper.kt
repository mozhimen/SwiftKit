package com.mozhimen.basick.utilk.android.content

import android.content.res.Resources

/**
 * @ClassName UtilKResourcesWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/17
 * @Version 1.0
 */
object UtilKResourcesWrapper {
    @JvmStatic
    fun getInt_config_shortAnimTime(resources: Resources): Int =
        resources.getInteger(android.R.integer.config_shortAnimTime)

    @JvmStatic
    fun getInt_config_mediumAnimTime(resources: Resources): Int =
        resources.getInteger(android.R.integer.config_mediumAnimTime)

    @JvmStatic
    fun getInt_config_longAnimTime(resources: Resources): Int =
        resources.getInteger(android.R.integer.config_longAnimTime)
}