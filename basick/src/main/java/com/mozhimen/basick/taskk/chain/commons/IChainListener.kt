package com.mozhimen.basick.taskk.chain.commons

import com.mozhimen.basick.taskk.chain.bases.BaseChainTask

/**
 * @ClassName TaskKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface IChainListener {
    fun onStart(node: BaseChainTask)
    fun onRunning(node: BaseChainTask)
    fun onFinished(node: BaseChainTask)
}
