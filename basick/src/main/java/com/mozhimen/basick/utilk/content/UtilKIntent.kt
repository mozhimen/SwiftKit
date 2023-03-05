package com.mozhimen.basick.utilk.content

import android.content.Intent
import android.os.Build

/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:03
 * @Version 1.0
 */
object UtilKIntent {
    @JvmStatic
    fun getInstallApp(filePathWithName: String): Intent? {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断安卓系统是否大于7.0  大于7.0使用以下方法
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //增加读写权限//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(UtilKUri.filePathString2Uri(filePathWithName) ?: return null, "application/vnd.android.package-archive")
        return intent
    }
}