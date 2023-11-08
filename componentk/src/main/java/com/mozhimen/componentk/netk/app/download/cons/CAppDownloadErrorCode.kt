package com.mozhimen.componentk.netk.app.download.cons

/**
 * @ClassName CAppDownloadErrorCode
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 13:58
 * @Version 1.0
 */
object CAppDownloadErrorCode {
    const val CODE_DOWNLOAD_NEED_MEMORY = 0//"存储空间不足，请清理内存后再试"
    const val CODE_INSTALL_NEED_MEMORY = 1//"存储空间不足，可能会导致安装失败,是否继续下载？"
    const val CODE_DOWNLOAD_PATH_NOT_EXIST =2//"下载路径不存在"
    const val CODE_UNZIPING = 11//正在解压, 无法删除
    const val CODE_CANT_FIND_TASK = 12//"未找到下载任务！"
}