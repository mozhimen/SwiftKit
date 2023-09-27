package com.mozhimen.componentk.netk.file.download.commons

/**
 * @ClassName IDownloader
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/26 22:06
 * @Version 1.0
 */
interface IDownloader {
    /**
     * 具体的下载实现
     */
    fun download()
    /**
     * 在 Service 中开始下载
     */
    fun startDownload()
}