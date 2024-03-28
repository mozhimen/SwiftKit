package com.mozhimen.basick.utilk.wrapper

import android.os.Build
import android.text.TextUtils
import com.mozhimen.basick.elemk.cons.CRom
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.java.lang.UtilKRuntime
import com.mozhimen.basick.utilk.java.lang.UtilKRuntimeWrapper
import java.util.Locale

/**
 * @ClassName UtilKRom
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 16:05
 * @Version 1.0
 */
object UtilKSysRom {

    private var _name: String? = null
    private var _version: String? = null

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isEmui(): Boolean =
        isRom(CRom.EMUI)

    @JvmStatic
    fun isMiui(): Boolean =
        isRom(CRom.MIUI)

    @JvmStatic
    fun isVivo(): Boolean =
        isRom(CRom.VIVO)

    @JvmStatic
    fun isOppo(): Boolean =
        isRom(CRom.OPPO)

    @JvmStatic
    fun isFlyme(): Boolean =
        isRom(CRom.FLYME)

    @JvmStatic
    fun is360(): Boolean =
        isRom(CRom.QIKU) || isRom(CRom.`360`)

    @JvmStatic
    fun isSmartisan(): Boolean =
        isRom(CRom.SMARTISAN)

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isRom(strRom: String): Boolean {
        _name?.let { return _name == strRom }
        if (!TextUtils.isEmpty(UtilKRuntimeWrapper.exec_getprop(CStrPackage.RO_MIUI_UI_VERSION_NAME).also { _version = it })) {
            _name = CRom.MIUI
        } else if (!TextUtils.isEmpty(UtilKRuntimeWrapper.exec_getprop(CStrPackage.RO_BUILD_VERSION_EMUI).also { _version = it })) {
            _name = CRom.EMUI
        } else if (!TextUtils.isEmpty(UtilKRuntimeWrapper.exec_getprop(CStrPackage.RO_BUILD_VERSION_OPPOROM).also { _version = it })) {
            _name = CRom.OPPO
        } else if (!TextUtils.isEmpty(UtilKRuntimeWrapper.exec_getprop(CStrPackage.RO_VIVO_OS_VERSION).also { _version = it })) {
            _name = CRom.VIVO
        } else if (!TextUtils.isEmpty(UtilKRuntimeWrapper.exec_getprop(CStrPackage.RO_SMARTISAN_VERSION).also { _version = it })) {
            _name = CRom.SMARTISAN
        } else {
            _version = Build.DISPLAY
            if (_version?.uppercase(Locale.getDefault())!!.contains(CRom.FLYME)) {
                _name = CRom.FLYME
            } else {
                _version = Build.UNKNOWN
                _name = Build.MANUFACTURER.uppercase(Locale.ROOT)
            }
        }
        return _name == strRom
    }
}