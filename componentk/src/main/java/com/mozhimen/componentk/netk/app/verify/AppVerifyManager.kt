package com.mozhimen.componentk.netk.app.verify

import com.mozhimen.componentk.netk.app.task.db.AppTask

/**
 * @ClassName AppVerifyManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 17:01
 * @Version 1.0
 */
object AppVerifyManager {
    /**
     * 判断是否需要校验MD5值
     * 1、NPK不需要校验MD5值
     * 2、如果是使用站内地址下载，不用校验MD5值
     * 3、如果使用站外地址，且没有站内地址，且第一次校验失败，则第二次时不用校验
     */
    @JvmStatic
    fun isNeedVerify(appTask: AppTask): Boolean {
        if (appTask.apkName.endsWith(".npk"))
            return false
        if (appTask.downloadUrlCurrent == appTask.downloadUrl) {//如果是使用站内地址下载，不用校验MD5值
            return false
        }
        return appTask.apkVerifyNeed
    }
}