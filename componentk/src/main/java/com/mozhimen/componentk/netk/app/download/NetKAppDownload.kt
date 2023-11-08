package com.mozhimen.componentk.netk.app.download

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import androidx.annotation.WorkerThread
import com.liulishuo.okdownload.core.exception.ServerCanceledException
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.file2strMd5
import com.mozhimen.componentk.netk.app.download.commons.IAppDownloadListener
import com.mozhimen.componentk.netk.app.download.commons.IAppDownloadStateListener
import com.mozhimen.componentk.netk.app.download.cons.CAppDownloadErrorCode
import com.mozhimen.componentk.netk.app.download.cons.CAppDownloadState
import com.mozhimen.componentk.netk.app.download.db.AppDownloadTask
import com.mozhimen.componentk.netk.app.download.db.DaoManager
import com.mozhimen.componentk.netk.app.download.helpers.AppZipManager
import com.mozhimen.componentk.netk.app.download.db.DatabaseManager
import com.mozhimen.componentk.netk.app.download.helpers.AppDownloadManager
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import java.io.File

/**
 * @ClassName NetKAppDownload
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 9:38
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKAppDownload : IAppDownloadStateListener, IUtilK {
    private val _appDownloadStateListeners = mutableListOf<IAppDownloadStateListener>()

    /////////////////////////////////////////////////////////////////

    fun init(context: Context) {
        AppDownloadManager.init(context)
        DatabaseManager.init(context)
    }

    fun registerDownloadStateListener(listener: IAppDownloadStateListener) {
        if (!_appDownloadStateListeners.contains(listener)) {
            _appDownloadStateListeners.add(listener)
        }
    }

    fun unregisterDownloadListener(listener: IAppDownloadStateListener) {
        val indexOf = _appDownloadStateListeners.indexOf(listener)
        if (indexOf >= 0)
            _appDownloadStateListeners.removeAt(indexOf)
    }

    /////////////////////////////////////////////////////////////////

    fun taskCreate(appDownloadTask: AppDownloadTask, listener: IAppDownloadListener? = null) {
        try {
            if (appDownloadTask.apkFileSize != 0L) {
                //当前剩余的空间
                val availMemory = UtilKFileDir.External.getFilesRootFreeSpace()
                //需要的最小空间
                val needMinMemory: Long = (appDownloadTask.apkFileSize * 1.2).toLong()
                //如果当前需要的空间大于剩余空间，提醒清理空间
                if (availMemory < needMinMemory) {
                    throw AppDownloadException(CAppDownloadErrorCode.CODE_DOWNLOAD_NEED_MEMORY)
                }

                //判断是否为npk,如果是npk,判断空间是否小于需要的2.2倍，如果小于2.2，提示是否继续
                if (appDownloadTask.apkSaveName.endsWith(".npk")) {
                    //警告空间
                    val warningsMemory: Long = (appDownloadTask.apkFileSize * 2.2).toLong()
                    //如果当前空间小于警告空间，
                    if (availMemory < warningsMemory) {
                        /*                    NiuAlertDialog.Builder(currentActivity)
                                                .setTitle("提示")
                                                .setMessage("存储空间不足，可能会导致安装失败,是否继续下载？")
                                                .setLeftButton("是") { dialog, witch ->
                                                    DownloadManager.download(appDownloadParam)
                                                    downloadCallback?.invoke(true)
                                                    dialog.dismiss()
                                                }
                                                .setRightButton("否") { dialog, witch ->
                                                    dialog.dismiss()
                                                }
                                                .show()*/
                        throw AppDownloadException(CAppDownloadErrorCode.CODE_INSTALL_NEED_MEMORY)
                    }
                }
            }
            AppDownloadManager.download(appDownloadTask)
            listener?.onSuccess()
        } catch (e: AppDownloadException) {
            listener?.onFail(e.code)
        }
    }

    fun taskWaitCancel(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_WAIT_CANCEL)
        AppDownloadManager.waitCancel(appDownloadTask)
    }

    fun taskCancel(appDownloadTask: AppDownloadTask, onDelete: IAB_Listener<Boolean, Int>) {
        if (AppZipManager.isUnziping(appDownloadTask)) {
            onDelete(false, CAppDownloadErrorCode.CODE_UNZIPING)
            return
        }
        TaskKExecutor.execute(TAG + "taskCancel") {
            AppDownloadManager.deleteOnBack(appDownloadTask, onDelete)//从数据库中移除掉
        }
    }

    fun downloadPause(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_PAUSE)
        AppDownloadManager.pause(appDownloadTask)
    }

    fun installCreate(appDownloadTask: AppDownloadTask) {
        //如果文件以.npk结尾则先解压
        if (appDownloadTask.apkSaveName.endsWith(".npk"))
            installNpk(appDownloadTask)
        else
            installApk(appDownloadTask)
    }

    /////////////////////////////////////////////////////////////////

    /**
     *  根据游戏id查询下载信息
     */
    fun getAppDownloadTaskByDownloadId(downloadId: String): AppDownloadTask? {
        return DaoManager.getByDownloadId(downloadId)
    }

    /**
     * 通过保存名称获取下载信息
     */
    fun getAppDownloadTaskByApkSaveName(apkSaveName: String): AppDownloadTask? {
        return DaoManager.getByApkSaveName(apkSaveName)
    }

    /**
     * 通过包名获取下载信息
     */
    fun getAppDownloadTaskByApkPackageName(apkPackageName: String): AppDownloadTask? {
        return DaoManager.getByApkPackageName(apkPackageName)
    }

    /**
     * 是否有正在下载的任务
     */
    fun hasDownloading(): Boolean {
        return DaoManager.hasDownloading()
    }

    /**
     * 是否有正在校验的任务
     */
    fun hasVerifying(): Boolean {
        return DaoManager.hasVerifying()
    }

    /**
     * 判断是否正在下载中
     * @return true 正在下载中  false 当前不是正在下载中
     */
    fun isDownloading(appDownloadTask: AppDownloadTask): Boolean {
        return AppDownloadManager.getAppDownloadProgress(appDownloadTask).isDownloading()//查询下载状态
    }

    /////////////////////////////////////////////////////////////////

    override fun onTaskCreate(appDownloadTask: AppDownloadTask) {
        TaskKExecutor.execute(TAG + "onTaskCreate") {
            val downloadId = DaoManager.getByDownloadId(appDownloadTask.taskId)//更新本地数据库中的数据
            if (downloadId != null) {
                applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_CREATE, 0)
            } else {
                DaoManager.addAll(appDownloadTask)
            }
        }
    }

    override fun onTaskWait(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_WAIT)
    }

    override fun onTaskCancel(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_CANCEL)
    }

    override fun onTaskSuccess(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_SUCCESS)
    }

    override fun onTaskFail(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_TASK_FAIL)
    }

    override fun onDownloadCreate(appDownloadTask: AppDownloadTask) {
        /*        //将结果传递给服务端
                GlobalScope.launch(Dispatchers.IO) {
                    if (appDownloadParam.appId == "2") {
                        ApplicationService.updateAppDownload("1")
                    } else {
                        ApplicationService.updateAppDownload(appDownloadParam.appId)
                    }
                }*/
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_CREATE)
    }

    override fun onDownloading(appDownloadTask: AppDownloadTask, progress: Int) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOADING, progress)
    }

    override fun onDownloadPause(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_PAUSE)
    }

    override fun onDownloadCancel(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_CANCEL)
    }

    override fun onDownloadSuccess(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_SUCCESS, nextMethod = {
            installCreate(appDownloadTask)//下载完成，去安装
        })
    }

    fun onDownloadFail(appDownloadTask: AppDownloadTask, exception: Exception?) {
        if (exception is ServerCanceledException) {
            if (exception.responseCode == 404 && appDownloadTask.downloadUrlCurrent != appDownloadTask.downloadUrl && appDownloadTask.downloadUrl.isNotEmpty()) {
                appDownloadTask.downloadUrlCurrent = appDownloadTask.downloadUrl
                taskCreate(appDownloadTask)
            } else
                onDownloadFail(appDownloadTask)
        } else {
            onDownloadFail(appDownloadTask)
        }
    }

    override fun onDownloadFail(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_DOWNLOAD_FAIL)
    }

    /////////////////////////////////////////////////////////////////

    override fun onVerifyCreate(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_VERIFY_CREATE)
    }

    override fun onVerifying(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_VERIFYING)
    }

    override fun onVerifySuccess(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_VERIFY_SUCCESS)
    }

    override fun onVerifyFail(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_VERIFY_FAIL)
    }

    /////////////////////////////////////////////////////////////////

    override fun onUnzipCreate(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_UNZIP_CREATE)
    }

    override fun onUnziping(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_UNZIPING)
    }

    override fun onUnzipSuccess(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_UNZIP_SUCCESS)
    }

    override fun onUnzipFail(appDownloadTask: AppDownloadTask) {
        //            AlertTools.showToast("解压失败，请检测存储空间是否足够！")
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_UNZIP_FAIL)
    }

    /////////////////////////////////////////////////////////////////

    override fun onInstallCreate(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_INSTALL_CREATE)
    }

    override fun onInstalling(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask, CAppDownloadState.STATE_INSTALLING)
    }

    override fun onInstallSuccess(appDownloadTask: AppDownloadTask) {
        if (appDownloadTask.apkPackageName.isEmpty()) return
        TaskKExecutor.execute(TAG + "onInstallSuccess") {
            val appDownloadParam = DaoManager.getByApkPackageName(appDownloadTask.apkPackageName) ?: return@execute//从本地数据库中查询出下载信息//如果查询不到，就不处理
            if (appDownloadParam.apkIsInstalled)//删除数据库中的其他已安装的数据，相同包名的只保留一条已安装的数据
                DaoManager.deleteOnBack(appDownloadParam)
            //将安装状态发给后端
            /*            GlobalScope.launch(Dispatchers.IO) {
                            ApplicationService.install(appDownloadParam0.appId)
                        }*/
            //将安装状态更新到数据库中
            applyAppDownloadTaskStateOnBack(appDownloadTask.apply {  apkIsInstalled = true},CAppDownloadState.STATE_INSTALL_SUCCESS,0)//更新安装的状态为1 q })
            /*            //TODO 如果设置自动删除安装包，安装成功后删除安装包
                        if (AutoDeleteApkSettingHelper.isAutoDelete()) {
                            if (deleteApkFile(appDownloadParam0)) {
                                HandlerHelper.post {
                                    AlertTools.showToast("文件已经删除！")
                                }
                            }
                        }*/
        }
    }

    override fun onInstallFail(appDownloadTask: AppDownloadTask) {
        applyAppDownloadTaskState(appDownloadTask,CAppDownloadState.STATE_INSTALL_FAIL)
    }

    /////////////////////////////////////////////////////////////////

    override fun onUninstallCreate(appDownloadTask: AppDownloadTask) {
        if (appDownloadTask.apkPackageName.isEmpty()) return
        TaskKExecutor.execute(TAG + "onUnInstall") {
            val appDownloadParam = DaoManager.getByApkPackageName(appDownloadTask.apkPackageName) ?: return@execute
            applyAppDownloadTaskStateOnBack(appDownloadParam.apply { apkIsInstalled = false },CAppDownloadState.STATE_UNINSTALL_CREATE,0)//设置为未安装
        }
    }

    /////////////////////////////////////////////////////////////////

    private fun applyAppDownloadTaskState(appDownloadTask: AppDownloadTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null) {
        DaoManager.update(appDownloadTask.apply {
            taskState = state
            if (progress > -1) downloadProgress = progress
        })
        postAppDownloadTaskState(appDownloadTask, state, progress, nextMethod)
    }

    @WorkerThread
    private fun applyAppDownloadTaskStateOnBack(appDownloadTask: AppDownloadTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null) {
        DaoManager.updateOnBack(appDownloadTask.apply {
            taskState = state
            if (progress > -1) downloadProgress = progress
        })
        postAppDownloadTaskState(appDownloadTask, state, progress, nextMethod)
    }

    private fun postAppDownloadTaskState(appDownloadTask: AppDownloadTask, state: Int, progress: Int = -1, nextMethod: I_Listener? = null){
        TaskKHandler.post {
            for (listener in _appDownloadStateListeners) {
                when (state) {
                    CAppDownloadState.STATE_TASK_CREATE -> listener.onTaskCreate(appDownloadTask)
                    CAppDownloadState.STATE_TASK_WAIT -> listener.onTaskWait(appDownloadTask)
                    CAppDownloadState.STATE_TASK_WAIT_CANCEL -> listener.onTaskWaitCancel(appDownloadTask)
                    CAppDownloadState.STATE_TASK_CANCEL -> listener.onTaskCancel(appDownloadTask)
                    CAppDownloadState.STATE_TASK_SUCCESS -> listener.onTaskSuccess(appDownloadTask)
                    CAppDownloadState.STATE_TASK_FAIL -> listener.onTaskFail(appDownloadTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CAppDownloadState.STATE_DOWNLOAD_CREATE -> listener.onDownloadCreate(appDownloadTask)
                    CAppDownloadState.STATE_DOWNLOADING -> listener.onDownloading(appDownloadTask, progress)
                    CAppDownloadState.STATE_DOWNLOAD_PAUSE -> listener.onDownloadPause(appDownloadTask)
                    CAppDownloadState.STATE_DOWNLOAD_CANCEL -> listener.onDownloadCancel(appDownloadTask)
                    CAppDownloadState.STATE_DOWNLOAD_SUCCESS -> listener.onDownloadSuccess(appDownloadTask)
                    CAppDownloadState.STATE_DOWNLOAD_FAIL -> listener.onDownloadFail(appDownloadTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CAppDownloadState.STATE_VERIFY_CREATE -> listener.onVerifyCreate(appDownloadTask)
                    CAppDownloadState.STATE_VERIFYING -> listener.onVerifying(appDownloadTask)
                    CAppDownloadState.STATE_VERIFY_SUCCESS -> listener.onVerifySuccess(appDownloadTask)
                    CAppDownloadState.STATE_VERIFY_FAIL -> listener.onVerifyFail(appDownloadTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CAppDownloadState.STATE_UNZIP_CREATE -> listener.onUnzipCreate(appDownloadTask)
                    CAppDownloadState.STATE_UNZIPING -> listener.onUnziping(appDownloadTask)
                    CAppDownloadState.STATE_UNZIP_SUCCESS -> listener.onUnzipSuccess(appDownloadTask)
                    CAppDownloadState.STATE_UNZIP_FAIL -> listener.onUnzipFail(appDownloadTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CAppDownloadState.STATE_INSTALL_CREATE -> listener.onInstallCreate(appDownloadTask)
                    CAppDownloadState.STATE_INSTALLING -> listener.onInstalling(appDownloadTask)
                    CAppDownloadState.STATE_INSTALL_SUCCESS -> listener.onInstallSuccess(appDownloadTask)
                    CAppDownloadState.STATE_INSTALL_FAIL -> listener.onInstallFail(appDownloadTask)
                    ///////////////////////////////////////////////////////////////////////////////
                    CAppDownloadState.STATE_UNINSTALL_CREATE -> listener.onUninstallCreate(appDownloadTask)
                }
            }
            nextMethod?.invoke()
        }
    }

    /**
     * 安装apk文件
     */
    private fun installApk(appDownloadTask: AppDownloadTask) {
        if (appDownloadTask.apkFileMd5.isEmpty() || "NONE" == appDownloadTask.apkFileMd5) {//如果文件没有MD5值或者为空，则不校验 直接去安装
            InstallApkHelper.installApk(appDownloadTask, File(UtilKFileDir.External.getFilesDownloadsDir() ?: return, appDownloadTask.apkSaveName))
            return
        }
        TaskKExecutor.execute(TAG + "installApk") {
            val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: kotlin.run {
                /**
                 * [CAppDownloadState.STATE_VERIFY_FAIL]
                 */
                onVerifyFail(appDownloadTask)
                return@execute
            }
            val apkFile = File(externalFilesDir, appDownloadTask.apkSaveName)
            if (!apkFile.exists()) {
                /**
                 * [CAppDownloadState.STATE_VERIFY_FAIL]
                 */
                onVerifyFail(appDownloadTask)
                return@execute
            }
            //判断是否需要校验MD5值
            if (isNeedVerify(appDownloadTask)) {
                /**
                 * @see CAppDownloadState.STATE_VERIFYING
                 */
                onVerifying(appDownloadTask)

                val apkFileMd5Remote = appDownloadTask.apkFileMd5//如果本地文件存在，且MD5值相等
                if (apkFileMd5Remote.isNotEmpty()) {
                    val apkFileMd5Locale = (apkFile.file2strMd5() ?: "") /*+ "1"*///取文件的MD5值
                    if (!TextUtils.equals(apkFileMd5Remote, apkFileMd5Locale)) {
                        onVerifyFail(appDownloadTask)

                        getApkSavePathName(appDownloadTask)?.deleteFile()//删除本地文件

                        if (appDownloadTask.downloadUrlCurrent != appDownloadTask.downloadUrl) {//重新使用内部地址下载
                            if (appDownloadTask.downloadUrl.isNotEmpty()) {
                                appDownloadTask.downloadUrlCurrent = appDownloadTask.downloadUrl
                                taskCreate(appDownloadTask)
                            } else {
                                //重新下载，下次不校验MD5值
                                appDownloadTask.apkVerifyNeed = true
                                taskCreate(appDownloadTask)
                            }
                        }
                        return@execute
                    }
                }
                //检测通过，去安装
                onVerifySuccess(appDownloadTask)
            }
            //调用安装的回调
            onInstallCreate(appDownloadTask)
            installApkOnMain(appDownloadTask, apkFile)
        }
    }

    /**
     * 安装.npk文件
     */
    private fun installNpk(appDownloadTask: AppDownloadTask) {
        if (AppZipManager.isUnziping(appDownloadTask)) //正在解压中，不进行操作
            return

        TaskKExecutor.execute(TAG + "installNpk") {
            if (isNeedVerify(appDownloadTask)) {
                onChecking(appDownloadTask)
                val context = NiuActivityManager.getCurrentActivity() ?: return@execute
                val externalFilesDir =
                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                if (externalFilesDir == null) {
                    onCheckingFailure(appDownloadTask)
                    return@execute
                }
                val file = File(externalFilesDir, appDownloadTask.saveName)
                if (!file.exists()) {
                    onCheckingFailure(appDownloadTask)
                    return@execute
                }
                val fileMd5 = appDownloadTask.fileMd5
                if (fileMd5.isNotEmpty() && "NONE" != fileMd5) {
                    //取文件的MD5值
                    val localFileMD5 = MD5Helper.getFileMD5(file)
                    if (!TextUtils.equals(fileMd5, localFileMD5)) {
                        onCheckingFailure(appDownloadTask)
                        return@execute
                    }
                }
                onCheckingSuccess(appDownloadTask)
            }
            onUnpacking(appDownloadTask)
            val unzip = ZipFileManger.unzip(appDownloadTask)
            if (unzip.isEmpty()) {
                //解压失败
                onUnzipFailure(appDownloadTask)
                return@execute
            }
            onUnzipSuccess(appDownloadTask)
            //调用安装的回调
            onGoInstall(appDownloadTask)
            installApkOnUi(appDownloadTask, File(unzip))
        }
    }

    /**
     * 判断是否需要校验MD5值
     * 1、NPK不需要校验MD5值
     * 2、如果是使用站内地址下载，不用校验MD5值
     * 3、如果使用站外地址，且没有站内地址，且第一次校验失败，则第二次时不用校验
     */
    private fun isNeedVerify(appDownloadTask: AppDownloadTask): Boolean {
        if (appDownloadTask.apkSaveName.endsWith(".npk"))
            return false
        //如果是使用站内地址下载，不用校验MD5值
        if (appDownloadTask.downloadUrlCurrent == appDownloadTask.downloadUrl) {
            return false
        }
        return appDownloadTask.apkVerifyNeed
    }

    private fun installApkOnMain(appFileParams: AppFileParams, file: File) {
        TaskKHandler.post {
            InstallApkHelper.installApk(appFileParams, file)
        }
    }

    /**
     * 获取本地保存的文件
     */
    private fun getApkSavePathName(appDownloadTask: AppDownloadTask): File? {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return null
        return File(externalFilesDir, appDownloadTask.apkSaveName)
    }

    /**
     * 删除Apk文件
     * @param appFileParams 需要删除的文件信息
     */
    private fun deleteApkFile(appFileParams: AppFileParams): Boolean {
        val context = NiuActivityManager.getCurrentActivity() ?: return true
        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            ?: return true
        //删除文件
        FileOperationsTools.deleteFile(File(externalFilesDir, appFileParams.saveName))
        //如果是npk,删除解压的文件夹
        if (appFileParams.saveName.endsWith(".npk")) {
            FileOperationsTools.deleteFolder(
                File(
                    externalFilesDir,
                    appFileParams.saveName.split(".npk")[0]
                ), true
            )
        }
        return true
    }
}