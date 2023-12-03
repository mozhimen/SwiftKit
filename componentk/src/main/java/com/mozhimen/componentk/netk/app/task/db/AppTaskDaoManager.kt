package com.mozhimen.componentk.netk.app.task.db

import androidx.annotation.WorkerThread
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.netk.app.task.cons.CNetKAppTaskState
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName AppTaskDaoManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 16:08
 * @Version 1.0
 */
@OptInApiInit_InApplication
object AppTaskDaoManager : IUtilK {
    private val _tasks = ConcurrentHashMap<String, AppTask>()

    fun init() {
        TaskKExecutor.execute(TAG + "init") {
            AppTaskDbManager.appTaskDao.getAll().forEach {
                _tasks[it.taskId] = it
            }
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

    @JvmStatic
    fun getByTaskId_PackageName(taskId: String, packageName: String): AppTask? {
        val appTask: AppTask = _tasks[taskId] ?: return null
        if (appTask.apkPackageName == packageName)
            return appTask
        return null
    }

    /**
     * 根据应用id查询下载对象
     * @param taskId 应用Id
     * @return null 表示没有查询到
     */
    @JvmStatic
    fun getByTaskId(taskId: String): AppTask? {
        return _tasks[taskId]
    }

    @JvmStatic
    fun getByDownloadId(downloadId: Int): AppTask? {
        return _tasks.filter { it.value.downloadId == downloadId }.map { it.value }.getOrNull(0)
    }

    /**
     * 根据应用包名查询下载对象
     * @param packageName 应用Id
     * @return null 表示没有查询到
     */
    @JvmStatic
    fun getByApkPackageName(packageName: String): AppTask? {
        return _tasks.filter { it.value.apkPackageName == packageName }.map { it.value }.getOrNull(0)
    }

    @JvmStatic
    fun getAllAtTaskDownloadOrWaitOrPause(): List<AppTask> {
        return _tasks.filter { it.value.isTaskDownload() || it.value.taskState == CNetKAppTaskState.STATE_TASK_WAIT || it.value.taskState == CNetKAppTaskState.STATE_TASK_PAUSE }
            .map { it.value }
    }

    @JvmStatic
    fun getAppTasksIsProcess(): List<AppTask> {
        return _tasks.filter { it.value.isTaskProcess() }.map { it.value }
    }

    @JvmStatic
    fun getAppTasksIsInstalled(): List<AppTask> {
        return _tasks.filter { it.value.isInstalled() }.map { it.value }
    }


    /**
     * 通过保存名称获取下载信息
     * @param apkName 保存名称
     * @return 下载信息
     */
    fun getByApkName(apkName: String): AppTask? {
        return _tasks.filter { it.value.apkName == apkName }.map { it.value }.getOrNull(0)
    }

    //////////////////////////////////////////////////////////

    fun hasDownloading(): Boolean {
        return _tasks.filter { it.value.isTaskDownload() }.isNotEmpty()
    }

    fun hasVerifying(): Boolean {
        return _tasks.filter { it.value.isTaskVerify() }.isNotEmpty()
    }

    fun hasUnziping(): Boolean {
        return _tasks.filter { it.value.isTaskUnzip() }.isNotEmpty()
    }

    fun hasInstalling(): Boolean {
        return _tasks.filter { it.value.isTaskInstall() }.isNotEmpty()
    }


    //////////////////////////////////////////////////////////

    //废弃
//    fun removeAppTaskForDatabase(appTask: AppTask) {
//        if (appTask.apkPackageName.isEmpty()) return
//        val appTask1 = getByApkPackageName(appTask.apkPackageName) ?: return//从本地数据库中查询出下载信息//如果查询不到，就不处理
//        //if (appTask1.apkIsInstalled)//删除数据库中的其他已安装的数据，相同包名的只保留一条已安装的数据
//        delete(appTask1)
//    }

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
            appTask.forEach {
                if (_tasks[it.taskId] != null) {
                    AppTaskDbManager.appTaskDao.update(it)
                } else {
                    _tasks[it.taskId] = it
                    AppTaskDbManager.appTaskDao.addAll(it)
                }
            }
        }
    }

    /**
     * 更新数据
     */
    fun update(appTask: AppTask) {
        TaskKExecutor.execute(TAG + "update") {
            updateOnBack(appTask)
        }
    }

    /**
     * 删除数据
     */
    fun delete(appTask: AppTask) {
        TaskKExecutor.execute(TAG + "delete") {
            deleteOnBack(appTask)
        }
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
    @WorkerThread
    private fun deleteOnBack(appTask: AppTask) {
        if (_tasks.contains(appTask.taskId))
            _tasks.remove(appTask.taskId)
        AppTaskDbManager.appTaskDao.delete(appTask)
    }

    /**
     * 同步更新，防止多个线程同时更新，出现问题
     */
    @Synchronized
    @WorkerThread
    private fun updateOnBack(appTask: AppTask) {
        appTask.taskUpdateTime = System.currentTimeMillis()
        try {
            if (_tasks.containsKey(appTask.taskId)) {
                AppTaskDbManager.appTaskDao.update(appTask)//将本条数据插入到数据库
            } else {
                AppTaskDbManager.appTaskDao.addAll(appTask)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _tasks[appTask.taskId] = appTask
    }


}