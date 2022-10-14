package com.mozhimen.basick.utilk

import android.os.Build
import com.mozhimen.basick.extsk.joinArray
import com.mozhimen.basick.extsk.long2String

/**
 * @ClassName UtilKBuild
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 19:56
 * @Version 1.0
 */
object UtilKBuild {

    //设备名
    fun getProduct(): String = Build.PRODUCT

    //构建类型
    fun getType(): String = Build.TYPE

    //构建标签聚合
    fun getTags(): String = Build.TAGS

    //构建SDK版本
    fun getVersionSDKCode(): String = Build.VERSION.SDK_INT.toString()

    //构建Release版本号
    fun getVersionRelease(): String = Build.VERSION.RELEASE

    //构建版本名称
    fun getVersionCodeName(): String = Build.VERSION.CODENAME

    //设备品牌
    fun getBrand(): String = Build.BRAND

    //设备/硬件制造商
    fun getManufacture(): String = Build.MANUFACTURER

    //设备最终用户名
    fun getModel(): String = Build.MODEL

    //设备支持架构
    fun getSupportABIs(): String = Build.SUPPORTED_ABIS.joinArray()

    //设备支持32位架构
    fun getSupport32BitABIs(): String = Build.SUPPORTED_32_BIT_ABIS.joinArray()

    //设备支持64位架构
    fun getSupport64BitABIs(): String = Build.SUPPORTED_64_BIT_ABIS.joinArray()

    //设备开发板名称
    fun getBoard(): String = Build.BOARD

    //设备工业设计名
    fun getDevice(): String = Build.DEVICE

    //硬件名称
    fun getHardware(): String = Build.HARDWARE

    //唯一标识版本字符
    fun getFingerPrint(): String = Build.FINGERPRINT

    //构建显示ID
    fun getDisplayId(): String = Build.DISPLAY

    //构建变更列表号
    fun getId(): String = Build.ID

    //设备无线固件版本
    fun getRadioVersion(): String = Build.getRadioVersion() ?: "unknown"

    //系统引导加载程序版本
    fun getBootLoader(): String = Build.BOOTLOADER

    //构建内部Host
    fun getHost(): String = Build.HOST

    //构建内部构建者
    fun getUser(): String = Build.USER

    //构建内部时间
    fun getTime(): String = Build.TIME.long2String(UtilKDate.FORMAT_yyyyMMddHHmmss)
}