package com.mozhimen.componentk.netk.app.utils

import android.content.Context
import android.view.View
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.utilk.android.content.UtilKContextStart
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState
import com.mozhimen.componentk.netk.app.task.db.AppTask

/**
 * @ClassName NetKAppViewUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/6 9:58
 * @Version 1.0
 */
object NetKAppViewUtil {
    @JvmStatic
    fun generateViewClickOfAppTask(
        view: View,
        appTask: AppTask,
        packageName: String,
        onStart: IAB_Listener<Context, AppTask>,
        onPause: IAB_Listener<Context, AppTask>,
        onResume: IAB_Listener<Context, AppTask>,
        onUnzip: IAB_Listener<Context, AppTask>,
        onInstall: IAB_Listener<Context, AppTask>,
        onCancel: IAB_Listener<Context, AppTask>
    ) {
        view.setOnClickListener {
            //获取当前最新的状态
//            val fileParams = appBriefRes.createAppFileParams()
//            onButtonClick?.invoke(it, fileParams.downloadState)
            when (appTask.taskState) {
                //如果是已安装，则打开App
                CNetKAppTaskState.STATE_TASK_SUCCESS -> {
                    //判断是否有更新，如果有更新，则下载最新版本
//                    if (appBriefRes.haveUpdate == 1) {
//                        download(it.context, button, fileParams)
//                    } else {
                    UtilKContextStart.startContextByPackageName(it.context, packageName)
//                    }
                }
                //如果是未下载，则下载app
                CNetKAppTaskState.STATE_TASK_CREATE/*, CNetKAppTaskState.STATE_TASK_WAIT*/ -> {
                    //startTask(it.context, appTask)
                    onStart.invoke(it.context, appTask)
                }
                //如果是下载完成，则去安装
                //如果是安装中，则去安装
//                AppState.APP_STATE_DOWNLOAD_COMPLETED, AppState.APP_STATE_INSTALLING -> {
//                    AppDownloadManager.install(
//                        fileParams
//                    )
//                }
                //如果是下载中，则暂停
                CNetKAppState.STATE_DOWNLOADING -> {
//                    NetKApp.taskPause(appTask)
                    onPause.invoke(it.context, appTask)
                }
                //如果是暂停、取消，则恢复
                CNetKAppTaskState.STATE_TASK_PAUSE -> {
//                AppState.APP_STATE_DOWNLOAD_CANCELED,
//                AppState.APP_STATE_PENDING_CANCELED -> {
//                    button.setText("等待中")
//                    NetKApp.taskResume(appTask)
                    onResume.invoke(it.context, appTask)
                }
                //等待中
//                AppState.APP_STATE_PENDING -> {
//                    button.setText("继续")
//                    AppDownloadManager.cancelPending(fileParams)
//                }

//                AppState.APP_STATE_DOWNLOAD_FAILED -> {
//                    textKProgress.setText("等待中")
//                    download(it.context, button, fileParams)
//                }

//                AppState.APP_STATE_CHECKING_FAILURE -> {
//                    textKProgress.setText("等待中")
//                    download(it.context, button, fileParams)
//                }
                CNetKAppState.STATE_VERIFY_SUCCESS, CNetKAppState.STATE_UNZIPING -> {
//                    NetKApp.unzip(appTask)
                    onUnzip.invoke(it.context, appTask)
                }

                CNetKAppState.STATE_UNZIP_SUCCESS, CNetKAppState.STATE_INSTALLING -> {
//                    startInstall(it.context, appTask)
                    onInstall.invoke(it.context, appTask)
                }

                else -> {
                    //解压中 AppState.APP_STATE_UNPACKING
                    //未安装 AppState.APP_STATE_NOT_INSTALLED
                    //安装中
                    //TODO 去详情页
                }
            }
        }
        view.setOnLongClickListener {
            when (appTask.taskState) {
                /*CNetKAppTaskState.STATE_TASK_WAIT,*/ CNetKAppState.STATE_DOWNLOADING, CNetKAppTaskState.STATE_TASK_PAUSE -> {
//                    cancelTask(it.context, appTask)
                    onCancel.invoke(it.context, appTask)
                }

                CNetKAppState.STATE_UNZIP_SUCCESS, CNetKAppState.STATE_INSTALLING -> {
                    onCancel.invoke(it.context, appTask)
                }
            }
            true
        }
    }
}