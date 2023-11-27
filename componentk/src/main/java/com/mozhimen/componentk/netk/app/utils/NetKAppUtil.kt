package com.mozhimen.componentk.netk.app.utils

import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState

/**
 * @ClassName NetKAppUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/16 15:46
 * @Version 1.0
 */
fun Int.intAppState2strAppState(): String =
    NetKAppUtil.intAppState2strAppState(this)

object NetKAppUtil {
    @JvmStatic
    fun intAppState2strAppState(state: Int): String =
        when (state) {
            CNetKAppState.STATE_DOWNLOAD_WAIT -> "下载等待"
            CNetKAppState.STATE_DOWNLOADING -> "下载中 "
            CNetKAppState.STATE_DOWNLOAD_PAUSE -> "下载暂停"
            CNetKAppState.STATE_DOWNLOAD_CANCEL -> "下载取消"
            CNetKAppState.STATE_DOWNLOAD_SUCCESS -> "下载成功"
            CNetKAppState.STATE_DOWNLOAD_FAIL -> "下载失败"

            CNetKAppState.STATE_VERIFYING -> "验证中 "
            CNetKAppState.STATE_VERIFY_SUCCESS -> "验证成功"
            CNetKAppState.STATE_VERIFY_FAIL -> "验证失败"

            CNetKAppState.STATE_UNZIPING -> "解压中 "
            CNetKAppState.STATE_UNZIP_SUCCESS -> "解压成功"
            CNetKAppState.STATE_UNZIP_FAIL -> "解压失败"

            CNetKAppState.STATE_INSTALLING -> "安装中 "
            CNetKAppState.STATE_INSTALL_SUCCESS -> "安装成功"
            CNetKAppState.STATE_INSTALL_FAIL -> "安装失败"

            CNetKAppState.STATE_UNINSTALL_SUCCESS -> "卸载成功"

            CNetKAppTaskState.STATE_TASK_CREATE -> "任务创建"
            CNetKAppTaskState.STATE_TASK_WAIT -> "任务等待"
            CNetKAppTaskState.STATE_TASK_PAUSE -> "任务暂停"
            CNetKAppTaskState.STATE_TASK_CANCEL -> "任务取消"
            CNetKAppTaskState.STATE_TASK_SUCCESS -> "任务成功"
            CNetKAppTaskState.STATE_TASK_FAIL -> "任务失败"
            else -> "任务中 "
        }
}