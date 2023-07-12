package com.mozhimen.basick.taskk.chain.helpers

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.elemk.android.os.bases.BaseWeakRefMainHandler
import com.mozhimen.basick.taskk.chain.bases.BaseChainTask
import com.mozhimen.basick.taskk.chain.commons.IChainRuntime
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.taskk.chain.mos.MChainTaskRuntimeInfo
import com.mozhimen.basick.taskk.chain.temps.CriticalChainTask
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.os.applyPostDelayed
import java.util.*

/**
 * @ClassName TaskKRuntime
 * @Description
 * TasKK运行时的任务调度器
 * 1.根据task的属性以不同的策略(线程，同步，延迟)调度任务
 * 2.校验依赖树中是否存在环形依赖
 * 3.校验依赖树中是否存在taskId相同的任务
 * 4.统计所有task的运行时信息(线程，状态，开始执行时间，电时的4.是否是阻塞任务)，用于log输出
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 15:34
 * @Version 1.0
 */
internal object ChainRuntime : BaseUtilK(), IChainRuntime {

    /**
     * 通过addBlockTask (String name)指定启动阶段需要阻完成的任务，只有当blockTasksId当中的任务都执行完了
     * 才会释放application的阻塞，才会拉起launchActivity
     */
    private val _blockTasksId: MutableList<String> = mutableListOf()

    /**
     * 如果blockTasksId集合中的任务还没有完成，那么在主线程中执行的任务会被添加到waitingTasks集合里面去
     * 目的是为了优先保证阻塞任务的优先完成，尽可能早的拉起launchActivity
     */
    private val _waitingTasks: MutableList<BaseChainTask> = mutableListOf()

    /**
     * 记录下启动阶段所有任务的运行时信息key是taskId
     */
    private val _chainkTaskRuntimeInfos: MutableMap<String, MChainTaskRuntimeInfo> = HashMap()

    private val _chainkTaskComparator by lazy { ChainTaskComparator() }

    //////////////////////////////////////////////////////////////////////////////////////////////

    override fun addBlockTask(id: String) {
        if (!TextUtils.isEmpty(id)) {
            _blockTasksId.add(id)
        }
    }

    override fun addBlockTasks(vararg ids: String) {
        if (ids.isNotEmpty()) {
            for (id in ids) {
                addBlockTask(id)
            }
        }
    }

    override fun removeBlockTask(id: String) {
        _blockTasksId.remove(id)
    }

    override fun hasBlockTasks(): Boolean {
        return _blockTasksId.iterator().hasNext()
    }

    override fun setThreadName(task: BaseChainTask, threadName: String) {
        val taskKRuntimeInfo = getTaskRuntimeInfo(task.id)
        taskKRuntimeInfo?.threadName = threadName
    }

    override fun setTaskStateInfo(task: BaseChainTask) {
        val taskKRuntimeInfo = getTaskRuntimeInfo(task.id)
        taskKRuntimeInfo?.setStateTime(task.state, System.currentTimeMillis())
    }

    override fun getTaskRuntimeInfo(id: String): MChainTaskRuntimeInfo? {
        return _chainkTaskRuntimeInfos[id]
    }

    override fun hasWaitingTasks(): Boolean {
        return _waitingTasks.iterator().hasNext()
    }

    override fun runWaitingTasks() {
        if (hasWaitingTasks()) {
            if (_waitingTasks.size > 1) {
                Collections.sort(_waitingTasks, _chainkTaskComparator)
            }
            if (hasBlockTasks()) {
                val head = _waitingTasks.removeAt(0)
                head.run()
            } else {
                for (task in _waitingTasks) {
                    BaseWeakRefMainHandler(this).applyPostDelayed(task.delayMills, task)
                }
                _waitingTasks.clear()
            }
        }
    }

    override fun executeTask(task: BaseChainTask) {
        if (task.isAsyncTask) {
            TaskKExecutor.execute(TAG, runnable = task)
        } else {
            //else里面的都是在主线程执行的
            //延迟任务，但是如果这个延迟任务它存在着后置任务A(延迟任务)-->B--->C (Block task)
            if (task.delayMills > 0 && !hasBlockBehindTask(task)) {
                BaseWeakRefMainHandler(this).applyPostDelayed(task.delayMills, task)
                return
            }
            if (!hasBlockTasks()) {
                task.run()
            } else {
                addWaitingTask(task)
            }
        }
    }

    override fun traversalDependencyTreeAndInit(task: BaseChainTask) {
        val traversalVisitor = linkedSetOf<BaseChainTask>()
        traversalVisitor.add(task)
        innerTraversalDependencyTreeAndInit(task, traversalVisitor)

        val iterator = _blockTasksId.iterator()
        while (iterator.hasNext()) {
            val taskId = iterator.next()
            //检查这个阻塞任务是否存在依赖树中
            if (!_chainkTaskRuntimeInfos.containsKey(taskId)) {
                throw java.lang.RuntimeException("block task ${task.id} not in dependency tree.")
            } else {
                traversalDependencyPriority(getTaskRuntimeInfo(taskId)?.chainKTask)
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    //把一个主线程上需要执行的任务，但又不影响launchActivity的启动，添加到等待队列
    private fun addWaitingTask(task: BaseChainTask) {
        if (!_waitingTasks.contains(task)) _waitingTasks.add(task)
    }

    //检测- -个延迟任务是否存在着后置的阻塞任务(就是等他们都共行完了,才会释放application的阻塞，才会拉起launchActivity)
    private fun hasBlockBehindTask(task: BaseChainTask): Boolean {
        if (task is CriticalChainTask) return false
        val behindTasks = task.behindTasks
        for (behindTask in behindTasks) {
            //需要判断-个task是不是阻塞任务，blockTaskIds--
            val behindTaskInfo = getTaskRuntimeInfo(behindTask.id)
            return if (behindTaskInfo != null && behindTaskInfo.isBlockTask) {
                true
            } else {
                hasBlockBehindTask(behindTask)
            }
        }
        return false
    }

    //那么此时 我们只需要向上遍历，提升 task 前置依赖任务的优先级即可
    private fun traversalDependencyPriority(task: BaseChainTask?) {
        if (task == null) return
        task.priority = Int.MAX_VALUE
        for (dependTask in task.dependTasks) {
            traversalDependencyPriority(dependTask)
        }
    }

    private fun innerTraversalDependencyTreeAndInit(task: BaseChainTask, traversalVisitor: LinkedHashSet<BaseChainTask>) {
        ////物始化taskKRuntimeInfo 并校验是否存在相同的任务名称taskK.Id
        var taskKRuntimeInfo = getTaskRuntimeInfo(task.id)
        if (taskKRuntimeInfo == null) {
            taskKRuntimeInfo = MChainTaskRuntimeInfo(task)
            if (_blockTasksId.contains(task.id)) {
                taskKRuntimeInfo.isBlockTask = true
            }
            _chainkTaskRuntimeInfos[task.id] = taskKRuntimeInfo
        } else {
            if (!taskKRuntimeInfo.isSameTask(task)) {
                throw RuntimeException("not allow to contain the same id ${task.id}")
            }
        }

        //校验环形依赖
        for (behindTask in task.behindTasks) {
            if (!traversalVisitor.contains(behindTask)) {
                traversalVisitor.add(behindTask)
            } else {
                throw RuntimeException("not allow loopback dependency, task id= ${task.id}")
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
                Log.e(TAG, "innerTraversalDependencyTreeAndInit builder $builder")
            }
            innerTraversalDependencyTreeAndInit(behindTask, traversalVisitor)
            traversalVisitor.remove(behindTask)
        }
    }
}
