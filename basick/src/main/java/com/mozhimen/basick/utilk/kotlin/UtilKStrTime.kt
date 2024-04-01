package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.java.util.UtilKDateWrapper
import java.lang.Exception

/**
 * @ClassName UtilKTime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/31 18:01
 * @Version 1.0
 */
fun String.isStrTime_ofHH(): Boolean =
    UtilKStrTime.isStrTime_ofHH(this)

fun String.isStrTime_ofHH(timeStr: String): Boolean =
    UtilKStrTime.isStrTime_ofHH(this, timeStr)

fun Pair<String, String>.isStrTime_ofHHmm() =
    UtilKStrTime.isStrTime_ofHHmm(this)

fun Pair<String, String>.isStrTime_ofHHmm(timeStr: String) =
    UtilKStrTime.isStrTime_ofHHmm(this, timeStr)

//////////////////////////////////////////////////////////////////

object UtilKStrTime {

    /**
     * 是否对齐时间
     * @param strTime_ofHHmm Pair<String, String> 小时两位,分钟两位 "01" to "00"
     */
    @JvmStatic
    fun isStrTime_ofHHmm(strTime_ofHHmm: Pair<String, String>): Boolean =
        isStrTime_ofHHmm(strTime_ofHHmm, UtilKDateWrapper.getCurrentHourStr_ofHHmm())

    /**
     * 是否对齐时间
     * @param strTime_ofHHmm Pair<String, String> 小时两位,分钟两位
     * @param timeStr String 格式HHmm
     */
    @JvmStatic
    fun isStrTime_ofHHmm(strTime_ofHHmm: Pair<String, String>, strTime: String): Boolean =
        isStrTime_ofHHmm(strTime_ofHHmm.first, strTime_ofHHmm.second, strTime)

    /**
     * 是否对齐时间
     * @param hourStrTwoBits String 小时两位
     * @param minuteStrTwoBits String 分钟两位
     * @param timeStr String 格式HHmm
     */
    @JvmStatic
    fun isStrTime_ofHHmm(strTime_HH: String, strTime_mm: String, timeStr: String): Boolean {
        return try {
            val time = timeStr.split(":")
            time[0] == strTime_HH && time[1] == strTime_mm
        } catch (e: Exception) {
            e.printStackTrace();
            false
        }
    }

    /**
     * 是否是指定整点
     * @param strTime_HH String HHmm格式
     */
    @JvmStatic
    fun isStrTime_ofHH(strTime_HH: String): Boolean =
        isStrTime_ofHH(strTime_HH, UtilKDateWrapper.getNowStr(CDateFormat.HH_mm))

    /**
     * 是否是指定整点
     * @param timeStr String HHmm格式
     */
    @JvmStatic
    fun isStrTime_ofHH(strTime_HH: String, timeStr: String): Boolean {
        val time = timeStr.split(":")
        return try {
            time[0] == strTime_HH && time[1] == "00"
        } catch (e: Exception) {
            e.printStackTrace();
            false
        }
    }

    //是否是整小时
    @JvmStatic
    fun isStrTime_ofmm(): Boolean =
        isStrTime_ofHH(UtilKDateWrapper.getNowStr(CDateFormat.mm))

    /**
     * 是否是整小时
     * @param strTime_mm String 时间格式mm
     */
    @JvmStatic
    fun isStrTime_ofmm(strTime_mm: String): Boolean =
        strTime_mm == "00"
}