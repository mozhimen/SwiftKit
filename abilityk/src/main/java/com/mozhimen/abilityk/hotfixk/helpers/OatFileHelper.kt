package com.mozhimen.abilityk.hotfixk.helpers

import android.annotation.SuppressLint
import java.io.File

/**
 * @ClassName OatFileHelper
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/22 11:18
 * @Version 1.0
 */
object OatFileHelper {
    /**
     * optimizedPathFor
     * @param path File
     * @return String
     */
    fun optimizedPathFor(path: File): String {
        // dex_location = /foo/bar/baz.jar
        // odex_location = /foo/bar/oat/<isa>/baz.odex
        val currentInstructionSet: String = try {
            getCurrentInstructionSet()
        } catch (e: Exception) {
            throw RuntimeException("optimizedPathFor: getCurrentInstructionSet fail:", e)
        }
        val parentFile: File = path.parentFile?: throw RuntimeException("optimizedPathFor: get parentFile fail")
        var fileName = path.name
        val index = fileName.lastIndexOf('.')
        if (index > 0) {
            fileName = fileName.substring(0, index)
        }
        return (parentFile.absolutePath + "/oat/"
                + currentInstructionSet + "/" + fileName + ".odex")
    }

    /**
     * getCurrentInstructionSet
     * @return String
     * @throws java
     */
    @SuppressLint("DiscouragedPrivateApi")
    @Throws(java.lang.Exception::class)
    fun getCurrentInstructionSet(): String {
        val clazz = Class.forName("dalvik.system.VMRuntime")
        val currentGet = clazz.getDeclaredMethod("getCurrentInstructionSet")
        return currentGet.invoke(null) as String
    }
}