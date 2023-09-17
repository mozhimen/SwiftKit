package com.mozhimen.basick.elemk.java.lang

import java.lang.ref.WeakReference
import java.util.concurrent.locks.Lock

/**
 * @ClassName NodeRunnableWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/14 23:37
 * @Version 1.0
 */
interface INodeRunnableWrapper {
    /**
     * 在队尾插入
     * @param candidate NodeRunnableWrapper
     */
    fun insert(candidate: NodeRunnableWrapper)
    fun remove(): NodeRunnable
    fun remove(runnable: Runnable): NodeRunnable?
}

class NodeRunnableWrapper(lock: Lock, runnable: Runnable?) : INodeRunnableWrapper {
    var next: NodeRunnableWrapper? = null
    var prev: NodeRunnableWrapper? = null
    var runnable: Runnable?
    var lock: Lock
    var nodeRunnable: NodeRunnable

    init {
        this.runnable = runnable
        this.lock = lock
        this.nodeRunnable = NodeRunnable(WeakReference(runnable), WeakReference(this))
    }

    override fun insert(candidate: NodeRunnableWrapper) {
        lock.lock()
        try {
            next?.prev = candidate
            candidate.next = next
            next = candidate
            candidate.prev = this
        } finally {
            lock.unlock()
        }
    }

    override fun remove(): NodeRunnable {
        lock.lock()
        try {
            prev?.next = next
            next?.prev = prev
            prev = null
            next = null
        } finally {
            lock.unlock()
        }
        return nodeRunnable
    }

    override fun remove(runnable: Runnable): NodeRunnable? {
        lock.lock()
        try {
            var current = next // Skipping head
            while (current != null) {
                if (current.runnable == runnable)  // We do comparison exactly how Handler does inside
                    return current.remove()
                current = current.next
            }
        } finally {
            lock.unlock()
        }
        return null
    }
}