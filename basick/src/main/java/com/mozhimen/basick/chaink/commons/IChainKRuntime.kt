package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.mos.MChainKTaskRuntimeInfo

/**
 * @ClassName IChainKRuntime
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 11:42
 * @Version 1.0
 */
interface IChainKRuntime {
    fun addBlockTask(id: String)
    fun addBlockTasks(vararg ids: String)
    fun removeBlockTask(id: String)
    fun hasBlockTasks(): Boolean
    fun setThreadName(task: BaseChainKTask, threadName: String)
    fun setTaskStateInfo(task: BaseChainKTask)
    fun getTaskRuntimeInfo(id: String): MChainKTaskRuntimeInfo?
    fun hasWaitingTasks(): Boolean
    fun runWaitingTasks()
    /**
     * 根据task 的属性以不同的策略 调度 task
     * @param task ChainKNode
     */
    fun executeTask(task: BaseChainKTask)

    /**
     * 校验依赖树中是否存在环形依赖校验，依赖树中是否存在taskId相同的任务 初始化task 对应taskRuntimeInfo
     * 遍历依赖树完成启动前的检查和初始化
     * @param task ChainKTask
     */
    fun traversalDependencyTreeAndInit(task: BaseChainKTask)
}