package com.mozhimen.componentk.netk.app.download.commons

import com.mozhimen.componentk.netk.app.download.db.AppDownloadTask

/**
 * @ClassName IDownloadStateListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 18:24
 * @Version 1.0
 */
interface IAppDownloadStateInstall {
    /**
     * 去安装
     */
    fun onInstallCreate(appDownloadTask: AppDownloadTask)

    /**
     * 安装中
     */
    fun onInstalling(appDownloadTask: AppDownloadTask)

    /**
     * 应用安装的监听
     */
    fun onInstallSuccess(appDownloadTask: AppDownloadTask)

    /**
     * 应用安装的监听
     */
    fun onInstallFail(appDownloadTask: AppDownloadTask)

    ///////////////////////////////////////////////////////////////////////////////////

    //    /**
//     * 应用卸载的监听
//     */
//    fun onUninstallStart(appDownloadParam: AppDownloadParam)
    fun onUninstallCreate(appDownloadTask: AppDownloadTask)
}

interface IAppDownloadStateVerify {
    /**
     * 应用校验
     */
    fun onVerifyCreate(appDownloadTask: AppDownloadTask)

    /**
     * 应用校验中
     */
    fun onVerifying(appDownloadTask: AppDownloadTask)

    /**
     * 应用校验成功
     */
    fun onVerifySuccess(appDownloadTask: AppDownloadTask)

    /**
     * 应用校验失败
     */
    fun onVerifyFail(appDownloadTask: AppDownloadTask)
}

interface IAppDownloadStateUnzip {
    /**
     * 解压中
     */
    fun onUnzipCreate(appDownloadTask: AppDownloadTask)

    /**
     * 解压中
     */
    fun onUnziping(appDownloadTask: AppDownloadTask)

    /**
     * 解压成功
     */
    fun onUnzipSuccess(appDownloadTask: AppDownloadTask)

    /**
     * 解压失败
     */
    fun onUnzipFail(appDownloadTask: AppDownloadTask)
}

interface IAppDownloadStateDownload {
    /**
     * 下载开始的回调
     */
    fun onDownloadCreate(appDownloadTask: AppDownloadTask)

    /**
     * 下载进度回调方法
     * @param progress 已下载的进度
     */
    fun onDownloading(appDownloadTask: AppDownloadTask, progress: Int)

    /**
     * 下载暂停的回调
     */
    fun onDownloadPause(appDownloadTask: AppDownloadTask)

    /**
     * 下载取消的回调
     */
    fun onDownloadCancel(appDownloadTask: AppDownloadTask)

    /**
     * 下载成功的回调 不做任何事 此时会去校验应用或者解压npk
     */
    fun onDownloadSuccess(appDownloadTask: AppDownloadTask)

    /**
     * 下载失败的回调
     */
    fun onDownloadFail(appDownloadTask: AppDownloadTask)

}

interface IAppDownloadStateListener : IAppDownloadStateDownload, IAppDownloadStateVerify, IAppDownloadStateUnzip, IAppDownloadStateInstall {
//    /**
//     * 预约状态发生变化的回调
//     *@param appFileParams 应用Id
//     *@param booked 预约状态 true 已预约 false 未预约
//     *
//     */
//    fun onReservationStateChange(appFileParams: AppDownloadParam, booked: Boolean)

    /**
     * 任务删除的回调
     */
    fun onTaskCreate(appDownloadTask: AppDownloadTask)

    /**
     * 任务等待的回调
     */
    fun onTaskWait(appDownloadTask: AppDownloadTask)


    fun onTaskWaitCancel(appDownloadTask: AppDownloadTask)

    /**
     * 任务删除的回调
     */
    fun onTaskCancel(appDownloadTask: AppDownloadTask)

    /**
     * 任务成功
     */
    fun onTaskSuccess(appDownloadTask: AppDownloadTask)

    /**
     * 任务失败
     */
    fun onTaskFail(appDownloadTask: AppDownloadTask)
}