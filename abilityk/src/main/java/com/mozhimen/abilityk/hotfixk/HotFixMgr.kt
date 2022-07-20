package com.mozhimen.abilityk.hotfixk

import android.app.Application
import android.content.Context
import android.os.Environment
import com.mozhimen.basick.utilk.UtilKFile
import java.io.File
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName HotFixMgr
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 17:42
 * @Version 1.0
 */
class HotFixMgr {


    fun init(context: Context) {
        val sourceFile = File(Environment.getExternalStorageDirectory(), "patch.dex")
        val destFileFile = File(context.applicationInfo.dataDir, "patch.dex")
        UtilKFile.copyFile(sourceFile, destFileFile)

        try {
            HotFixK.fix(context, destFileFile.absolutePath)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
    }
}