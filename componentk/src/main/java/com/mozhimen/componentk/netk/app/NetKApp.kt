package com.mozhimen.componentk.netk.app

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.ProcessLifecycleOwner
import com.liulishuo.okdownload.core.exception.ServerCanceledException
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.deleteFolder
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.commons.IAppStateListener
import com.mozhimen.componentk.netk.app.download.commons.IAppDownloadListener
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.cons.ENetKAppFinishType
import com.mozhimen.componentk.netk.app.task.db.AppTaskDaoManager
import com.mozhimen.componentk.netk.app.unzip.NetKAppUnzipManager
import com.mozhimen.componentk.netk.app.task.db.AppTaskDbManager
import com.mozhimen.componentk.netk.app.download.NetKAppDownloadManager
import com.mozhimen.componentk.netk.app.install.NetKAppInstallManager
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.install.NetKAppInstallProxy
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState
import java.io.File

/**
 * @ClassName NetKAppDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 9:38
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKApp : IAppStateListener, BaseUtilK() {
    private val _appDownloadStateListeners = mutableListOf<IAppStateListener>()

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    private val _netKAppInstallProxy by lazy { NetKAppInstallProxy(_context, ProcessLifecycleOwner.get()) }

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    val netKAppInstallProxy get() = _netKAppInstallProxy

    /////////////////////////////////////////////////////////////////
    // init
    /////////////////////////////////////////////////////////////////
    //region # init
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    fun init(context: Context) {
        _netKAppInstallProxy.bindLifecycle(ProcessLifecycleOwner.get())// 注册应用安装的监听 InstalledApkReceiver.registerReceiver(this)
        AppTaskDbManager.init(context)
        InstallKManager.init(context)
        NetKAppDownloadManager.init(context)
    }

    fun registerDownloadStateListener(listener: IAppStateListener) {
        if (!_appDownloadStateListeners.contains(listener)) {
            _appDownloadStateListeners.add(listener)
        }
    }

    fun unregisterDownloadListener(listener: IAppStateListener) {
        val indexOf = _appDownloadStateListeners.indexOf(listener)
        if (indexOf >= 0)
            _appDownloadStateListeners.removeAt(indexOf)
    }
    //endregion

    /////////////////////////////////////////////////////////////////
    // control
    /////////////////////////////////////////////////////////////////
    //region # control
    fun taskStart(appTask: AppTask) {
        try {
            if (appTask.isTaskProcess()) {
                Log.d(TAG, "taskStart: the task already start")
                return
            }
            if (appTask.apkFileSize != 0L) {
                //当前剩余的空间
                val availMemory = UtilKFileDir.External.getFilesRootFreeSpace()
                //需要的最小空间
                val needMinMemory: Long = (appTask.apkFileSize * 1.2).toLong()
                //如果当前需要的空间大于剩余空间，提醒清理空间
                if (availMemory < needMinMemory) {
                    throw AppDownloadException(CNetKAppErrorCode.CODE_TASK_NEED_MEMORY_APK)
                }

                //判断是否为npk,如果是npk,判断空间是否小于需要的2.2倍，如果小于2.2，提示是否继续
                if (appTask.apkName.endsWith(".npk")) {
                    //警告空间
                    val warningsMemory: Long = (appTask.apkFileSize * 2.2).toLong()
                    //如果当前空间小于警告空间，
                    if (availMemory < warningsMemory) {
                        /*                    NiuAlertDialog.Builder(currentActivity)
                                                .setTitle("提示")
                                                .setMessage("存储空间不足，可能会导致安装失败,是否继续下载？")
                                                .setLeftButton("是") { dialog, witch ->
                                                    DownloadManager.download(appTask)
                                                    downloadCallback?.invoke(true)
                                                    dialog.dismiss()
                                                }
                                                .setRightButton("否") { dialog, witch ->
                                                    dialog.dismiss()
                                                }
                                                .show()*/
                        throw AppDownloadException(CNetKAppErrorCode.CODE_TASK_NEED_MEMORY_NPK)
                    }
                }
            }
            AppTaskDaoManager.addAppTask2Database(appTask)

            /**
             * [CNetKAppTaskState.STATE_TASK_CREATE]
             */
            onTaskCreate(appTask)
            /**
             * [CNetKAppState.STATE_DOWNLOAD_CREATE]
             */
            onDownloadCreate(appTask)

            NetKAppDownloadManager.download(appTask/*, listener*/)
        } catch (e: AppDownloadException) {
            onTaskFinish(appTask, ENetKAppFinishType.FAIL(e))
//            listener?.onFail(e.code)
        }
    }

    fun taskCancel(appTask: AppTask, onCancelBlock: IAB_Listener<Boolean, Int>? = null) {
        if (!appTask.isTaskProcess()) {
            Log.d(TAG, "taskCancel: task is not process")
            return
        }
        if (appTask.isTaskDownload() && appTask.isTaskWait()) {
            Log.d(TAG, "taskCancel: downloadWaitCancel")
            NetKAppDownloadManager.downloadWaitCancel(appTask, onCancelBlock)

        } else if (appTask.isTaskDownload() && appTask.isDownloading()) {
            Log.d(TAG, "taskCancel: downloadCancelOnBack")
            TaskKExecutor.execute(TAG + "onTaskCancel") {
                NetKAppDownloadManager.downloadCancelOnBack(appTask, onCancelBlock)//从数据库中移除掉
            }

        } else if (appTask.isTaskUnzip() && NetKAppUnzipManager.isUnziping(appTask)) {
            onCancelBlock?.invoke(false, CNetKAppErrorCode.CODE_TASK_CANCEL_FAIL_ON_UNZIPING)
        }
    }

    fun taskPause(appTask: AppTask) {
        if (!appTask.isTaskProcess()) {
            Log.d(TAG, "taskPause: task is not process")
            return
        }
        if (appTask.isTaskDownload() && appTask.isTasking()) {
            Log.d(TAG, "taskPause: downloadPause")
            NetKAppDownloadManager.downloadPause(appTask)
        }
    }

    fun taskResume(appTask: AppTask) {
        if (!appTask.isTaskProcess()) {
            Log.d(TAG, "downloadResume: task is not process")
            return
        }
        if (appTask.isTaskDownload() && appTask.isTaskPause()) {
            Log.d(TAG, "taskPause: downloadResume")
            NetKAppDownloadManager.downloadResume(appTask)
        }
    }
    //endregion

    /////////////////////////////////////////////////////////////////
    // state
    /////////////////////////////////////////////////////////////////
    //region # state
    /**
     *  根据游戏id查询下载信息
     */
    fun getAppTaskByDownloadId(downloadId: String): AppTask? {
        return AppTaskDaoManager.getByTaskId(downloadId)
    }

    /**
     * 通过保存名称获取下载信息
     */
    fun getAppTaskByApkSaveName(apkSaveName: String): AppTask? {
        return AppTaskDaoManager.getByApkName(apkSaveName)
    }

    /**
     * 通过包名获取下载信息
     */
    fun getAppTaskByApkPackageName(apkPackageName: String): AppTask? {
        return AppTaskDaoManager.getByApkPackageName(apkPackageName)
    }

    /**
     * 是否有正在下载的任务
     */
    fun hasDownloading(): Boolean {
        return AppTaskDaoManager.hasDownloading()
    }

    /**
     * 是否有正在校验的任务
     */
    fun hasVerifying(): Boolean {
        return AppTaskDaoManager.hasVerifying()
    }

    /**
     * 是否有正在解压的任务
     */
    fun hasUnziping(): Boolean {
        return AppTaskDaoManager.hasUnziping()
    }

    /**
     * 判断是否正在下载中
     * @return true 正在下载中  false 当前不是正在下载中
     */
    fun isDownloading(appTask: AppTask): Boolean {
        return NetKAppDownloadManager.getAppDownloadProgress(appTask).isDownloading()//查询下载状态
    }
    //endregion

    /////////////////////////////////////////////////////////////////

    override fun onTaskCreate(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_CREATE)
    }

    override fun onTaskWait(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_WAIT)
    }

    override fun onTasking(appTask: AppTask, state: Int) {
        applyAppTaskState(appTask, state)
    }

    override fun onTaskPause(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_PAUSE)
    }

    override fun onTaskFinish(appTask: AppTask, finishType: ENetKAppFinishType) {
        when (finishType) {
            ENetKAppFinishType.SUCCESS -> applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_SUCCESS, 0)

            ENetKAppFinishType.CANCEL -> applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_CANCEL, nextMethod = {
                onTaskCreate(appTask)
            })

            is ENetKAppFinishType.FAIL -> applyAppTaskState(appTask, CNetKAppTaskState.STATE_TASK_FAIL)
        }
    }

    /////////////////////////////////////////////////////////////////

    override fun onDownloadCreate(appTask: AppTask) {
        /*        //将结果传递给服务端
                GlobalScope.launch(Dispatchers.IO) {
                    if (appTask.appId == "2") {
                        ApplicationService.updateAppDownload("1")
                    } else {
                        ApplicationService.updateAppDownload(appTask.appId)
                    }
                }*/
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_CREATE)
    }

    override fun onDownloadWait(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_WAIT, 0, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_WAIT]
             */
            onTaskWait(appTask)
        })
    }

    override fun onDownloading(appTask: AppTask, progress: Int) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOADING, progress, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_DOWNLOADING)
        })
    }

    override fun onDownloadPause(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_PAUSE, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_PAUSE]
             */
            onTaskPause(appTask)
        })
    }

    override fun onDownloadCancel(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_CANCEL, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_CANCEL]
             */
            onTaskFinish(appTask, ENetKAppFinishType.CANCEL)
        })
    }

    override fun onDownloadSuccess(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_SUCCESS, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_DOWNLOAD_SUCCESS)
        })
    }

    fun onDownloadFail(appTask: AppTask, exception: Exception?) {
        if (exception is ServerCanceledException) {
            if (exception.responseCode == 404 && appTask.downloadUrlCurrent != appTask.downloadUrl && appTask.downloadUrl.isNotEmpty()) {
                appTask.downloadUrlCurrent = appTask.downloadUrl
                appTask.taskState = CNetKAppTaskState.STATE_TASK_CREATE
                taskStart(appTask)
            } else {
                /**
                 * [CNetKAppState.STATE_DOWNLOAD_FAIL]
                 */
                onDownloadFail(appTask, AppDownloadException(CNetKAppErrorCode.CODE_DOWNLOAD_SERVER_CANCELED, exception.message ?: ""))
            }
        } else {
            /**
             * [CNetKAppState.STATE_DOWNLOAD_FAIL]
             */
            onDownloadFail(appTask, AppDownloadException(CNetKAppErrorCode.CODE_DOWNLOAD_SERVER_CANCELED))
        }
    }

    override fun onDownloadFail(appTask: AppTask, exception: AppDownloadException) {
        applyAppTaskState(appTask, CNetKAppState.STATE_DOWNLOAD_FAIL, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_FAIL]
             */
            onTaskFinish(appTask, ENetKAppFinishType.FAIL(exception))
        })
    }

    /////////////////////////////////////////////////////////////////

    override fun onVerifying(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_VERIFYING, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_VERIFYING)
        })
    }

    override fun onVerifySuccess(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_VERIFY_SUCCESS, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_VERIFY_SUCCESS)
        })
    }

    override fun onVerifyFail(appTask: AppTask, exception: AppDownloadException) {
        applyAppTaskState(appTask, CNetKAppState.STATE_VERIFY_FAIL, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_FAIL]
             */
            onTaskFinish(appTask, ENetKAppFinishType.FAIL(exception))
        })
    }

    /////////////////////////////////////////////////////////////////

    override fun onUnziping(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_UNZIPING, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_UNZIPING)
        })
    }

    override fun onUnzipSuccess(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_UNZIP_SUCCESS, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_UNZIP_SUCCESS)
        })
    }

    override fun onUnzipFail(appTask: AppTask, exception: AppDownloadException) {
        //            AlertTools.showToast("解压失败，请检测存储空间是否足够！")
        applyAppTaskState(appTask, CNetKAppState.STATE_UNZIP_FAIL, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_FAIL]
             */
            onTaskFinish(appTask, ENetKAppFinishType.FAIL(exception))
        })
    }

    /////////////////////////////////////////////////////////////////

    override fun onInstalling(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_INSTALLING, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASKING]
             */
            onTasking(appTask, CNetKAppState.STATE_INSTALLING)
        })
    }

    override fun onInstallSuccess(appTask: AppTask) {
        applyAppTaskState(appTask, CNetKAppState.STATE_INSTALL_SUCCESS, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_SUCCESS]
             */
            onTaskFinish(appTask, ENetKAppFinishType.SUCCESS)
        })
    }

    override fun onInstallFail(appTask: AppTask, exception: AppDownloadException) {
        applyAppTaskState(appTask, CNetKAppState.STATE_INSTALL_FAIL, nextMethod = {
            /**
             * [CNetKAppTaskState.STATE_TASK_FAIL]
             */
            onTaskFinish(appTask, ENetKAppFinishType.FAIL(exception))
        })
    }

    /////////////////////////////////////////////////////////////////

    override fun onUninstallSuccess(appTask: AppTask) {
        if (appTask.apkPackageName.isEmpty()) return
        val appTask = AppTaskDaoManager.getByApkPackageName(appTask.apkPackageName) ?: return
        applyAppTaskState(appTask.apply { apkIsInstalled = false }, CNetKAppState.STATE_UNINSTALL_CREATE, 0)//设置为未安装
    }

    /////////////////////////////////////////////////////////////////

    private fun applyAppTaskState(appTask: AppTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null) {
        AppTaskDaoManager.update(appTask.apply {
            taskState = state
            if (progress > -1) downloadProgress = progress
        })
        postAppTaskState(appTask, state, progress, nextMethod)
    }

    @WorkerThread
    private fun applyAppTaskStateOnBack(appTask: AppTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null) {
        AppTaskDaoManager.updateOnBack(appTask.apply {
            taskState = state
            if (progress > -1) downloadProgress = progress
        })
        postAppTaskState(appTask, state, progress, nextMethod)
    }

    private fun postAppTaskState(appTask: AppTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null) {
        TaskKHandler.post {
            for (listener in _appDownloadStateListeners) {
                when (state) {
                    CNetKAppState.STATE_TASK_CREATE -> listener.onTaskCreate(appTask)
                    CNetKAppState.STATE_TASK_WAIT -> listener.onTaskWait(appTask)
                    CNetKAppState.STATE_TASK_WAIT_CANCEL -> listener.onTaskWaitCancel(appTask)
                    CNetKAppState.STATE_TASK_CANCEL -> listener.onTaskCancel(appTask)
                    CNetKAppState.STATE_TASK_SUCCESS -> listener.onTaskSuccess(appTask)
                    CNetKAppState.STATE_TASK_FAIL -> listener.onTaskFail(appTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CNetKAppState.STATE_DOWNLOAD_CREATE -> listener.onDownloadCreate(appTask)
                    CNetKAppState.STATE_DOWNLOADING -> listener.onDownloading(appTask, progress)
                    CNetKAppState.STATE_DOWNLOAD_PAUSE -> listener.onDownloadPause(appTask)
                    CNetKAppState.STATE_DOWNLOAD_CANCEL -> listener.onDownloadCancel(appTask)
                    CNetKAppState.STATE_DOWNLOAD_SUCCESS -> listener.onDownloadSuccess(appTask)
                    CNetKAppState.STATE_DOWNLOAD_FAIL -> listener.onDownloadFail(appTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CNetKAppState.STATE_VERIFY_CREATE -> listener.onVerifyCreate(appTask)
                    CNetKAppState.STATE_VERIFYING -> listener.onVerifying(appTask)
                    CNetKAppState.STATE_VERIFY_SUCCESS -> listener.onVerifySuccess(appTask)
                    CNetKAppState.STATE_VERIFY_FAIL -> listener.onVerifyFail(appTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CNetKAppState.STATE_UNZIP_CREATE -> listener.onUnzipCreate(appTask)
                    CNetKAppState.STATE_UNZIPING -> listener.onUnziping(appTask)
                    CNetKAppState.STATE_UNZIP_SUCCESS -> listener.onUnzipSuccess(appTask)
                    CNetKAppState.STATE_UNZIP_FAIL -> listener.onUnzipFail(appTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CNetKAppState.STATE_INSTALL_CREATE -> listener.onInstallCreate(appTask)
                    CNetKAppState.STATE_INSTALLING -> listener.onInstalling(appTask)
                    CNetKAppState.STATE_INSTALL_SUCCESS -> listener.onInstallSuccess(appTask)
                    CNetKAppState.STATE_INSTALL_FAIL -> listener.onInstallFail(appTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CNetKAppState.STATE_UNINSTALL_SUCCESS -> listener.onUninstallSuccess(appTask)
                }
            }
            nextMethod?.invoke()
        }
    }
}