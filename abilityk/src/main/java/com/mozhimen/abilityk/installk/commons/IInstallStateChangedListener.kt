package com.mozhimen.abilityk.installk.commons

/**
 * @ClassName IOnInstallStateChangedListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 0:26
 * @Version 1.0
 */
interface IInstallStateChangedListener {
    fun onDownloadStart() {}
    fun onInstallStart()
    fun onInstallFinish()
    fun onInstallFail(msg: String?)
    fun onNeedPermissions() {}
}