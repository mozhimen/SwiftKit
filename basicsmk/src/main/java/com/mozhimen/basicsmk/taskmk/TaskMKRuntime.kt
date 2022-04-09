package com.mozhimen.basicsmk.taskmk

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basicsmk.BuildConfig
import com.mozhimen.basicsmk.executormk.ExecutorMK
import com.mozhimen.basicsmk.taskmk.commons.TaskMKRuntimeListener
import com.mozhimen.basicsmk.taskmk.helpers.TaskMKComparator
import com.mozhimen.basicsmk.taskmk.mos.TaskMKRuntimeInfo
import com.mozhimen.basicsmk.utilmk.UtilMKMHandler
import com.mozhimen.basicsmk.utilmk.UtilMKUIHandler
import java.util.*
import kotlin.collections.HashMap

/**
 * @ClassName TaskMKRuntime
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 15:34
 * @Version 1.0
 */
/*
* TasKMK运行时的任务调度器
* 1.根据task的属性以不同的策略(线程，同步，延迟)调度任务
* 2.校验依赖树中是否存在环形依赖
* 3.校验依赖树中是否存在taskId相同的任务
* 4.统计所有task的运行时信息(线程，状态，开始执行时间，电时的4.是否是阻塞任务)，用于log输出
 */
internal object TaskMKRuntime {
    //通过addBlockTask (String name)指定启动阶段需要阻完成的任务，只有当blockTasksId当中的任务都执行完了
    //才会释放application的阻塞，才会拉起launchActivity
    val blockTasksId: MutableList<String> = mutableListOf()

    //如果blockTasksId集合中的任务还没有完成，那么在主线程中执行的任务会被添加到waitingTasks集合里面去
    //目的是为了优先保证阻塞任务的优先完成，尽可能早的拉起launchActivity
    val waitingTasks: MutableList<TaskMK> = mutableListOf()

    //记录下启动阶段所有任务的运行时信息key是taskId
    val taskMKRuntimeInfos: MutableMap<String, TaskMKRuntimeInfo> = HashMap()

    val taskMKComparator = kotlin.Comparator<TaskMK> { task1, task2 ->
        TaskMKComparator.compareTaskMK(task1, task2)
    }

    @JvmStatic
    fun addBlockTask(id: String) {
        if (!TextUtils.isEmpty(id)) {
            blockTasksId.add(id)
        }
    }

    @JvmStatic
    fun addBlockTasks(vararg ids: String) {
        if (ids.isNotEmpty()) {
            for (id in ids) {
                addBlockTask(id)
            }
        }
    }

    @JvmStatic
    fun removeBlockTask(id: String) {
        blockTasksId.remove(id)
    }

    @JvmStatic
    fun hasBlockTasks(): Boolean {
        return blockTasksId.iterator().hasNext()
    }

    @JvmStatic
    fun setThreadName(taskMK: TaskMK, threadName: String?) {
        val taskMKRuntimeInfo = getTaskMKRuntimeInfo(taskMK.id)
        taskMKRuntimeInfo?.threadName = threadName
    }

    @JvmStatic
    fun setStateInfo(taskMK: TaskMK) {
        val taskMKRuntimeInfo = getTaskMKRuntimeInfo(taskMK.id)
        taskMKRuntimeInfo?.setStateTime(taskMK.state, System.currentTimeMillis())
    }

    @JvmStatic
    fun getTaskMKRuntimeInfo(id: String): TaskMKRuntimeInfo? {
        return taskMKRuntimeInfos[id]
    }

    @JvmStatic
    fun hasWaitingTasks(): Boolean {
        return waitingTasks.iterator().hasNext()
    }

    fun runWaitingTasks() {
        if (hasWaitingTasks()) {
            if (waitingTasks.size > 1) {
                Collections.sort(waitingTasks, taskMKComparator)
            }
            if (hasBlockTasks()) {
                val head = waitingTasks.removeAt(0)
                head.run()
            } else {
                for (waitingTask in waitingTasks) {
                    UtilMKMHandler.instance.postDelay(waitingTask.delayMills, waitingTask)
                }
                waitingTasks.clear()
            }
        }
    }

    //根据task 的属性以不同的策略 调度 task
    @JvmStatic
    fun executeTask(taskMK: TaskMK) {
        if (taskMK.isAsyncTask) {
            ExecutorMK.execute(runnable = taskMK)
        } else {
            //else里面的都是在主线程执行的
            //延迟任务，但是如果这个延迟任务它存在着后置任务A(延迟任务)-->B--->C (Block task)
            if (taskMK.delayMills > 0 && !hasBlockBehindTask(taskMK)) {
                UtilMKMHandler.instance.postDelay(taskMK.delayMills, taskMK)
                return
            }
            if (!hasBlockTasks()) {
                taskMK.run()
            } else {
                addWaitingTask(taskMK)
            }
        }
    }

