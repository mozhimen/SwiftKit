package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName IChainKTask
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 11:36
 * @Version 1.0
 */
interface IChainKTask {
    fun run(id: String)
    fun start()
    fun addTaskKListener(listener: IChainKListener)

    /**
     * 给当前task添加-个前置的依赖任务
     * @param node ChainKTask
     */
    fun dependOn(node: BaseChainKTask)

    /**
     * 给当前task移除一个前置依赖任务
     * @param dependTask ChainKTask
     */
    fun removeDependence(dependTask: BaseChainKTask)

    /**
     * 给当前任务添加后置依赖项
     * 他和dependOn 是相反的
     * @param behindTask ChainKTask
     */
    fun behind(behindTask: BaseChainKTask)

    /**
     * 给当前task移除-个后置的任务
     * @param behindTask ChainKTask
     */
    fun removeBehind(behindTask: BaseChainKTask)
}