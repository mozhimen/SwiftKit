package com.mozhimen.basick.chaink.helpers

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.elemk.handler.bases.BaseWeakClazzMainHandler
import com.mozhimen.basick.chaink.mos.ChainKNode
import com.mozhimen.basick.chaink.mos.ChainKNodeGroup
import com.mozhimen.basick.taskk.executor.TaskKExecutor
import com.mozhimen.basick.chaink.mos.MNodeRuntimeInfo
import com.mozhimen.basick.utilk.os.thread.applyPostDelayed
import java.util.*

/**
 * @ClassName TaskKRuntime
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 15:34
 * @Version 1.0
 */
/*
* TasKK运行时的任务调度器
* 1.根据task的属性以不同的策略(线程，同步，延迟)调度任务
* 2.校验依赖树中是否存在环形依赖
* 3.校验依赖树中是否存在taskId相同的任务
* 4.统计所有task的运行时信息(线程，状态，开始执行时间，电时的4.是否是阻塞任务)，用于log输出
 */
internal object ChainKRuntime {
    private const val TAG = "TaskKRuntime>>>>>"

    //通过addBlockTask (String name)指定启动阶段需要阻完成的任务，只有当blockTasksId当中的任务都执行完了
    //才会释放application的阻塞，才会拉起launchActivity
    private val _blockTasksId: MutableList<String> = mutableListOf()

    //如果blockTasksId集合中的任务还没有完成，那么在主线程中执行的任务会被添加到waitingTasks集合里面去
    //目的是为了优先保证阻塞任务的优先完成，尽可能早的拉起launchActivity
    private val _waitingTasks: MutableList<ChainKNode> = mutableListOf()

    //记录下启动阶段所有任务的运行时信息key是taskId
    private val _mNodeRuntimeInfos: MutableMap<String, MNodeRuntimeInfo> = HashMap()

    val chainKNodeComparator = kotlin.Comparator<ChainKNode> { task1, task2 ->
        NodeComparator.compareNode(task1, task2)
    }

    @JvmStatic
    fun addBlockTask(id: String) {
        if (!TextUtils.isEmpty(id)) {
            _blockTasksId.add(id)
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
        _blockTasksId.remove(id)
    }

    @JvmStatic
    fun hasBlockTasks(): Boolean {
        return _blockTasksId.iterator().hasNext()
    }

    @JvmStatic
    fun setThreadName(node: ChainKNode, threadName: String?) {
        val taskKRuntimeInfo = getTaskKRuntimeInfo(node.id)
        taskKRuntimeInfo?.threadName = threadName
    }

    @JvmStatic
    fun setStateInfo(node: ChainKNode) {
        val taskKRuntimeInfo = getTaskKRuntimeInfo(node.id)
        taskKRuntimeInfo?.setStateTime(node.state, System.currentTimeMillis())
    }

    @JvmStatic
    fun getTaskKRuntimeInfo(id: String): MNodeRuntimeInfo? {
        return _mNodeRuntimeInfos[id]
    }

    @JvmStatic
    fun hasWaitingTasks(): Boolean {
        return _waitingTasks.iterator().hasNext()
    }

    fun runWaitingTasks() {
        if (hasWaitingTasks()) {
            if (_waitingTasks.size > 1) {
                Collections.sort(_waitingTasks, chainKNodeComparator)
            }
            if (hasBlockTasks()) {
                val head = _waitingTasks.removeAt(0)
                head.run()
            } else {
                for (waitingTask in _waitingTasks) {
                    BaseWeakClazzMainHandler(this).applyPostDelayed(waitingTask.delayMills, waitingTask)
                }
                _waitingTasks.clear()
            }
        }
    }

    //根据task 的属性以不同的策略 调度 task
    @JvmStatic
    fun executeTask(node: ChainKNode) {
        if (node.isAsyncTask) {
            TaskKExecutor.execute(TAG, runnable = node)
        } else {
            //else里面的都是在主线程执行的
            //延迟任务，但是如果这个延迟任务它存在着后置任务A(延迟任务)-->B--->C (Block task)
            if (node.delayMills > 0 && !hasBlockBehindTask(node)) {
                BaseWeakClazzMainHandler(this).applyPostDelayed(node.delayMills, node)
                return
            }
            if (!hasBlockTasks()) {
                node.run()
            } else {
                addWaitingTask(node)
            }
        }
    }

    //把一个主线程上需要执行的任务，但又不影响launchActivity的启动，添加到等待队列
    private fun addWaitingTask(node: ChainKNode) {
        if (!_waitingTasks.contains(node)) {
            _waitingTasks.add(node)
        }
    }

    //检测- -个延迟任务是否存在着后置的阻塞任务(就是等他们都共行完了,才会释放application的阻塞，才会拉起launchActivity)
    private fun hasBlockBehindTask(node: ChainKNode): Boolean {
        if (node is ChainKNodeGroup.CriticalChainKNode) {
            return false
        }

        val behindTasks = node.behindTasks
        for (behindTask in behindTasks) {
            //需要判断-个task是不是阻塞任务，blockTaskIds--
            val behindTaskInfo = getTaskKRuntimeInfo(behindTask.id)
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
    fun traversalDependencyTreeAndInit(node: ChainKNode) {
        val traversalVisitor = linkedSetOf<ChainKNode>()
        traversalVisitor.add(node)
        innerTraversalDependencyTreeAndInit(node, traversalVisitor)

        val iterator = _blockTasksId.iterator()
        while (iterator.hasNext()) {
            val taskId = iterator.next()
            //检查这个阻塞任务是否存在依赖树中
            if (!_mNodeRuntimeInfos.containsKey(taskId)) {
                throw java.lang.RuntimeException("block task ${node.id} not in dependency tree.")
            } else {
                traversalDependencyPriority(getTaskKRuntimeInfo(taskId)?.chainKNode)
            }
        }
    }

    //那么此时 我们只需要向上遍历，提升 task 前置依赖任务的优先级即可
    private fun traversalDependencyPriority(node: ChainKNode?) {
        if (node == null) return
        node.priority = Int.MAX_VALUE
        for (dependTask in node.dependTasks) {
            traversalDependencyPriority(dependTask)
        }
    }

    private fun innerTraversalDependencyTreeAndInit(node: ChainKNode, traversalVisitor: LinkedHashSet<ChainKNode>) {
        ////物始化taskKRuntimeInfo 并校验是否存在相同的任务名称taskK.Id
        var taskKRuntimeInfo = getTaskKRuntimeInfo(node.id)
        if (taskKRuntimeInfo == null) {
            taskKRuntimeInfo = MNodeRuntimeInfo(node)
            if (_blockTasksId.contains(node.id)) {
                taskKRuntimeInfo.isBlockTask = true
            }
            _mNodeRuntimeInfos[node.id] = taskKRuntimeInfo
        } else {
            if (!taskKRuntimeInfo.isSameTask(node)) {
                throw RuntimeException("not allow to contain the same id ${node.id}")
            }
        }

        //校验环形依赖
        for (behindTask in node.behindTasks) {
            if (!traversalVisitor.contains(behindTask)) {
                traversalVisitor.add(behindTask)
            } else {
                throw RuntimeException("not allow loopback dependency, task id= ${node.id}")
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
