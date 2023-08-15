package com.mozhimen.underlayk.crashk.commons

import java.io.File


/**
 * @ClassName ICrashKMgr
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 10:32
 * @Version 1.0
 */
interface ICrashKMgr {
    fun getJavaCrashFiles(): Array<File>
    fun getNativeCrashFiles(): Array<File>
    fun getCrashFiles(): Array<File>
}