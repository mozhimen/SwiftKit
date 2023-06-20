package com.mozhimen.basick.utilk.os

import com.mozhimen.basick.elemk.cons.CDateFormat
import java.lang.Exception

/**
 * @ClassName UtilKTime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/31 18:01
 * @Version 1.0
 */
fun Long.isOnTheHour(): Boolean =
    UtilKTime.isOnTheHour(this)

fun String.isOnTheHourOf(): Boolean =
    UtilKTime.isOnTheHourOf(this)

fun String.isOnTheHourOf(timeStr: String): Boolean =
    UtilKTime.isOnTheHourOf(this, timeStr)

fun Pair<String, String>.isOnTheMinuteOf() =
    UtilKTime.isOnTheMinuteOf(this)

fun Pair<String, String>.isOnTheMinuteOf(timeStr: String) =
    UtilKTime.isOnTheMinuteOf(this, timeStr)

object UtilKTime {
    /**
     * 是否对齐时间
     * @param hourAndMinuteTwoBits Pair<String, String> 小时两位,分钟两位
     * @param timeStr String 格式HHmm
     * @return Boolean
     */
    @JvmStatic
    fun isOnTheMinuteOf(hourAndMinuteTwoBits: Pair<String, String>): Boolean =
        isOnTheMinuteOf(hourAndMinuteTwoBits.first, hourAndMinuteTwoBits.second, UtilKDate.getNowStr(CDateFormat.HHmm))

    /**
     * 是否对齐时间
     * @param hourAndMinuteTwoBits Pair<String, String> 小时两位,分钟两位
     * @param timeStr String 格式HHmm
     * @return Boolean
     */
    @JvmStatic
    fun isOnTheMinuteOf(hourAndMinuteTwoBits: Pair<String, String>, timeStr: String): Boolean =
        isOnTheMinuteOf(hourAndMinuteTwoBits.first, hourAndMinuteTwoBits.second, timeStr)

    /**
     * 是否对齐时间
     * @param hourStrTwoBits String 小时两位
     * @param minuteStrTwoBits String 分钟两位
     * @param timeStr String 格式HHmm
     * @return Boolean
     */
    @JvmStatic
    fun isOnTheMinuteOf(hourStrTwoBits: String, minuteStrTwoBits: String, timeStr: String): Boolean {
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
    fun isOnTheHourOf(hourStrTwoBits: String): Boolean =
        isOnTheHourOf(hourStrTwoBits, UtilKDate.getNowStr(CDateFormat.HHmm))

    /**
     * 是否是指定整点
     * @param hourStrTwoBits String
     * @param timeStr String HHmm格式
     */
    @JvmStatic
    fun isOnTheHourOf(hourStrTwoBits: String, timeStr: String): Boolean {
        val time = timeStr.split(":")
        return try {
            time[0] == hourStrTwoBits && time[1] == "00"
        } catch (e: Exception) {
            e.printStackTrace();false
        }
    }

    /**
     * 获取当前小时2位
     * @return String
     */
    @JvmStatic
    fun getCurrentHourStrDoubleBits(): String =
        UtilKDate.getNowStr(CDateFormat.mm)

    /**
     * 是否是整小时
     * @param timeLong Long
     */
    @JvmStatic
    fun isOnTheHour(timeLong: Long): Boolean =
        (timeLong / 1000) % 3600 == 0L

    /**
     * 是否是整小时
     * @return Boolean
     */
    @JvmStatic
    fun isOnTheHour(): Boolean =
        isOnTheHour(UtilKDate.getNowStr(CDateFormat.mm))

    /**
     * 是否是整小时
     * @param timeStr String 时间格式mm
     * @return Boolean
     */
    @JvmStatic
    fun isOnTheHour(timeStr: String): Boolean {
        return try {
            timeStr == "00"
        } catch (e: Exception) {
            e.printStackTrace();false
        }
    }
}