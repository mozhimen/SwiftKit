package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName TaskKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface IChainKListener {
    fun onStart(node: BaseChainKTask)
    fun onRunning(node: BaseChainKTask)
    fun onFinished(node: BaseChainKTask)
}
