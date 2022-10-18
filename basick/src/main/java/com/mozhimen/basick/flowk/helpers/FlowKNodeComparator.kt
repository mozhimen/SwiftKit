package com.mozhimen.basick.flowk.helpers

import com.mozhimen.basick.flowk.FlowKNode

/**
 * @ClassName TaskKComparator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 17:07
 * @Version 1.0
 */
object FlowKNodeComparator {
    fun compareTaskK(flowKNode1: FlowKNode, flowKNode2: FlowKNode): Int {
        if (flowKNode1.priority < flowKNode2.priority) {
            return 1
        }
        if (flowKNode1.priority > flowKNode2.priority) {
            return -1
        }
        return 0
    }
}