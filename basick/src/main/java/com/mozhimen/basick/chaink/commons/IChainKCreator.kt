package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName ITaskKCreator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 21:09
 * @Version 1.0
 */
interface IChainKCreator {
    fun createChain(chainName: String): BaseChainKTask
}