package com.mozhimen.debugk.cons

import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.*
import com.mozhimen.basick.utilk.device.UtilKDevice
import com.mozhimen.basick.utilk.java.datatype.boolean2Str
import com.mozhimen.basick.utilk.view.display.UtilKScreen
import com.mozhimen.basick.utilk.net.UtilKNetConn
import com.mozhimen.basick.utilk.os.UtilKBuild
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import com.mozhimen.debugk.BuildConfig
import com.mozhimen.debugk.annors.ADebugKParams

/**
 * @ClassName DebugKParams
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/29 10:04
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.READ_PHONE_STATE, CPermission.READ_PRIVILEGED_PHONE_STATE, CPermission.CAMERA,
    CPermission.ACCESS_NETWORK_STATE, CPermission.ACCESS_WIFI_STATE, CPermission.INTERNET
)
class DebugKParams {
    /**
     * 设备参数
     * @return String
     */
    @ADebugKParams("设备参数")
    fun deviceParams(): String = ""

    @ADebugKParams("设备序列号")
    fun deviceSerialNo(): String = UtilKDevice.getSerialNumber()

    @ADebugKParams("设备短序列号")
    fun deviceSerialNoShort(): String = UtilKDevice.getSerialNumberShort()

    @ADebugKParams("设备IP")
    fun deviceIP(): String = UtilKNetConn.getIP() ?: "unknown"

    @ADebugKParams("设备Rom版本")
    fun deviceRomVersion(): String = UtilKDevice.getRomVersion()

    @ADebugKParams("设备硬件版本")
    fun deviceHardwareVersion(): String = UtilKDevice.getHardwareVersion()

    @ADebugKParams("设备是否有sd卡")
    fun deviceHasSdcard(): String = UtilKDevice.isHasSdcard().boolean2Str()

    @ADebugKParams("设备是否有前置摄像")
    fun deviceHasFrontCamera(): String = UtilKDevice.isHasFrontCamera().boolean2Str()

    @ADebugKParams("设备是否有后置摄像头")
    fun deviceHasBackCamera(): String = UtilKDevice.isHasBackCamera().boolean2Str()

    /**
     * 构建参数
     * @return String
     */
    @ADebugKParams("构建参数")
    fun buildParams(): String = ""

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

    @ADebugKParams("构建设备名")
    fun buildProduct(): String = UtilKBuild.getProduct()

    @ADebugKParams("构建设备品牌")
    fun buildBrand(): String = UtilKBuild.getBrand()

    @ADebugKParams("构建设备/硬件制造商")
    fun buildManufacture(): String = UtilKBuild.getManufacture()

    @ADebugKParams("构建无线固件版本")
    fun buildRadioVersion(): String = UtilKBuild.getRadioVersion()

    @ADebugKParams("构建最终用户名")
    fun buildModel(): String = UtilKBuild.getModel()

    @ADebugKParams("构建支持架构")
    fun buildSupportABIs(): String = UtilKBuild.getSupportABIs()

    @ADebugKParams("构建支持32位架构")
    fun buildSupport32BitABIs(): String = UtilKBuild.getSupport32BitABIs()

    @ADebugKParams("构建支持64位架构")
    fun buildSupport64BitABIs(): String = UtilKBuild.getSupport64BitABIs()

    @ADebugKParams("构建开发板名称")
    fun buildBoard(): String = UtilKBuild.getBoard()

    @ADebugKParams("构建工业设计名")
    fun buildDevice(): String = UtilKBuild.getDevice()

    @ADebugKParams("构建硬件名称")
    fun buildHardware(): String = UtilKBuild.getHardware()

    @ADebugKParams("构建唯一标识版本字符")
    fun buildFingerPrint(): String = UtilKBuild.getFingerPrint()

    @ADebugKParams("构建系统引导加载程序版本")
    fun buildBootLoader(): String = UtilKBuild.getBootLoader()

    /**
     * 屏幕参数
     * @return String
     */
    @ADebugKParams("屏幕参数")
    fun screenParams(): String = ""

    @ADebugKParams("屏幕尺寸")
    fun screenSize(): String = UtilKScreen.getScreenSize().toString()

    @ADebugKParams("屏幕真实分辨率px")
    fun screenResolution(): String = "设备分辨率: w " + UtilKScreen.getRealScreenWidth() + " h " + UtilKScreen.getRealScreenHeight()

    @ADebugKParams("屏幕当前分辨率px")
    fun screenResolution2(): String = "设备分辨率: w " + UtilKScreen.getCurrentScreenWidth() + " h " + UtilKScreen.getCurrentScreenHeight()

    @ADebugKParams("屏幕分辨率dp")
    fun screenResolutionDpi(): String = "设备分辨率: w " + UtilKScreen.getScreenWidthDp() + " h " + UtilKScreen.getScreenHeightDp()

    @ADebugKParams("屏幕密度px")
    fun screenDensity(): String = UtilKScreen.getScreenDensity().toString()

    @ADebugKParams("屏幕密度dp")
    fun screenDensityDpi(): String = UtilKScreen.getScreenDensityDpi().toString()

    @ADebugKParams("状态栏高度")
    fun screenStatusBarHeight(): String = UtilKStatusBar.getStatusBarHeight().toString()

    @ADebugKParams("导航栏高度")
    fun screenNavigationBarHeight(): String = UtilKNavigationBar.getNavigationBarHeight().toString()

    @ADebugKParams("屏幕竖屏")
    fun screenIsPortrait(): String = if (UtilKScreen.isScreenPortrait()) "是" else "否"
}