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

    override fun onDownloadProgress(appDownloadParam: AppDownloadParam, complete: Int) {
    }

    override fun onDeleteTask(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadCanceled(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onDownloadFail(appDownloadParam: AppDownloadParam) {
    }

    override fun onChecking(appDownloadParam: AppDownloadParam) {
    }

    override fun onCheckingSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onCheckingFailure(appDownloadParam: AppDownloadParam) {
    }

    override fun onUnzip(appDownloadParam: AppDownloadParam) {
    }

    override fun onUnzipSuccess(appDownloadParam: AppDownloadParam) {
    }

    override fun onUnzipFailure(appDownloadParam: AppDownloadParam) {
    }

    override fun onGoInstall(appDownloadParam: AppDownloadParam) {
    }

    override fun onInstall(appDownloadParam: AppDownloadParam) {
    }

    override fun onUninstall(appDownloadParam: AppDownloadParam) {
    }
}