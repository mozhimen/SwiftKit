package com.mozhimen.componentk.netk.app.download.db

import android.database.sqlite.SQLiteDatabaseLockedException
import androidx.annotation.WorkerThread
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName AppDownloadParamDaoManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:08
 * @Version 1.0
 */
@OptInApiInit_InApplication
object DaoManager : IUtilK {
    private val _downloadTasks = mutableListOf<AppDownloadTask>()

    fun init() {
        TaskKExecutor.execute(TAG + "init") {
            _downloadTasks.addAll(DatabaseManager.appDownloadParamDao.getAll())
        }
    }

    //////////////////////////////////////////////////////////

//    fun getAllDownloading(countDownLatch: CountDownLatch, list: MutableList<AppDownloadParam>) {
//        TaskKExecutor.execute(TAG + "getAllDownloading") {
//            val queryAllDownload = DatabaseManager.appDownloadParamDao.getAllDownloading()
//            list.addAll(queryAllDownload)
//            countDownLatch.countDown()
//        }
//    }

    /**
     * 根据应用id查询下载对象
     * @param downloadId 应用Id
     * @return null 表示没有查询到
     */
    fun getByDownloadId(downloadId: String): AppDownloadTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.taskId == downloadId)
                return next
        }
        return null
    }

    /**
     * 根据应用包名查询下载对象
     * @param apkPackageName 应用Id
     * @return null 表示没有查询到
     */
    fun getByApkPackageName(apkPackageName: String): AppDownloadTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkPackageName == apkPackageName)
                return next
        }
        return null
    }

    /**
     * 通过保存名称获取下载信息
     * @param apkSaveName 保存名称
     * @return 下载信息
     */
    fun getByApkSaveName(apkSaveName: String): AppDownloadTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkSaveName == apkSaveName) //游戏id，包名相同即判定为同一个游戏
                return next
        }
        return null
    }

    fun hasDownloading(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isDownloading())
                return true
        }
        return false
    }

    fun hasVerifying(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isVerifying())
                return true
        }
        return false
    }

    fun hasUnziping(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isUnziping())
                return true
        }
        return false
    }

    fun hasInstalling(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isInstalling())
                return true
        }
        return false
    }

    //////////////////////////////////////////////////////////

    fun addAll(vararg appDownloadTask: AppDownloadTask) {
        TaskKExecutor.execute(TAG + "addAll") {
            appDownloadTask.forEach {
                it.taskUpdateTime = System.currentTimeMillis()
            }
            _downloadTasks.addAll(appDownloadTask.toList())
            DatabaseManager.appDownloadParamDao.addAll(*appDownloadTask)
        }
    }

    /**
     * 更新数据
     */
    fun update(appDownloadTask: AppDownloadTask) {
        TaskKExecutor.execute(TAG + "update") {
            updateSync(appDownloadTask)
        }
    }

    /**
     * 在子线程更新数据
     * @param appDownloadTask 文件信息
     */
    @WorkerThread
    fun updateOnBack(appDownloadTask: AppDownloadTask) {
        updateSync(appDownloadTask)
    }

    /**
     * 删除任务
     * @param appDownloadTask 需要删除的任务
     */
    @WorkerThread
    fun deleteOnBack(appDownloadTask: AppDownloadTask) {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.taskId == appDownloadTask.taskId) {
                iterator.remove()
                break
            }
        }
        DatabaseManager.appDownloadParamDao.delete(appDownloadTask)
    }


    /*    fun queryAppDownloadParam(appBaseInfo: AppBaseInfo): AppDownloadParam? {
        val iterator = _appDownloadParams.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.packName == appBaseInfo.packageName) {
                //如果Id相同，说明是我们自己安装的,还需要判断下载地址是否相同，如果相同，则返回对象，否则需要重新创建一个
                if (next.appId == appBaseInfo.id) {
                    if (next.currentDownloadUrl == AppDownloadManager.getDownloadUrl(appBaseInfo)) {
                        return next
                    } else {
                        //从数据库中删除
                        iterator.remove()
                        AppDownloadManager.executorService.execute {
                            DatabaseManager.appDownloadParamDao.delete(next)
                        }
                        break
                    }
                }
            }
        }
        return null
    }*/

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 同步更新，防止多个线程同时更新，出现问题
     * idnex 无作用
     */
    @Synchronized
    private fun updateSync(appDownloadTask: AppDownloadTask) {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkPackageName == appDownloadTask.apkPackageName) {
                iterator.remove()
                _downloadTasks.add(appDownloadTask.apply {
                    taskUpdateTime = System.currentTimeMillis()
                })
                try {
                    DatabaseManager.appDownloadParamDao.update(appDownloadTask)//将本条数据插入到数据库
                } catch (e: SQLiteDatabaseLockedException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }
}