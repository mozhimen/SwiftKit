package com.mozhimen.basick.taskk.threadpool.helpers

/**
 * @ClassName Cache
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 14:54
 * @Version 1.0
 */
class PriorityRunnable(val name: String, val priority: Int, private val runnable: Runnable) : Runnable,
    Comparable<PriorityRunnable> {
    override fun run() {
        runnable.run()
    }

    override fun compareTo(other: PriorityRunnable): Int {
        return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
    }
}