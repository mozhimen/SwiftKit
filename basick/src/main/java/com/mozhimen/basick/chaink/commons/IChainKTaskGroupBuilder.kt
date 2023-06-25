package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName IChainKBuilder
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 14:07
 * @Version 1.0
 */
interface IChainKTaskGroupBuilder {
    fun add(id: String): IChainKTaskGroupBuilder
    fun dependOn(id: String): IChainKTaskGroupBuilder
    fun build(): BaseChainKTask
}