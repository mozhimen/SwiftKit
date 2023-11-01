package com.mozhimen.componentk.netk.app.download

import com.mozhimen.componentk.netk.app.download.commons.INetKAppDownloadState
import com.mozhimen.componentk.netk.app.download.db.AppDownloadParam

/**
 * @ClassName NetKAppDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 9:38
 * @Version 1.0
 */
object NetKAppDownload : INetKAppDownloadState {
    private val _netKAppDownloadStateListeners = mutableListOf<INetKAppDownloadState>()

    /////////////////////////////////////////////////////////////////

    fun registerDownloadStateListener(listener: INetKAppDownloadState) {
        if (!_netKAppDownloadStateListeners.contains(listener)) {
            _netKAppDownloadStateListeners.add(listener)
        }
    }

    fun unregisterDownloadListener(listener: INetKAppDownloadState) {
        val indexOf = _netKAppDownloadStateListeners.indexOf(listener)
        if (indexOf >= 0) {
            _netKAppDownloadStateListeners.removeAt(indexOf)
        }
    }

    /////////////////////////////////////////////////////////////////

    override fun onDownloadStart(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadProgress(appDownloadParam: AppDownloadParam, progress: Int) {
    }

    override fun onDownloadPause(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadFail(appDownloadParam: AppDownloadParam) {
    }

    /////////////////////////////////////////////////////////////////

    override fun onVerifyStart(appDownloadParam: AppDownloadParam) {
    }

    override fun onVerifySuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onVerifyFail(appDownloadParam: AppDownloadParam) {
    }

    /////////////////////////////////////////////////////////////////

    override fun onUnzipStart(appDownloadParam: AppDownloadParam) {
    }

    override fun onUnzipSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onUnzipFail(appDownloadParam: AppDownloadParam) {
    }

    /////////////////////////////////////////////////////////////////

    override fun onInstallStart(appDownloadParam: AppDownloadParam) {
    }

    override fun onInstallSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onInstallFail(appDownloadParam: AppDownloadParam) {
    }

    /////////////////////////////////////////////////////////////////

    override fun onUninstallStart(appDownloadParam: AppDownloadParam) {
    }

    /////////////////////////////////////////////////////////////////

    override fun onTaskStart(appDownloadParam: AppDownloadParam) {
    }

    override fun onTaskCancel(appDownloadParam: AppDownloadParam) {
    }
}