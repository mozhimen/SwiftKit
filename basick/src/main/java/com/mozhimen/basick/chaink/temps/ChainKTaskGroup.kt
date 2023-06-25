package com.mozhimen.basick.chaink.temps

import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.commons.IChainKCreator
import com.mozhimen.basick.chaink.commons.IChainKTaskGroupBuilder
import com.mozhimen.basick.chaink.helpers.ChainKTaskFactory

/**
 * @ClassName TaskKGroup
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 20:57
 * @Version 1.0
 */
class ChainKTaskGroup private constructor(id: String) : BaseChainKTask(id) {

    lateinit var endTask: BaseChainKTask//任务组中所有任务的结束节点
    lateinit var startTask: BaseChainKTask//任务组的开始节点

    /////////////////////////////////////////////////////////////////////////////////////////

    override fun start() {
        startTask.start()
    }

    override fun run(id: String) {
        //不需要处理的
    }

    override fun behind(behindTask: BaseChainKTask) {
        //当咱们给一一个任务组添加后置任务的时候，那么这个任务应该添加到组当中谁的后面? ? ?
        endTask.behind(behindTask)//把新来的后置任务添加到任务组的结束节点上面去，这样的话，任务组里面所有的任务都结束了，这个后置任务才会执行
    }

    override fun dependOn(node: BaseChainKTask) {
        startTask.dependOn(node)
    }

    override fun removeDependence(dependTask: BaseChainKTask) {
        startTask.removeDependence(dependTask)
    }

    override fun removeBehind(behindTask: BaseChainKTask) {
        endTask.removeBehind(behindTask)
    }

    class Builder(groupName: String, flowKCreator: IChainKCreator) : IChainKTaskGroupBuilder {
        private val _taskFactory: ChainKTaskFactory = ChainKTaskFactory(flowKCreator)
        private val _startTask: BaseChainKTask = CriticalChainKTask(groupName + "_end")
        private val _endTask: BaseChainKTask = CriticalChainKTask(groupName + "_start")
        private val _flowKGroup: ChainKTaskGroup = ChainKTaskGroup(groupName)

        ///////////////////////////////////////////////////////////////////////////////////////////////

        private var _priority = 0//默认为该任务组中所村任务优先级的最高的
        private var _currentTaskShouldDependOnStartTask = true//本次添加进来的这个task是否把start节点当做依赖

        //那如果这个task它存在与其他task的依赖关系，那么就不能直接添加到start节点的后面了。而需要通过dependOn来指定任务的依赖关系
        private var _currentAddTask: BaseChainKTask? = null

        ///////////////////////////////////////////////////////////////////////////////////////////////

        override fun add(id: String): Builder {
            val taskK = _taskFactory.getTaskK(id)
            if (taskK.priority > _priority) {
                _priority = taskK.priority
            }
            return add(taskK)
        }

        override fun dependOn(id: String): Builder {
            return dependOn(_taskFactory.getTaskK(id))
        }

        override fun build(): ChainKTaskGroup {
            if (_currentAddTask == null) {
                _startTask.behind(_endTask)
            } else {
                if (_currentTaskShouldDependOnStartTask) {
                    _startTask.behind(_currentAddTask!!)
                }
            }
            _startTask.priority = _priority
            _endTask.priority = _priority
            _flowKGroup.startTask = _startTask
            _flowKGroup.endTask = _endTask
            return _flowKGroup
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////

        private fun add(node: BaseChainKTask): Builder {
            if (_currentTaskShouldDependOnStartTask && _currentAddTask != null) {
                _startTask.behind(_currentAddTask!!)
            }
            _currentAddTask = node
            _currentTaskShouldDependOnStartTask = true
            _currentAddTask!!.behind(_endTask)
            return this
        }

        private fun dependOn(node: BaseChainKTask): Builder {
            //确定刚才我们所添加进来的mCurrentAddTask和task的依赖关系--- - -mCurrentAddTask依赖于task
            node.behind(_currentAddTask!!)
            // start --task10 --mCurrentAddTask (task 11) --end
            _endTask.removeDependence(node)
            _currentTaskShouldDependOnStartTask = false
            return this
        }
    }
}
