package com.mozhimen.underlayk.crashk.commons

import java.io.File


/**
 * @ClassName ICrashK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 10:26
 * @Version 1.0
 */
interface ICrashK {
    fun init(listener: ICrashKListener?)
    fun getCrashFiles(): Array<File>
}