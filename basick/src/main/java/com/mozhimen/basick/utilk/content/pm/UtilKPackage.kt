package com.mozhimen.basick.utilk.content.pm

import com.mozhimen.basick.utilk.content.UtilKApplication


/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/21 16:19
 * @Version 1.0
 */
object UtilKPackage {
    private val _context by lazy { UtilKApplication.instance.applicationContext }

    @JvmStatic
    fun getVersionCode(): Int =
        UtilKPackageInfo.getVersionCode(_context)
}