    //把一个主线程上需要执行的任务，但又不影响launchActivity的启动，添加到等待队列
    private fun addWaitingTask(taskMK: TaskMK) {
        if (!waitingTasks.contains(taskMK)) {
            waitingTasks.add(taskMK)
        }
    }

    //检测- -个延迟任务是否存在着后置的阻塞任务(就是等他们都共行完了,才会释放application的阻塞，才会拉起launchActivity)
    private fun hasBlockBehindTask(taskMK: TaskMK): Boolean {
        if (taskMK is TaskMKGroup.CriticalTaskMK) {
            return false
        }

        val behindTasks = taskMK.behindTasks
        for (behindTask in behindTasks) {
            //需要判断-个task是不是阻塞任务，blockTaskIds--
            val behindTaskInfo = getTaskMKRuntimeInfo(behindTask.id)
            return if (behindTaskInfo != null && behindTaskInfo.isBlockTask) {
                true
            } else {
                hasBlockBehindTask(behindTask)
            }
        }
        return false
    }

    //校验依赖树中是否存在环形依赖校验，依赖树中是否存在taskId相同的任务 初始化task 对应taskRuntimeInfo
    //遍历依赖树完成启动前的检查和初始化
    @JvmStatic
    fun traversalDependencyTreeAndInit(taskMK: TaskMK) {
        val traversalVisitor = linkedSetOf<TaskMK>()
        traversalVisitor.add(taskMK)
        innerTraversalDependencyTreeAndInit(taskMK, traversalVisitor)

        val iterator = blockTasksId.iterator()
        while (iterator.hasNext()) {
            val taskId = iterator.next()
            //检查这个阻塞任务是否存在依赖树中
            if (!taskMKRuntimeInfos.containsKey(taskId)) {
                throw java.lang.RuntimeException("block task ${taskMK.id} not in dependency tree.")
            } else {
                traversalDependencyPriority(getTaskMKRuntimeInfo(taskId)?.taskMK)
            }
        }
    }

    //那么此时 我们只需要向上遍历，提升 task 前置依赖任务的优先级即可
    private fun traversalDependencyPriority(taskMK: TaskMK?) {
        if (taskMK == null) return
        taskMK.priority = Int.MAX_VALUE
        for (dependTask in taskMK.dependTasks) {
            traversalDependencyPriority(dependTask)
        }
    }

    private fun innerTraversalDependencyTreeAndInit(taskMK: TaskMK, traversalVisitor: LinkedHashSet<TaskMK>) {
        ////物始化taskMKRuntimeInfo 并校验是否存在相同的任务名称taskMK.Id
        var taskMKRuntimeInfo = getTaskMKRuntimeInfo(taskMK.id)
        if (taskMKRuntimeInfo == null) {
            taskMKRuntimeInfo = TaskMKRuntimeInfo(taskMK)
            if (blockTasksId.contains(taskMK.id)) {
                taskMKRuntimeInfo.isBlockTask = true
            }
            taskMKRuntimeInfos[taskMK.id] = taskMKRuntimeInfo
        } else {
            if (!taskMKRuntimeInfo.isSameTask(taskMK)) {
                throw RuntimeException("not allow to contain the same id ${taskMK.id}")
            }
        }

        //校验环形依赖
        for (behindTask in taskMK.behindTasks) {
            if (!traversalVisitor.contains(behindTask)) {
                traversalVisitor.add(behindTask)
            } else {
                throw RuntimeException("not allow loopback dependency, task id= ${taskMK.id}")
            }

            //start--> task1--> task2--> task3--> task4--> task5--> end
            //对task3后面的依赖任务路径上的task做环形依赖检查初始化runtimeInfo信息
            if (BuildConfig.DEBUG && behindTask.behindTasks.isEmpty()) {
                //behindTask =end
                val iterator = traversalVisitor.iterator()
                val builder: StringBuilder = StringBuilder()
                while (iterator.hasNext()) {
                    builder.append(iterator.next().id)
                    builder.append("--> ")
                }
                Log.e(TaskMKRuntimeListener.TAG, builder.toString())
            }
            innerTraversalDependencyTreeAndInit(behindTask, traversalVisitor)
            traversalVisitor.remove(behindTask)
        }
    }
}
