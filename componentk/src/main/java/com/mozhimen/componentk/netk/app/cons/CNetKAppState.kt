package com.mozhimen.componentk.netk.app.cons

/**
 * @ClassName CAppDownloadState
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 11:36
 * @Version 1.0
 */
object CNetKAppState {
    //任务
    const val STATE_TASK_CREATE = 0//STATE_NOT_INSTALLED = 0//未安装 处于未下载，
    const val STATE_TASK_WAIT = 1//STATE_PENDING = 3//等待中
    const val STATE_TASK_WAIT_CANCEL = 2//STATE_PENDING_CANCELED = 4//取消等待中
    const val STATE_TASK_CANCEL = 3//取消任务
    const val STATE_TASK_SUCCESS = 8//任务成功
    const val STATE_TASK_FAIL = 9//任务失败

    @JvmStatic
    fun isTasking(state: Int): Boolean =
        state in STATE_TASK_WAIT until STATE_TASK_SUCCESS

    //////////////////////////////////////////////////////////////
    //下载
    const val STATE_DOWNLOAD_CREATE = 10//STATE_DOWNLOADED = 5//未下载
    const val STATE_DOWNLOADING = 11//STATE_DOWNLOAD_IN_PROGRESS = 6//正在下载
    const val STATE_DOWNLOAD_PAUSE = 12//STATE_DOWNLOAD_PAUSED = 7//下载暂停
    const val STATE_DOWNLOAD_CANCEL = 13//下载取消
    const val STATE_DOWNLOAD_SUCCESS = 18//STATE_DOWNLOAD_COMPLETED = 8//下载完成
    const val STATE_DOWNLOAD_FAIL = 19//STATE_DOWNLOAD_FAILED = 10//下载失败

    @JvmStatic
    fun isDownloading(state: Int): Boolean =
        state in STATE_DOWNLOADING until STATE_DOWNLOAD_SUCCESS

    //////////////////////////////////////////////////////////////
    //校验
    const val STATE_VERIFY_CREATE = 20 //待校验
    const val STATE_VERIFYING = 21//STATE_CHECKING = 14//校验中
    const val STATE_VERIFY_SUCCESS = 28//STATE_CHECKING_SUCCESS = 15//校验成功
    const val STATE_VERIFY_FAIL = 29//STATE_CHECKING_FAILURE = 16//校验失败

    @JvmStatic
    fun isVerifying(state: Int): Boolean =
        state in STATE_VERIFYING until STATE_VERIFY_SUCCESS

    //////////////////////////////////////////////////////////////
    //解压
    const val STATE_UNZIP_CREATE = 30//待解压
    const val STATE_UNZIPING = 31//STATE_UNPACKING = 11//解压中
    const val STATE_UNZIP_SUCCESS = 38//STATE_UNPACKING_SUCCESSFUL = 12//解压成功
    const val STATE_UNZIP_FAIL = 39//STATE_UNPACKING_FAILED = 13//解压失败

    @JvmStatic
    fun isUnziping(state: Int): Boolean =
        state in STATE_UNZIPING until STATE_UNZIP_SUCCESS

    //////////////////////////////////////////////////////////////
    //安装
    const val STATE_INSTALL_CREATE = 40//待安装
    const val STATE_INSTALLING = 41//STATE_INSTALLING = 1//安装中
    const val STATE_INSTALL_SUCCESS = 48//STATE_INSTALLED = 2//已安装
    const val STATE_INSTALL_FAIL = 49//STATE_INSTALLED = 2//已安装

    @JvmStatic
    fun isInstalling(state: Int):Boolean =
        state in STATE_INSTALLING until STATE_INSTALL_SUCCESS

    //////////////////////////////////////////////////////////////
    //卸载
    const val STATE_UNINSTALL_CREATE = 50

    /*    //////////////////////////////////////////////////////////////
        //更新
        const val STATE_UPDATE_CREATE = 50//需要更新
        const val STATE_UPDATEING = 51//STATE_NEED_UPDATE = 17//更新中
        const val STATE_UPDATE_SUCCESS = 58
        const val STATE_UPDATE_FAIL = 59*/

}