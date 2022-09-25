package com.mozhimen.componentktest.netk.mos

/**
 * @ClassName LingXiReq
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/11 15:43
 * @Version 1.0
 */
data class LingXiReq(
    val personCode: String,//为用户的个人门铃码的字符串
    val equipNo: String,//设备编号
    val timeRange: Int//核酸周期(24/48/72 小时)不传为系统默认
)