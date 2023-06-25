package com.mozhimen.basick.chaink.helpers

import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.commons.IChainKCreator

/**
 * @ClassName ChainKTaskFactory
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 14:01
 * @Version 1.0
 */
internal class ChainKTaskFactory(private val _flowKCreator: IChainKCreator) {
    //利用iTaskCreator创建task 实例，并管理
    private val _chainKTasksCache: MutableMap<String, BaseChainKTask> = HashMap()

    fun getTaskK(id: String): BaseChainKTask {
        var task = _chainKTasksCache[id]
        if (task != null) return task
        task = _flowKCreator.createChain(id)
        _chainKTasksCache[id] = task
        return task
    }
}