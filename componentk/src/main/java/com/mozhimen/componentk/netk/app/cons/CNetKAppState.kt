package com.mozhimen.componentk.netk.app.cons

import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState

/**
 * @ClassName CAppDownloadState
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 11:36
 * @Version 1.0
 */
object CNetKAppState {
    private const val STATE_DOWNLOAD_CREATE = 10//STATE_DOWNLOADED = 5//未下载
//    const val STATE_DOWNLOAD_WAIT = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASK_WAIT//11//下载等待
    const val STATE_DOWNLOADING = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASKING//12//STATE_DOWNLOAD_IN_PROGRESS = 6//正在下载
    const val STATE_DOWNLOAD_PAUSE = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASK_PAUSE//13//STATE_DOWNLOAD_PAUSED = 7//下载暂停
    const val STATE_DOWNLOAD_CANCEL = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASK_CANCEL//17//下载取消
    const val STATE_DOWNLOAD_SUCCESS = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASK_SUCCESS//18//STATE_DOWNLOAD_COMPLETED = 8//下载完成
    const val STATE_DOWNLOAD_FAIL = STATE_DOWNLOAD_CREATE + CNetKAppTaskState.STATE_TASK_FAIL//19//STATE_DOWNLOAD_FAILED = 10//下载失败

    @JvmStatic
    fun isTaskDownload(state: Int): Boolean =
        state in STATE_DOWNLOAD_CREATE..STATE_DOWNLOAD_FAIL

    @JvmStatic
    fun isDownloading(state: Int): Boolean =
        state == STATE_DOWNLOADING

    //////////////////////////////////////////////////////////////
    //校验
    private const val STATE_VERIFY_CREATE = 20
    const val STATE_VERIFYING = STATE_VERIFY_CREATE + CNetKAppTaskState.STATE_TASKING//20//STATE_CHECKING = 14//校验中
    const val STATE_VERIFY_SUCCESS = STATE_VERIFY_CREATE + CNetKAppTaskState.STATE_TASK_SUCCESS//28//STATE_CHECKING_SUCCESS = 15//校验成功
    const val STATE_VERIFY_FAIL = STATE_VERIFY_CREATE + CNetKAppTaskState.STATE_TASK_FAIL//29//STATE_CHECKING_FAILURE = 16//校验失败

    @JvmStatic
    fun isTaskVerify(state: Int): Boolean =
        state in STATE_VERIFY_CREATE..STATE_VERIFY_FAIL

    //////////////////////////////////////////////////////////////
    //解压
    private const val STATE_UNZIP_CREATE = 30
    const val STATE_UNZIPING = STATE_UNZIP_CREATE + CNetKAppTaskState.STATE_TASKING//30//STATE_UNPACKING = 11//解压中
    const val STATE_UNZIP_SUCCESS = STATE_UNZIP_CREATE + CNetKAppTaskState.STATE_TASK_SUCCESS//38//STATE_UNPACKING_SUCCESSFUL = 12//解压成功
    const val STATE_UNZIP_FAIL = STATE_UNZIP_CREATE + CNetKAppTaskState.STATE_TASK_FAIL//39//STATE_UNPACKING_FAILED = 13//解压失败

    @JvmStatic
    fun isTaskUnzip(state: Int): Boolean =
        state in STATE_UNZIP_CREATE..STATE_UNZIP_FAIL

    @JvmStatic
    fun isUnzipSuccess(state: Int): Boolean =
        state == STATE_UNZIP_SUCCESS

    //////////////////////////////////////////////////////////////
    //安装
    private const val STATE_INSTALL_CREATE = 40
    const val STATE_INSTALLING = STATE_INSTALL_CREATE + CNetKAppTaskState.STATE_TASKING//40//STATE_INSTALLING = 1//安装中
    const val STATE_INSTALL_SUCCESS = STATE_INSTALL_CREATE + CNetKAppTaskState.STATE_TASK_SUCCESS//48//STATE_INSTALLED = 2//已安装
    const val STATE_INSTALL_FAIL = STATE_INSTALL_CREATE + CNetKAppTaskState.STATE_TASK_FAIL//49//STATE_INSTALLED = 2//已安装
    const val STATE_INSTALL_CANCEL = STATE_INSTALL_CREATE + CNetKAppTaskState.STATE_TASK_CANCEL//49//STATE_INSTALLED = 2//安装取消

    @JvmStatic
    fun isTaskInstall(state: Int): Boolean =
        state in STATE_INSTALL_CREATE..STATE_INSTALL_FAIL

    @JvmStatic
    fun isInstalled(state: Int): Boolean =
        state == STATE_INSTALL_SUCCESS

    @JvmStatic
    fun canInstall(state: Int): Boolean =
        state == STATE_VERIFY_SUCCESS || state == STATE_UNZIP_SUCCESS || state == STATE_INSTALLING

    //////////////////////////////////////////////////////////////
    //卸载
    private const val STATE_UNINSTALL_CREATE = 50
    const val STATE_UNINSTALL_SUCCESS = STATE_UNINSTALL_CREATE + CNetKAppTaskState.STATE_TASK_SUCCESS//58

    /*    //////////////////////////////////////////////////////////////
        //更新
        const val STATE_UPDATE_CREATE = 50//需要更新
        const val STATE_UPDATEING = 51//STATE_NEED_UPDATE = 17//更新中
        const val STATE_UPDATE_SUCCESS = 58
        const val STATE_UPDATE_FAIL = 59*/

}