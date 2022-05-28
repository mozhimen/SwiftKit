package com.mozhimen.basick.taskk.annors

import androidx.annotation.IntDef

/**
 * @ClassName TaskKState
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:22
 * @Version 1.0
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    TaskKState.IDLE,
    TaskKState.RUNNING,
    TaskKState.FINISHED,
    TaskKState.START
)
annotation class TaskKState {
    companion object {
        const val IDLE = 0 //静止
        const val START = 1 //启动,可能需要等待调度，
        const val RUNNING = 2 //运行
        const val FINISHED = 3 //运行结束
    }
}
