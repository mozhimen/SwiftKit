package com.mozhimen.basick.utilk.content

import android.content.Context


/**
 * @ClassName UtilKDir
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/27 16:41
 * @Version 1.0
 */
object UtilKDir {
    private val _context by lazy { UtilKApplication.instance.get() }

    @JvmStatic
    fun getFilesAbsolutePath(): String =
        UtilKContext.getFilesAbsolutePath(_context)

    @JvmStatic
    fun getExternalRootFilesAbsolutePath(): String =
        UtilKContext.getExternalRootFilesAbsolutePath(_context)

    @JvmStatic
    fun getCacheAbsolutePath(): String =
        UtilKContext.getCacheAbsolutePath(_context)
}