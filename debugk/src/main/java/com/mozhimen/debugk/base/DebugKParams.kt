package com.mozhimen.debugk.base

import com.mozhimen.basick.utilk.exts.boolean2String
import com.mozhimen.basick.utilk.*
import com.mozhimen.debugk.BuildConfig
import com.mozhimen.debugk.base.annors.ADebugKParams

/**
 * @ClassName DebugKParams
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 10:04
 * @Version 1.0
 */
class DebugKParams {

    @ADebugKParams("序列号")
    fun localSerialNo(): String = UtilKDevice.getSerialNumber()

    @ADebugKParams("短序列号")
    fun localSerialNoShort(): String = UtilKDevice.getSerialNumberShort()

    @ADebugKParams("构建版本")
    fun buildConfigVersion(): String = "code ${BuildConfig.VERSION_CODE} name ${BuildConfig.VERSION_NAME}"

    @ADebugKParams("构建环境")
    fun buildConfigEnvironment(): String = if (BuildConfig.DEBUG) "测试环境" else "正式环境"

    @ADebugKParams("构建时间")
    fun buildConfigTime(): String = BuildConfig.BUILD_TIME

    @ADebugKParams("构建类型")
    fun buildType(): String = UtilKBuild.getType()

    @ADebugKParams("构建标签聚合")
    fun buildTags(): String = UtilKBuild.getTags()

    @ADebugKParams("构建SDK版本")
    fun buildVersionSDKCode(): String = UtilKBuild.getVersionSDKCode()

    @ADebugKParams("构建Release版本号")
    fun buildVersionRelease(): String = UtilKBuild.getVersionRelease()

    @ADebugKParams("构建版本名称")
    fun buildVersionCodeName(): String = UtilKBuild.getVersionCodeName()

    @ADebugKParams("构建显示ID")
    fun buildDisplayId(): String = UtilKBuild.getDisplayId()

    @ADebugKParams("构建变更列表号")
    fun buildId(): String = UtilKBuild.getId()

    @ADebugKParams("构建内部Host")
    fun buildHost(): String = UtilKBuild.getHost()

    @ADebugKParams("构建内部构建者")
    fun buildUser(): String = UtilKBuild.getUser()

    @ADebugKParams("构建内部时间")
    fun buildTime(): String = UtilKBuild.getTime()

    @ADebugKParams("设备分辨率")
    fun deviceScreen(): String = "设备分辨率: w " + UtilKScreen.getScreenWidth() + " h " + UtilKScreen.getScreenHeight()

    @ADebugKParams("设备IP")
    fun deviceIP(): String = UtilKDevice.getDeviceIP()

    @ADebugKParams("设备Rom版本")
    fun deviceRomVersion(): String = UtilKDevice.getRomVersion()

    @ADebugKParams("设备硬件版本")
    fun deviceHardwareVersion(): String = UtilKDevice.getHardwareVersion()

    @ADebugKParams("设备是否有sd卡")
    fun deviceHasSdcard(): String = UtilKDevice.isHasSdcard().boolean2String()

    @ADebugKParams("设备是否有前置摄像")
    fun deviceHasFrontCamera(): String = UtilKDevice.isHasFrontCamera().boolean2String()

    @ADebugKParams("设备是否有后置摄像头")
    fun deviceHasBackCamera(): String = UtilKDevice.isHasBackCamera().boolean2String()

    @ADebugKParams("设备名")
    fun deviceProduct(): String = UtilKBuild.getProduct()

    @ADebugKParams("设备品牌")
    fun deviceBrand(): String = UtilKBuild.getBrand()

    @ADebugKParams("设备/硬件制造商")
    fun deviceManufacture(): String = UtilKBuild.getManufacture()

    @ADebugKParams("设备无线固件版本")
    fun deviceRadioVersion(): String = UtilKBuild.getRadioVersion()

    @ADebugKParams("设备最终用户名")
    fun deviceModel(): String = UtilKBuild.getModel()

    @ADebugKParams("设备支持架构")
    fun deviceSupportABIs(): String = UtilKBuild.getSupportABIs()

    @ADebugKParams("设备支持32位架构")
    fun deviceSupport32BitABIs(): String = UtilKBuild.getSupport32BitABIs()

    @ADebugKParams("设备支持64位架构")
    fun deviceSupport64BitABIs(): String = UtilKBuild.getSupport64BitABIs()

    @ADebugKParams("设备开发板名称")
    fun deviceBoard(): String = UtilKBuild.getBoard()

    @ADebugKParams("设备工业设计名")
    fun deviceDevice(): String = UtilKBuild.getDevice()

    @ADebugKParams("硬件名称")
    fun hardware(): String = UtilKBuild.getHardware()

    @ADebugKParams("唯一标识版本字符")
    fun fingerPrint(): String = UtilKBuild.getFingerPrint()

    @ADebugKParams("系统引导加载程序版本")
    fun bootLoader(): String = UtilKBuild.getBootLoader()

}