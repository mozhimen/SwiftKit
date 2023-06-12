package com.mozhimen.basick.chaink.helpers

import com.mozhimen.basick.chaink.mos.ChainKNode

/**
 * @ClassName TaskKComparator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 17:07
 * @Version 1.0
 */
object NodeComparator {
    @JvmStatic
    fun compareNode(node1: ChainKNode, node2: ChainKNode): Int {
        if (node1.priority < node2.priority) {
            return 1
        }
        if (node1.priority > node2.priority) {
            return -1
        }
        return 0
    }
}