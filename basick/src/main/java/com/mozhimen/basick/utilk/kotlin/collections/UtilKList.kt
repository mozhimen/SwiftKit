package com.mozhimen.basick.utilk.kotlin.collections

import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.util.stream.Collectors

/**
 * @ClassName UtilKList
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:55
 * @Version 1.0
 */
fun <T> List<T>.ifNotEmpty(block: IA_Listener<List<T>>) {
    UtilKList.ifNotEmpty(this, block)
}

fun <T> List<T>.ifNotEmptyOr(onNotEmpty: IA_Listener<List<T>>, onEmpty: I_Listener) {
    UtilKList.ifNotEmptyOr(this, onNotEmpty, onEmpty)
}

fun <T> List<T>.list2str(): String =
    UtilKList.list2str(this)

///////////////////////////////////////////////////////////////////////////

object UtilKList {
    @JvmStatic
    fun <T> ifNotEmpty(list: List<T>, block: IA_Listener<List<T>>) {
        if (list.isNotEmpty())
            block.invoke(list)
    }

    @JvmStatic
    fun <T> ifNotEmptyOr(list: List<T>, onNotEmpty: IA_Listener<List<T>>, onEmpty: I_Listener) {
        if (list.isNotEmpty())
            onNotEmpty.invoke(list)
        else
            onEmpty.invoke()
    }

    @JvmStatic
    @JvmOverloads
    fun <T> list2str(list: List<T>, defaultValue: String = "", splitChar: String = ","): String =
        if (UtilKBuildVersion.isAfterV_24_7_N()) {
            list.stream().map { elem: T? -> elem?.toString() ?: "" }.collect(Collectors.joining(splitChar)).ifEmpty { defaultValue }
        } else {
            val stringBuilder = StringBuilder()
            for (obj in list)
                stringBuilder.append(obj?.toString() ?: "").append(splitChar)
            if (stringBuilder.isNotEmpty())
                stringBuilder.deleteAt(stringBuilder.length - 1).toString()
            else defaultValue
        }
}