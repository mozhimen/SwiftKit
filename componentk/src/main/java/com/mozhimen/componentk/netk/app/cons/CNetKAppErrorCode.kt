package com.mozhimen.componentk.netk.app.cons

/**
 * @ClassName CAppDownloadErrorCode
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 13:58
 * @Version 1.0
 */
internal fun Int.intAppErrorCode2strAppError(): String =
    CNetKAppErrorCode.intAppErrorCode2strAppError(this)

object CNetKAppErrorCode {
    const val CODE_TASK_NEED_MEMORY_APK = 0//"存储空间不足，请清理内存后再试"
    const val CODE_TASK_NEED_MEMORY_NPK = 1//"存储空间不足，可能会导致安装失败,是否继续下载？"
    const val CODE_TASK_CANCEL_FAIL_ON_UNZIPING = 2//正在解压, 无法删除
    const val CODE_TASK_HAS_INSTALL = 3//已经安装
    const val CODE_DOWNLOAD_PATH_NOT_EXIST = 10//"下载路径不存在"
    const val CODE_DOWNLOAD_CANT_FIND_TASK = 11//"未找到下载任务！"
    const val CODE_DOWNLOAD_SERVER_CANCELED = 12//
    const val CODE_DOWNLOAD_ENOUGH = 13//
    const val CODE_VERIFY_DIR_NULL = 20
    const val CODE_VERIFY_FILE_NOT_EXIST = 21
    const val CODE_VERIFY_MD5_FAIL = 22
    const val CODE_VERIFY_FORMAT_INVALID = 23
    const val CODE_UNZIP_DIR_NULL = 30
    const val CODE_UNZIP_FAIL = 31
    const val CODE_INSTALL_HAST_VERIFY_OR_UNZIP = 32

    @JvmStatic
    fun intAppErrorCode2strAppError(code: Int): String =
        when (code) {
            CODE_TASK_NEED_MEMORY_APK -> "APK安装所需空间不足"
            CODE_TASK_NEED_MEMORY_NPK -> "NPK安装所需空间不足"
            CODE_TASK_CANCEL_FAIL_ON_UNZIPING -> "在解压时任务取消失败"
            CODE_TASK_HAS_INSTALL -> "APK已经安装"
            CODE_DOWNLOAD_PATH_NOT_EXIST -> "下载路径不存在"
            CODE_DOWNLOAD_CANT_FIND_TASK -> "下载任务丢失"
            CODE_DOWNLOAD_SERVER_CANCELED -> "下载服务取消"
            CODE_DOWNLOAD_ENOUGH -> "下载队列已满, 请稍候再试吧"
            CODE_VERIFY_DIR_NULL -> "验证路径为空"
            CODE_VERIFY_FILE_NOT_EXIST -> "验证文件不存在"
            CODE_VERIFY_MD5_FAIL -> "验证MD5失败"
            CODE_VERIFY_FORMAT_INVALID -> "APK验证格式非法"
            CODE_UNZIP_DIR_NULL -> "解压路径为空"
            CODE_UNZIP_FAIL -> "解压失败"
            CODE_INSTALL_HAST_VERIFY_OR_UNZIP -> "安装文件未解压或验证"
            else -> "未知错误"
        }
}