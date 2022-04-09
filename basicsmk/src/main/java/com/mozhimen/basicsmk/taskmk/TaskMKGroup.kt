package com.mozhimen.basicsmk.taskmk

import com.mozhimen.basicsmk.taskmk.commons.ITaskMKCreator

/**
 * @ClassName TaskMKGroup
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 20:57
 * @Version 1.0
 */
class TaskMKGroup private constructor(id: String) : TaskMK(id) {

    lateinit var endTask: TaskMK//任务组中所有任务的结束节点
    lateinit var startTask: TaskMK//任务组的开始节点

    override fun start() {
        startTask.start()
    }

    override fun run(id: String) {
        //不需要处理的
    }

    override fun behind(behindTask: TaskMK) {
        //当咱们给一一个任务组添加后置任务的时候，那么这个任务应该添加到组当中谁的后面? ? ?
        endTask.behind(behindTask)//把新来的后置任务添加到任务组的结束节点上面去，这样的话，任务组里面所有的任务都结束了，这个后置任务才会执行
    }

    override fun dependOn(dependTaskMK: TaskMK) {
        startTask.dependOn(dependTaskMK)
    }

    override fun removeDependence(dependTask: TaskMK) {
        startTask.removeDependence(dependTask)
    }

    override fun removeBehind(behindTask: TaskMK) {
        endTask.removeBehind(behindTask)
    }

    class Builder(groupName: String, iTaskMKCreator: ITaskMKCreator) {
        private val _taskFactory = TaskMKFactory(iTaskMKCreator)
        private val _startTask: TaskMK = CriticalTaskMK(groupName + "_end")
        private val _endTask: TaskMK = CriticalTaskMK(groupName + "_start")
        private val _taskMKGroup: TaskMKGroup = TaskMKGroup(groupName)
        private var _priority = 0//默认为该任务组中所村任务优先级的最高的

        private var _currentTaskShouldDependOnStartTask = true//本次添加进来的这个task是否把start节点当做依赖
        //那如果这个task它存在与其他task的依赖关系，那么就不能直接添加到start节点的后面了。而需要通过dependOn来指定任务的依赖关系

        private var _currentAddTask: TaskMK? = null

        fun add(id: String): Builder {
            val taskMK = _taskFactory.getTaskMK(id)
            if (taskMK.priority > _priority) {
                _priority = taskMK.priority
            }
            return add(taskMK)
        }

        private fun add(taskMK: TaskMK): Builder {
            if (_currentTaskShouldDependOnStartTask && _currentAddTask != null) {
                _startTask.behind(_currentAddTask!!)
            }
            _currentAddTask = taskMK
            _currentTaskShouldDependOnStartTask = true
            _currentAddTask!!.behind(_endTask)
            return this
        }

        fun dependOn(id: String): Builder {
            return dependOn(_taskFactory.getTaskMK(id))
        }

        private fun dependOn(taskMK: TaskMK): Builder {
            //确定刚才我们所添加进来的mCurrentAddTask和task的依赖关系--- - -mCurrentAddTask依赖于task
            taskMK.behind(_currentAddTask!!)
            // start --task10 --mCurrentAddTask (task 11) --end
            _endTask.removeDependence(taskMK)
            _currentTaskShouldDependOnStartTask = false
            return this
        }

        fun build(): TaskMKGroup {
            if (_currentAddTask == null) {
                _startTask.behind(_endTask)
            } else {
                if (_currentTaskShouldDependOnStartTask) {
                    _startTask.behind(_currentAddTask!!)
                }
            }
            _startTask.priority = _priority
            _endTask.priority = _priority
            _taskMKGroup.startTask = _startTask
            _taskMKGroup.endTask = _endTask
            return _taskMKGroup
        }
    }

    private class TaskMKFactory(private val iTaskMKCreator: ITaskMKCreator) {
        //利用iTaskCreator创建task 实例，并管理
        private val mCacheTasks: MutableMap<String, TaskMK> = HashMap()
        fun getTaskMK(id: String): TaskMK {
            var taskMK = mCacheTasks[id]
            if (taskMK != null) {
                return taskMK
            }
            taskMK = iTaskMKCreator.createTaskMK(id)
            requireNotNull(taskMK) { "create task fail, make sure iTaskCreator can create a Task with only taskId" }
            mCacheTasks[id] = taskMK
            return taskMK
        }
    }

    internal class CriticalTaskMK internal constructor(id: String) : TaskMK(id) {
        override fun run(id: String) {
            //nothing to do
        }
    }
}
