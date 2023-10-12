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
     * @param appDownloadParam app信息
     */
    fun onDownloadStart(appDownloadParam: AppDownloadParam)

    /**
     * 下载进度回调方法
     * @param appDownloadParam app信息
     * @param complete 已下载的进度
     */
    fun onDownloadProgress(appDownloadParam: AppDownloadParam, complete: Int)

    /**
     * 任务删除的回调
     * @param appDownloadParam app信息
     */
    fun onDeleteTask(appDownloadParam: AppDownloadParam)

    /**
     * 下载取消的回调
     * @param appDownloadParam app信息
     */
    fun onDownloadCanceled(appDownloadParam: AppDownloadParam)

    /**
     * 下载成功的回调 不做任何事 此时会去校验应用或者解压npk
     * @param appDownloadParam app信息
     */
    fun onDownloadSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 下载失败的回调
     * @param appDownloadParam app信息
     */
    fun onDownloadFail(appDownloadParam: AppDownloadParam)

    /**
     * 应用校验
     * @param appDownloadParam 应用信息
     */
    fun onChecking(appDownloadParam: AppDownloadParam)

    /**
     * 应用校验成功
     * @param appDownloadParam 应用信息
     */
    fun onCheckingSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 应用校验失败
     * @param appDownloadParam 应用信息
     */
    fun onCheckingFailure(appDownloadParam: AppDownloadParam)

    /**
     * 解压中
     * @param appDownloadParam app信息
     */
    fun onUnzip(appDownloadParam: AppDownloadParam)

    /**
     * 解压成功
     * @param appDownloadParam app信息
     */
    fun onUnzipSuccess(appDownloadParam: AppDownloadParam)

    /**
     * 解压失败
     * @param appDownloadParam app信息
     */
    fun onUnzipFailure(appDownloadParam: AppDownloadParam)

    /**
     * 去安装
     * @param appDownloadParam app信息
     */
    fun onGoInstall(appDownloadParam: AppDownloadParam)

    /**
     * 应用安装的监听
     * @param appDownloadParam app信息
     */
    fun onInstall(appDownloadParam: AppDownloadParam)

    /**
     * 应用卸载的监听
     * @param appDownloadParam app信息
     */
    fun onUninstall(appDownloadParam: AppDownloadParam)
}