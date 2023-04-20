package com.mozhimen.basick.utilk.os

import android.os.Build
import com.mozhimen.basick.utilk.device.UtilKDate
import com.mozhimen.basick.utilk.exts.combineArray2Str
import com.mozhimen.basick.utilk.exts.long2Str

/**
 * @ClassName UtilKBuild
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 19:56
 * @Version 1.0
 */
object UtilKBuild {

    //设备名
    @JvmStatic
    fun getProduct(): String = Build.PRODUCT

    //构建类型
    @JvmStatic
    fun getType(): String = Build.TYPE

    //构建标签聚合
    @JvmStatic
    fun getTags(): String = Build.TAGS

    //构建SDK版本
    @JvmStatic
    fun getVersionSDKCode(): String = Build.VERSION.SDK_INT.toString()

    //构建Release版本号
    @JvmStatic
    fun getVersionRelease(): String = Build.VERSION.RELEASE

    //构建版本名称
    @JvmStatic
    fun getVersionCodeName(): String = Build.VERSION.CODENAME

    //设备品牌
    @JvmStatic
    fun getBrand(): String = Build.BRAND

    //设备/硬件制造商
    @JvmStatic
    fun getManufacture(): String = Build.MANUFACTURER

    //设备最终用户名
    @JvmStatic
    fun getModel(): String = Build.MODEL

    //设备支持架构
    @JvmStatic
    fun getSupportABIs(): String = Build.SUPPORTED_ABIS.combineArray2Str()

    //设备支持32位架构
    @JvmStatic
    fun getSupport32BitABIs(): String = Build.SUPPORTED_32_BIT_ABIS.combineArray2Str()

    //设备支持64位架构
    @JvmStatic
    fun getSupport64BitABIs(): String = Build.SUPPORTED_64_BIT_ABIS.combineArray2Str()

    //设备开发板名称
    @JvmStatic
    fun getBoard(): String = Build.BOARD

    //设备工业设计名
    @JvmStatic
    fun getDevice(): String = Build.DEVICE

    //硬件名称
    @JvmStatic
    fun getHardware(): String = Build.HARDWARE

    //唯一标识版本字符
    @JvmStatic
    fun getFingerPrint(): String = Build.FINGERPRINT

    //构建显示ID
    @JvmStatic
    fun getDisplayId(): String = Build.DISPLAY

    //构建变更列表号
    @JvmStatic
    fun getId(): String = Build.ID

    //设备无线固件版本
    @JvmStatic
    fun getRadioVersion(): String = Build.getRadioVersion() ?: "unknown"

    //系统引导加载程序版本
    @JvmStatic
    fun getBootLoader(): String = Build.BOOTLOADER

    //构建内部Host
    @JvmStatic
    fun getHost(): String = Build.HOST

    //构建内部构建者
    @JvmStatic
    fun getUser(): String = Build.USER

    //构建内部时间
    @JvmStatic
    fun getTime(): String = Build.TIME.long2Str(UtilKDate.Format.yyyyMMddHHmmss)
}