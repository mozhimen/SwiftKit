package com.mozhimen.basick.taskk.chain.commons

import com.mozhimen.basick.taskk.chain.bases.BaseChainTask

/**
 * @ClassName IChainKTask
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface IChainTask {
    fun run(id: String)
    fun start()
    fun addTaskKListener(listener: IChainListener)

    /**
     * 给当前task添加-个前置的依赖任务
     * @param node ChainKTask
     */
    fun dependOn(node: BaseChainTask)

    /**
     * 给当前task移除一个前置依赖任务
     * @param dependTask ChainKTask
     */
    fun removeDependence(dependTask: BaseChainTask)

    /**
     * 给当前任务添加后置依赖项
     * 他和dependOn 是相反的
     * @param behindTask ChainKTask
     */
    fun behind(behindTask: BaseChainTask)

    /**
     * 给当前task移除-个后置的任务
     * @param behindTask ChainKTask
     */
    fun removeBehind(behindTask: BaseChainTask)
}