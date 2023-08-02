package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import java.lang.Exception

/**
 * @ClassName UtilKTime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/31 18:01
 * @Version 1.0
 */
fun Long.isTimeAtHour(): Boolean =
    UtilKTime.isTimeAtHour(this)

fun String.isTimeAtHourOf(): Boolean =
    UtilKTime.isTimeAtHourOf(this)

fun String.isTimeAtHourOf(timeStr: String): Boolean =
    UtilKTime.isTimeAtHourOf(this, timeStr)

fun Pair<String, String>.isTimeAtMinuteOf() =
    UtilKTime.isTimeAtMinuteOf(this)

fun Pair<String, String>.isTimeAtMinuteOf(timeStr: String) =
    UtilKTime.isTimeAtMinuteOf(this, timeStr)

object UtilKTime {
    /**
     * 获取当前小时2位
     * @return String
     */
    @JvmStatic
    fun getCurrentHourStrDoubleBits(): String =
            UtilKDate.getNowStr(CDateFormat.HH)

    //////////////////////////////////////////////////////////////////////

    /**
     * 是否对齐时间
     * @param hourAndMinuteTwoBits Pair<String, String> 小时两位,分钟两位
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtMinuteOf(hourAndMinuteTwoBits: Pair<String, String>): Boolean =
        isTimeAtMinuteOf(hourAndMinuteTwoBits.first, hourAndMinuteTwoBits.second, UtilKDate.getNowStr(CDateFormat.HHmm))

    /**
     * 是否对齐时间
     * @param hourAndMinuteTwoBits Pair<String, String> 小时两位,分钟两位
     * @param timeStr String 格式HHmm
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtMinuteOf(hourAndMinuteTwoBits: Pair<String, String>, timeStr: String): Boolean =
        isTimeAtMinuteOf(hourAndMinuteTwoBits.first, hourAndMinuteTwoBits.second, timeStr)

    /**
     * 是否对齐时间
     * @param hourStrTwoBits String 小时两位
     * @param minuteStrTwoBits String 分钟两位
     * @param timeStr String 格式HHmm
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtMinuteOf(hourStrTwoBits: String, minuteStrTwoBits: String, timeStr: String): Boolean {
        val time = timeStr.split(":")
        return try {
            time[0] == hourStrTwoBits && time[1] == minuteStrTwoBits
        } catch (e: Exception) {
            e.printStackTrace();false
        }
    }

    /**
     * 是否是指定整点
     * @param hourStrTwoBits String HHmm格式
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtHourOf(hourStrTwoBits: String): Boolean =
        isTimeAtHourOf(hourStrTwoBits, UtilKDate.getNowStr(CDateFormat.HHmm))

    /**
     * 是否是指定整点
     * @param hourStrTwoBits String
     * @param timeStr String HHmm格式
     */
    @JvmStatic
    fun isTimeAtHourOf(hourStrTwoBits: String, timeStr: String): Boolean {
        val time = timeStr.split(":")
        return try {
            time[0] == hourStrTwoBits && time[1] == "00"
        } catch (e: Exception) {
            e.printStackTrace();false
        }
    }

    /**
     * 是否是整小时
     * @param timeLong Long
     */
    @JvmStatic
    fun isTimeAtHour(timeLong: Long): Boolean =
        (timeLong / 1000) % 3600 == 0L

    /**
     * 是否是整小时
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtHour(): Boolean =
        isTimeAtHour(UtilKDate.getNowStr(CDateFormat.mm))

    /**
     * 是否是整小时
     * @param timeStr String 时间格式mm
     * @return Boolean
     */
    @JvmStatic
    fun isTimeAtHour(timeStr: String): Boolean {
        return try {
            timeStr == "00"
        } catch (e: Exception) {
            e.printStackTrace();false
        }
    }
}