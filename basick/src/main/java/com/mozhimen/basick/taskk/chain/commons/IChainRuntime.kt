package com.mozhimen.basick.taskk.chain.commons

import com.mozhimen.basick.taskk.chain.bases.BaseChainTask
import com.mozhimen.basick.taskk.chain.mos.MChainTaskRuntimeInfo

/**
 * @ClassName IChainKRuntime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface IChainRuntime {
    fun addBlockTask(id: String)
    fun addBlockTasks(vararg ids: String)
    fun removeBlockTask(id: String)
    fun hasBlockTasks(): Boolean
    fun setThreadName(task: BaseChainTask, threadName: String)
    fun setTaskStateInfo(task: BaseChainTask)
    fun getTaskRuntimeInfo(id: String): MChainTaskRuntimeInfo?
    fun hasWaitingTasks(): Boolean
    fun runWaitingTasks()
    /**
     * 根据task 的属性以不同的策略 调度 task
     * @param task ChainKNode
     */
    fun executeTask(task: BaseChainTask)

    /**
     * 校验依赖树中是否存在环形依赖校验，依赖树中是否存在taskId相同的任务 初始化task 对应taskRuntimeInfo
     * 遍历依赖树完成启动前的检查和初始化
     * @param task ChainKTask
     */
    fun traversalDependencyTreeAndInit(task: BaseChainTask)
}