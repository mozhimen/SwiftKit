package com.mozhimen.basick.elemk.java.lang

import java.lang.ref.WeakReference

/**
 * @ClassName NodeRunnable
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/14 23:40
 * @Version 1.0
 */
class NodeRunnable(private val _runnable: WeakReference<Runnable>, private val _wrapper: WeakReference<NodeRunnableWrapper>) : Runnable {
    override fun run() {
        _wrapper.get()?.remove()
        _runnable.get()?.run()
    }
}