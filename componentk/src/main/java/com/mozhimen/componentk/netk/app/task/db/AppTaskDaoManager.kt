package com.mozhimen.componentk.netk.app.task.db

import android.database.sqlite.SQLiteDatabaseLockedException
import androidx.annotation.WorkerThread
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState

/**
 * @ClassName AppTaskDaoManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:08
 * @Version 1.0
 */
@OptInApiInit_InApplication
object AppTaskDaoManager : IUtilK {
    private val _downloadTasks = mutableListOf<AppTask>()

    fun init() {
        TaskKExecutor.execute(TAG + "init") {
            _downloadTasks.addAll(AppTaskDbManager.appTaskDao.getAll())
        }
    }

    //////////////////////////////////////////////////////////

//    fun getAllDownloading(countDownLatch: CountDownLatch, list: MutableList<AppTask>) {
//        TaskKExecutor.execute(TAG + "getAllDownloading") {
//            val queryAllDownload = DatabaseManager.appDownloadParamDao.getAllDownloading()
//            list.addAll(queryAllDownload)
//            countDownLatch.countDown()
//        }
//    }

    /**
     * 根据应用id查询下载对象
     * @param taskId 应用Id
     * @return null 表示没有查询到
     */
    fun getByTaskId(taskId: String): AppTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.taskId == taskId)
                return next
        }
        return null
    }

    /**
     * 根据应用包名查询下载对象
     * @param packageName 应用Id
     * @return null 表示没有查询到
     */
    fun getByApkPackageName(packageName: String): AppTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkPackageName == packageName)
                return next
        }
        return null
    }

    /**
     * 通过保存名称获取下载信息
     * @param apkName 保存名称
     * @return 下载信息
     */
    fun getByApkName(apkName: String): AppTask? {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkName == apkName) //游戏id，包名相同即判定为同一个游戏
                return next
        }
        return null
    }

    //////////////////////////////////////////////////////////

    fun hasDownloading(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isTaskDownload())
                return true
        }
        return false
    }

    fun hasVerifying(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isTaskVerify())
                return true
        }
        return false
    }

    fun hasUnziping(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isTaskUnzip())
                return true
        }
        return false
    }

    fun hasInstalling(): Boolean {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.isTaskInstall())
                return true
        }
        return false
    }

    //////////////////////////////////////////////////////////

    fun removeAppTaskForDatabase(appTask: AppTask) {
        if (appTask.apkPackageName.isEmpty()) return
        val appTask1 = getByApkPackageName(appTask.apkPackageName) ?: return//从本地数据库中查询出下载信息//如果查询不到，就不处理
        if (appTask1.apkIsInstalled)//删除数据库中的其他已安装的数据，相同包名的只保留一条已安装的数据
            delete(appTask1)
    }

    fun addAppTask2Database(appTask: AppTask) {
        val appTask1 = getByTaskId(appTask.taskId)//更新本地数据库中的数据
        if (appTask1 == null) {
            addAll(appTask)
        }
    }

    //////////////////////////////////////////////////////////

    private fun addAll(vararg appTask: AppTask) {
        TaskKExecutor.execute(TAG + "addAll") {
            appTask.forEach {
                it.taskState = CNetKAppTaskState.STATE_TASK_CREATE
                it.downloadProgress = 0
                it.taskUpdateTime = System.currentTimeMillis()
            }
            _downloadTasks.addAll(appTask.toList())
            AppTaskDbManager.appTaskDao.addAll(*appTask)
        }
    }

    /**
     * 更新数据
     */
    fun update(appTask: AppTask) {
        TaskKExecutor.execute(TAG + "update") {
            updateSync(appTask)
        }
    }

    /**
     * 在子线程更新数据
     */
    @WorkerThread
    fun updateOnBack(appTask: AppTask) {
        updateSync(appTask)
    }

    /**
     * 删除数据
     */
    fun delete(appTask: AppTask) {
        TaskKExecutor.execute(TAG + "delete") {
            deleteSync(appTask)
        }
    }

    /**
     * 在子线程删除数据
     */
    @WorkerThread
    fun deleteOnBack(appTask: AppTask) {
        deleteSync(appTask)
    }


    /*    fun queryAppTask(appBaseInfo: AppBaseInfo): AppTask? {
        val iterator = _appTasks.iterator()
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
     * 删除任务
     * @param appTask 需要删除的任务
     */
    @Synchronized
    private fun deleteSync(appTask: AppTask) {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.taskId == appTask.taskId) {
                iterator.remove()
                try {
                    AppTaskDbManager.appTaskDao.delete(appTask)
                } catch (e: SQLiteDatabaseLockedException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }

    /**
     * 同步更新，防止多个线程同时更新，出现问题
     */
    @Synchronized
    private fun updateSync(appTask: AppTask) {
        val iterator = _downloadTasks.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next.apkPackageName == appTask.apkPackageName) {
                iterator.remove()
                _downloadTasks.add(appTask.apply {
                    taskUpdateTime = System.currentTimeMillis()
                })
                try {
                    AppTaskDbManager.appTaskDao.update(appTask)//将本条数据插入到数据库
                } catch (e: SQLiteDatabaseLockedException) {
                    e.printStackTrace()
                }
                break
            }
        }
    }
}