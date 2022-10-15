package com.mozhimen.abilityk.hotfixk

import android.os.Environment
import com.mozhimen.abilityk.hotfixk.commons.IHotFixKListener
import com.mozhimen.basick.executork.ExecutorK
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKGlobal
import java.io.File
import java.lang.reflect.InvocationTargetException

/**
 * @ClassName HotFixMgr
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/20 17:42
 * @Version 1.0
 */
class HotFixMgr {

    companion object {
        private const val TAG = "HotFixKMgr>>>>>"
        private const val HOTFIXK_DIR = "hotfixk"

        @JvmStatic
        val instance = HotFixKMgrHolder.holder
    }

    private val _context = UtilKGlobal.instance.getApp()!!
    private val _hotfixPath = getHotFixFilesDir().absolutePath
    private var _hotFixKListener: IHotFixKListener? = null

    @Volatile
    private var _fixNum = 0

    fun init(sourcePath: String = Environment.getExternalStorageDirectory().absolutePath, hotFixKListener: IHotFixKListener? = null) {
        this._hotFixKListener = hotFixKListener
        ExecutorK.execute(TAG, 0) {
            //先将文件搬运至cacheDir目录下
            moveHotFixFiles2InnerSpace(sourcePath)
            //将所有文件进行加载更新
            startHotFix()
        }
    }

    fun startHotFix() {
        val hotFixKFiles: Array<File> = getHotFixFiles()
        val totalSize = hotFixKFiles.size
        hotFixKFiles.forEach {
            fix(it)
        }
        LogK.dt(TAG, "startHotFix: total files $totalSize fixed files $_fixNum")
        _hotFixKListener?.onFixFinished(totalSize, _fixNum)
    }

    /**
     * 将所有前缀为hotfixk的dex文件移动到app目录下的cacheDir下
     * @param sourcePath String
     */
    fun moveHotFixFiles2InnerSpace(sourcePath: String) {
        val sourceFiles: Array<File> = getAllExternalHotFixFiles(sourcePath)
        sourceFiles.forEach {
            val destFile = File(_hotfixPath, it.name)
            UtilKFile.copyFile(it, destFile)
            UtilKFile.deleteFile(it)
        }
        LogK.dt(TAG, "moveHotFixFiles2InnerSpace: move ${sourceFiles.size} files to cache dir")
        _hotFixKListener?.onMoveHotFixFilesFinished(sourceFiles.size)
    }

    /**
     * 需要在application中设置: android:requestLegacyExternalStorage="true"
     * @param dexFile File
     */
    fun fix(dexFile: File) {
        try {
            HotFixK.fix(_context, dexFile.absolutePath)
        } catch (e: IllegalAccessException) {
            LogK.et(TAG, "startFix: IllegalAccessException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: NoSuchFieldException) {
            LogK.et(TAG, "startFix: NoSuchFieldException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: InvocationTargetException) {
            LogK.et(TAG, "startFix: InvocationTargetException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: NoSuchMethodException) {
            LogK.et(TAG, "startFix: NoSuchMethodException ${e.message}")
            e.printStackTrace()
            return
        } catch (e: Throwable) {
            LogK.et(TAG, "startFix: Throwable ${e.message}")
            e.printStackTrace()
            return
        }
        _fixNum++
    }

    /**
     * 获取到HotFixK文件的存储路径
     * @return File
     */
    fun getHotFixFilesDir(): File {
        val hotFixFolder = File(_context.cacheDir, HOTFIXK_DIR)
        if (!hotFixFolder.exists()) {
            hotFixFolder.mkdirs()
        }
        return hotFixFolder
    }

    /**
     * 获取到存储路径下的所有文件
     * @return Array<File>
     */
    fun getHotFixFiles(): Array<File> {
        return File(_hotfixPath).listFiles() ?: emptyArray()
    }

    /**
     * 清除所有热修复dex文件
     */
    fun clearHotFixFiles() {
        UtilKFile.deleteFolder(_hotfixPath)
    }

    private fun getAllExternalHotFixFiles(sourcePath: String): Array<File> {
        val sourceFiles: Array<File> = File(sourcePath).listFiles() ?: emptyArray()
        val dexFiles = ArrayList<File>()
        sourceFiles.forEach {
            if (!UtilKFile.isFolder(it) && it.extension == "dex" && it.name.startsWith("hotfixk"))
                dexFiles.add(it)
        }
        return dexFiles.toTypedArray()
    }

    private object HotFixKMgrHolder {
        val holder = HotFixMgr()
    }
}