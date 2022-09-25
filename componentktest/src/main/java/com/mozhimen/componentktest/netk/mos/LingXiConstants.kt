package com.mozhimen.componentktest.netk.mos

/**
 * @ClassName LingXiConstants
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/11 15:32
 * @Version 1.0
 */
object LingXiConstants {

    enum class LingXiCode(val code: Int, val msg: String) {
        SUCCESS(200, "成功"),//200 成功
        REQ_NULL(9000, "请求参数为空"),//9000 请求参数为空
        DEVICE_DIS(9001, "设备不存在"),//9001 设备不存在
        DEVICE_UNBIND(9002, "设备未绑定"),//9002 设备未绑定
        INFO_FAIL(9003, "获取信息失败"),//9003 获取信息失败
        CODE_LAST(9004, "配相关人员信息或苏康码已过期"),//9004 苏康码:已过期 or 配相关人员信息
        OTHER(-1, "未配置的错误代码")
    }

    const val SP_LINGXI = "sp_lingxi"

    const val SP_LINGXI_URL = "sp_lingxi_url"
    const val LINGXI_URL = "http://121.229.39.86:8123/"
    const val SP_LINGXI_TIME_RANGE = "sp_lingxi_time_range"
    const val LINGXI_TIME_RANGE = 72

    fun getLingXiMsg(code: Int): String {
        return LingXiCode.values().firstOrNull { it.code == code }?.msg ?: LingXiCode.OTHER.msg
    }
}