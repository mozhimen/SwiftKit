package com.mozhimen.componentk.netk.app.download.commons

import com.mozhimen.componentk.netk.app.download.db.AppDownloadParam

/**
 * @ClassName IDownloadStateListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 18:24
 * @Version 1.0
 */
interface INetKAppDownloadState {
//    /**
//     * 预约状态发生变化的回调
//     *@param appFileParams 应用Id
//     *@param booked 预约状态 true 已预约 false 未预约
//     *
//     */
//    fun onReservationStateChange(appFileParams: AppDownloadParam, booked: Boolean)

    /**
     * 下载开始的回调
     */
    fun onDownloadStart(appDownloadParam: AppDownloadParam)

    /**
     * 下载进度回调方法
     * @param progress 已下载的进度
     */
    fun onDownloadProgress(appDownloadParam: AppDownloadParam, progress: Int)

    /**
     * 下载取消的回调
     */
    fun onDownloadPause(appDownloadParam: AppDownloadParam)

    /**
     * 下载成功的回调 不做任何事 此时会去校验应用或者解压npk
     */
    fun onDownloadSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 下载失败的回调
     */
    fun onDownloadFail(appDownloadParam: AppDownloadParam)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 应用校验
     */
    fun onVerifyStart(appDownloadParam: AppDownloadParam)

    /**
     * 应用校验成功
     */
    fun onVerifySuccess(appDownloadParam: AppDownloadParam)

    /**
     * 应用校验失败
     */
    fun onVerifyFail(appDownloadParam: AppDownloadParam)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 解压中
     */
    fun onUnzipStart(appDownloadParam: AppDownloadParam)

    /**
     * 解压成功
     */
    fun onUnzipSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 解压失败
     */
    fun onUnzipFail(appDownloadParam: AppDownloadParam)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 去安装
     */
    fun onInstallStart(appDownloadParam: AppDownloadParam)

    /**
     * 应用安装的监听
     */
    fun onInstallSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 应用安装的监听
     */
    fun onInstallFail(appDownloadParam: AppDownloadParam)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 应用卸载的监听
     */
    fun onUninstallStart(appDownloadParam: AppDownloadParam)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 任务删除的回调
     */
    fun onTaskStart(appDownloadParam: AppDownloadParam)

    /**
     * 任务删除的回调
     */
    fun onTaskCancel(appDownloadParam: AppDownloadParam)
}