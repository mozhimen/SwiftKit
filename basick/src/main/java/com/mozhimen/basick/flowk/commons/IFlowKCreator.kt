package com.mozhimen.basick.flowk.commons

import com.mozhimen.basick.flowk.FlowKNode

/**
 * @ClassName ITaskKCreator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 21:09
 * @Version 1.0
 */
interface IFlowKCreator {
    fun createFlow(flowName: String): FlowKNode
}