package com.mozhimen.basick.utilk.wrapper

import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.elemk.mos.MRomInfo
import com.mozhimen.basick.utilk.android.os.UtilKBuild
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKSystemProperties
import com.mozhimen.basick.utilk.java.lang.UtilKRuntimeWrapper
import com.mozhimen.basick.utilk.java.util.UtilKProperties
import java.util.Locale

/**
 * @ClassName UtilKRom
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 16:05
 * @Version 1.0
 */
object UtilKSysRom {

    private var _romInfo: MRomInfo? = null//name,version

    @JvmStatic
    fun getRomInfo(): MRomInfo {
        if (_romInfo != null) return _romInfo!!
        val romInfo = MRomInfo()
        val brand: String = UtilKBuild.getBrand()
        val manufacturer: String = UtilKBuild.getManufacture()
        if (isRom_of(brand, manufacturer, *getROM_HUAWEI())) {
            romInfo.name = getROM_HUAWEI().get(0)
            val version: String = getRomVersion(CStrPackage.ro_build_version_emui)
            val temp = version.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (temp.size > 1) {
                romInfo.version = temp[1]
            } else {
                romInfo.version = version
            }
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_VIVO())) {
            romInfo.name = getROM_VIVO().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_vivo_os_build_display_id)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_XIAOMI())) {
            romInfo.name = getROM_XIAOMI().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_build_version_incremental)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_OPPO())) {
            romInfo.name = getROM_OPPO().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_build_version_opporom)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_LEECO())) {
            romInfo.name = getROM_LEECO().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_letv_release_version)
            return romInfo
        }

        if (isRom_of(brand, manufacturer, *getROM_360())) {
            romInfo.name = getROM_360().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_build_uiversion)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_ZTE())) {
            romInfo.name = getROM_ZTE().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_build_MiFavor_version)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_ONEPLUS())) {
            romInfo.name = getROM_ONEPLUS().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_rom_version)
            return romInfo
        }
        if (isRom_of(brand, manufacturer, *getROM_NUBIA())) {
            romInfo.name = getROM_NUBIA().get(0)
            romInfo.version = getRomVersion(CStrPackage.ro_build_rom_id)
            return romInfo
        }

        if (isRom_of(brand, manufacturer, *getROM_COOLPAD())) {
            romInfo.name = getROM_COOLPAD().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_LG())) {
            romInfo.name = getROM_LG().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_GOOGLE())) {
            romInfo.name = getROM_GOOGLE().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_SAMSUNG())) {
            romInfo.name = getROM_SAMSUNG().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_MEIZU())) {
            romInfo.name = getROM_MEIZU().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_LENOVO())) {
            romInfo.name = getROM_LENOVO().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_SMARTISAN())) {
            romInfo.name = getROM_SMARTISAN().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_HTC())) {
            romInfo.name = getROM_HTC().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_SONY())) {
            romInfo.name = getROM_SONY().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_GIONEE())) {
            romInfo.name = getROM_GIONEE().get(0)
        } else if (isRom_of(brand, manufacturer, *getROM_MOTOROLA())) {
            romInfo.name = getROM_MOTOROLA().get(0)
        } else {
            romInfo.name = manufacturer
        }
        romInfo.version = getRomVersion("")
        return romInfo
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getROM_HUAWEI(): Array<String> =
        arrayOf("huawei")

    @JvmStatic
    fun getROM_VIVO(): Array<String> =
        arrayOf("vivo")

    @JvmStatic
    fun getROM_XIAOMI(): Array<String> =
        arrayOf("xiaomi")

    @JvmStatic
    fun getROM_OPPO(): Array<String> =
        arrayOf("oppo")

    @JvmStatic
    fun getROM_LEECO(): Array<String> =
        arrayOf("leeco", "letv")

    @JvmStatic
    fun getROM_360(): Array<String> =
        arrayOf("360", "qiku")

    @JvmStatic
    fun getROM_ZTE(): Array<String> =
        arrayOf("zte")

    @JvmStatic
    fun getROM_ONEPLUS(): Array<String> =
        arrayOf("oneplus")

    @JvmStatic
    fun getROM_NUBIA(): Array<String> =
        arrayOf("nubia")

    @JvmStatic
    fun getROM_COOLPAD(): Array<String> =
        arrayOf("coolpad", "yulong")

    @JvmStatic
    fun getROM_LG(): Array<String> =
        arrayOf("lg", "lge")

    @JvmStatic
    fun getROM_GOOGLE(): Array<String> =
        arrayOf("google")

    @JvmStatic
    fun getROM_SAMSUNG(): Array<String> =
        arrayOf("samsung")

    @JvmStatic
    fun getROM_MEIZU(): Array<String> =
        arrayOf("meizu")

    @JvmStatic
    fun getROM_LENOVO(): Array<String> =
        arrayOf("lenovo")

    @JvmStatic
    fun getROM_SMARTISAN(): Array<String> =
        arrayOf("smartisan")

    @JvmStatic
    fun getROM_HTC(): Array<String> =
        arrayOf("htc")

    @JvmStatic
    fun getROM_SONY(): Array<String> =
        arrayOf("sony")

    @JvmStatic
    fun getROM_GIONEE(): Array<String> =
        arrayOf("gionee", "amigo")

    @JvmStatic
    fun getROM_MOTOROLA(): Array<String> =
        arrayOf("motorola")

    @JvmStatic
    fun getProperties(strPackage: String): String? {
        var systemProperties: String? = UtilKRuntimeWrapper.exec_getprop(strPackage)
        if (systemProperties?.isNotEmpty() == true)
            return systemProperties
        systemProperties = UtilKProperties.getProperty_ofBuildProp(strPackage)
        if (systemProperties?.isNotEmpty() == true)
            return systemProperties
        if (UtilKBuildVersion.isBeforeV_28_9_P())
            return UtilKSystemProperties.getStrStr(strPackage)
        return systemProperties
    }

    @JvmStatic
    fun getRomVersion(propertyName: String): String {
        var romVersion: String? = null
        if (propertyName.isNotEmpty())
            romVersion = getProperties(propertyName)
        if (romVersion.isNullOrEmpty()) {
            try {
                val display = UtilKBuild.getDisplay()
                if (display.isNotEmpty()) {
                    romVersion = display.lowercase(Locale.getDefault())
                }
            } catch (ignore: Throwable) { /**/
            }
        }
        return romVersion ?: ""
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isHuawei(): Boolean =
        getROM_HUAWEI().get(0) == getRomInfo().name

    @JvmStatic
    fun isVivo(): Boolean =
        getROM_VIVO().get(0) == getRomInfo().name

    @JvmStatic
    fun isXiaomi(): Boolean =
        getROM_XIAOMI().get(0) == getRomInfo().name

    @JvmStatic
    fun isOppo(): Boolean =
        getROM_OPPO().get(0) == getRomInfo().name

    @JvmStatic
    fun isLeeco(): Boolean =
        getROM_LEECO().get(0) == getRomInfo().name

    @JvmStatic
    fun is360(): Boolean =
        getROM_360().get(0) == getRomInfo().name

    @JvmStatic
    fun isZte(): Boolean =
        getROM_ZTE().get(0) == getRomInfo().name

    @JvmStatic
    fun isOneplus(): Boolean =
        getROM_ONEPLUS().get(0) == getRomInfo().name

    @JvmStatic
    fun isNubia(): Boolean =
        getROM_NUBIA().get(0) == getRomInfo().name

    @JvmStatic
    fun isCoolpad(): Boolean =
        getROM_COOLPAD().get(0) == getRomInfo().name

    @JvmStatic
    fun isLg(): Boolean =
        getROM_LG().get(0) == getRomInfo().name

    @JvmStatic
    fun isGoogle(): Boolean =
        getROM_GOOGLE().get(0) == getRomInfo().name

    @JvmStatic
    fun isSamsung(): Boolean =
        getROM_SAMSUNG().get(0) == getRomInfo().name

    @JvmStatic
    fun isMeizu(): Boolean =
        getROM_MEIZU().get(0) == getRomInfo().name

    @JvmStatic
    fun isLenovo(): Boolean =
        getROM_LENOVO().get(0) == getRomInfo().name

    @JvmStatic
    fun isSmartisan(): Boolean =
        getROM_SMARTISAN().get(0) == getRomInfo().name

    @JvmStatic
    fun isHtc(): Boolean =
        getROM_HTC().get(0) == getRomInfo().name

    @JvmStatic
    fun isSony(): Boolean =
        getROM_SONY().get(0) == getRomInfo().name

    @JvmStatic
    fun isGionee(): Boolean =
        getROM_GIONEE().get(0) == getRomInfo().name

    @JvmStatic
    fun isMotorola(): Boolean =
        getROM_MOTOROLA().get(0) == getRomInfo().name

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isRom_of(brand: String, manufacturer: String, vararg names: String): Boolean =
        names.any { brand.contains(it, true) } || names.any { manufacturer.contains(it, true) }
}