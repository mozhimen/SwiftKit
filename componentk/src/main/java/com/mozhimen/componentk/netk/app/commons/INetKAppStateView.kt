package com.mozhimen.componentk.netk.app.commons

import android.view.View
import com.mozhimen.componentk.netk.app.cons.ENetKAppFinishType
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.task.db.AppTask

/**
 * @ClassName INetKViewAppState
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/16 10:58
 * @Version 1.0
 */
interface INetKAppStateView<V : View> {
    fun onTaskCreate(view: V?, appTask: AppTask)
    fun onTaskWait(view: V?, appTask: AppTask) //任务等待的回调
    fun onTasking(view: V?, appTask: AppTask, state: Int)//任务进行中
    fun onTaskPause(view: V?, appTask: AppTask)
    fun onTaskFinish(view: V?, appTask: AppTask, finishType: ENetKAppFinishType)

    fun onDownloadWait(view: V?, appTask: AppTask) {}
    fun onDownloading(view: V?, appTask: AppTask, progress: Int, currentIndex: Long, totalIndex: Long, offsetIndexPerSeconds: Long) {}//下载进度回调方法
    fun onDownloadPause(view: V?, appTask: AppTask) {}//下载暂停的回调
    fun onDownloadCancel(view: V?, appTask: AppTask) {}//下载取消的回调
    fun onDownloadSuccess(view: V?, appTask: AppTask) {}//下载成功的回调 不做任何事 此时会去校验应用或者解压npk
    fun onDownloadFail(view: V?, appTask: AppTask, exception: AppDownloadException) {}//下载失败的回调

    fun onVerifying(view: V?, appTask: AppTask) {}//应用校验中
    fun onVerifySuccess(view: V?, appTask: AppTask) {}//应用校验成功
    fun onVerifyFail(view: V?, appTask: AppTask, exception: AppDownloadException) {}//应用校验失败

    fun onUnziping(view: V?, appTask: AppTask, progress: Int, currentIndex: Long, totalIndex: Long, offsetIndexPerSeconds: Long) {}//解压中
    fun onUnzipSuccess(view: V?, appTask: AppTask) {}//解压成功
    fun onUnzipFail(view: V?, appTask: AppTask, exception: AppDownloadException) {}//解压失败

    fun onInstalling(view: V?, appTask: AppTask) {}//安装中
    fun onInstallSuccess(view: V?, appTask: AppTask) {}//应用安装的监听
    fun onInstallCancel(view: V?, appTask: AppTask) {}//安装取消
    fun onInstallFail(view: V?, appTask: AppTask, exception: AppDownloadException) {}

    fun onUninstallSuccess(view: V?, appTask: AppTask) {}//应用卸载的监听
}