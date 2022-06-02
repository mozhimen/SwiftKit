package com.mozhimen.underlayk.debugk

import com.mozhimen.basick.extsk.boolean2String
import com.mozhimen.basick.utilk.*
import com.mozhimen.underlayk.BuildConfig
import com.mozhimen.underlayk.debugk.annors.DebugKParamsAnnor

/**
 * @ClassName DebugKParams
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 10:04
 * @Version 1.0
 */
class DebugKParams {

    @DebugKParamsAnnor("序列号")
    fun localSerialNo(): String = UtilKDevice.getSerialNumber()

    @DebugKParamsAnnor("短序列号")
    fun localSerialNoShort(): String = UtilKDevice.getSerialNumberShort()

    @DebugKParamsAnnor("构建版本")
    fun buildConfigVersion(): String = "code ${BuildConfig.VERSION_CODE} name ${BuildConfig.VERSION_NAME}"

    @DebugKParamsAnnor("构建环境")
    fun buildConfigEnvironment(): String = if (BuildConfig.DEBUG) "测试环境" else "正式环境"

    @DebugKParamsAnnor("构建时间")
    fun buildConfigTime(): String = BuildConfig.BUILD_TIME

    @DebugKParamsAnnor("构建类型")
    fun buildType(): String = UtilKBuild.getType()

    @DebugKParamsAnnor("构建标签聚合")
    fun buildTags(): String = UtilKBuild.getTags()

    @DebugKParamsAnnor("构建SDK版本")
    fun buildVersionSDKCode(): String = UtilKBuild.getVersionSDKCode()

    @DebugKParamsAnnor("构建Release版本号")
    fun buildVersionRelease(): String = UtilKBuild.getVersionRelease()

    @DebugKParamsAnnor("构建版本名称")
    fun buildVersionCodeName(): String = UtilKBuild.getVersionCodeName()

    @DebugKParamsAnnor("构建显示ID")
    fun buildDisplayId(): String = UtilKBuild.getDisplayId()

    @DebugKParamsAnnor("构建变更列表号")
    fun buildId(): String = UtilKBuild.getId()

    @DebugKParamsAnnor("构建内部Host")
    fun buildHost(): String = UtilKBuild.getHost()

    @DebugKParamsAnnor("构建内部构建者")
    fun buildUser(): String = UtilKBuild.getUser()

    @DebugKParamsAnnor("构建内部时间")
    fun buildTime(): String = UtilKBuild.getTime()

    @DebugKParamsAnnor("设备分辨率")
    fun deviceScreen(): String = "设备分辨率: w " + UtilKScreen.getScreenWidth() + " h " + UtilKScreen.getScreenHeight()

    @DebugKParamsAnnor("设备IP")
    fun deviceIP(): String = UtilKDevice.getDeviceIP()

    @DebugKParamsAnnor("设备Rom版本")
    fun deviceRomVersion(): String = UtilKDevice.getRomVersion()

    @DebugKParamsAnnor("设备硬件版本")
    fun deviceHardwareVersion(): String = UtilKDevice.getHardwareVersion()

    @DebugKParamsAnnor("设备是否有sd卡")
    fun deviceHasSdcard(): String = UtilKDevice.isHasSdcard().boolean2String()

    @DebugKParamsAnnor("设备是否有前置摄像")
    fun deviceHasFrontCamera(): String = UtilKDevice.isHasFrontCamera().boolean2String()

    @DebugKParamsAnnor("设备是否有后置摄像头")
    fun deviceHasBackCamera(): String = UtilKDevice.isHasBackCamera().boolean2String()

    @DebugKParamsAnnor("设备名")
    fun deviceProduct(): String = UtilKBuild.getProduct()

    @DebugKParamsAnnor("设备品牌")
    fun deviceBrand(): String = UtilKBuild.getBrand()

    @DebugKParamsAnnor("设备/硬件制造商")
    fun deviceManufacture(): String = UtilKBuild.getManufacture()

    @DebugKParamsAnnor("设备无线固件版本")
    fun deviceRadioVersion(): String = UtilKBuild.getRadioVersion()

    @DebugKParamsAnnor("设备最终用户名")
    fun deviceModel(): String = UtilKBuild.getModel()

    @DebugKParamsAnnor("设备支持架构")
    fun deviceSupportABIs(): String = UtilKBuild.getSupportABIs()

    @DebugKParamsAnnor("设备支持32位架构")
    fun deviceSupport32BitABIs(): String = UtilKBuild.getSupport32BitABIs()

    @DebugKParamsAnnor("设备支持64位架构")
    fun deviceSupport64BitABIs(): String = UtilKBuild.getSupport64BitABIs()

    @DebugKParamsAnnor("设备开发板名称")
    fun deviceBoard(): String = UtilKBuild.getBoard()

    @DebugKParamsAnnor("设备工业设计名")
    fun deviceDevice(): String = UtilKBuild.getDevice()

    @DebugKParamsAnnor("硬件名称")
    fun hardware(): String = UtilKBuild.getHardware()

    @DebugKParamsAnnor("唯一标识版本字符")
    fun fingerPrint(): String = UtilKBuild.getFingerPrint()

    @DebugKParamsAnnor("系统引导加载程序版本")
    fun bootLoader(): String = UtilKBuild.getBootLoader()

}