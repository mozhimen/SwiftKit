package com.mozhimen.componentktest.netk.mos

/**
 * @ClassName LingXiRes
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/11 15:44
 * @Version 1.0
 */
data class LingXiRes(
    val certCode: String,//扫码/刷卡人证件号（脱敏）
    val name: String,//扫码/刷卡人姓名
    val level: String,//苏康码:1 绿码 2 黄码 3 红码
    val nuclein_state: Int,//核酸状态:0 已采样未检测, 1 阴性, -1 无核酸记
    val vaccine: Int,//疫苗状态:0 接种过疫苗, 1 未接种疫
    val controlled: Boolean,//是否在管控区域
    val timeRange: Int//核酸核验周期(24/48/72)
)