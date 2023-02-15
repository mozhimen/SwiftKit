package com.mozhimen.basick.flowk.commons

import com.mozhimen.basick.flowk.mos.FlowKNode

/**
 * @ClassName TaskKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface IFlowKListener {
    fun onStart(flowKNode: FlowKNode)
    fun onRunning(flowKNode: FlowKNode)
    fun onFinished(flowKNode: FlowKNode)
}
