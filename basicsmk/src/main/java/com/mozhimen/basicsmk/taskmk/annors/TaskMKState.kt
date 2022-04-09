package com.mozhimen.basicsmk.taskmk.annors

import androidx.annotation.IntDef

/**
 * @ClassName TaskMKState
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:22
 * @Version 1.0
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    TaskMKState.IDLE,
    TaskMKState.RUNNING,
    TaskMKState.FINISHED,
    TaskMKState.START
)
annotation class TaskMKState {
    companion object {
        const val IDLE = 0 //静止
        const val START = 1 //启动,可能需要等待调度，
        const val RUNNING = 2 //运行
        const val FINISHED = 3 //运行结束
    }
}
