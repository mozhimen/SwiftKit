package com.mozhimen.basick.taskk.chain.commons

import com.mozhimen.basick.taskk.chain.bases.BaseChainTask

/**
 * @ClassName ITaskKCreator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 21:09
 * @Version 1.0
 */
interface IChainCreator {
    fun createChain(chainName: String): BaseChainTask
}