package com.mozhimen.componentk.netk.file.helpers

import okhttp3.Response


/**
 * @ClassName NetKFileDownloadUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 18:43
 * @Version 1.0
 */
object NetKFileDownloadUtil {
    /**
     * 文件最后修改时间
     * @param response Response
     * @return String?
     */
    @JvmStatic
    fun getLastModify(response: Response): String? {
        return response.headers["Last-Modified"]
    }
}