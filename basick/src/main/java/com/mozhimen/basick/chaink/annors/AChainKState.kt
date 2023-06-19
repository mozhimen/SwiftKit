package com.mozhimen.basick.chaink.annors

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
    AChainKState.IDLE,
    AChainKState.RUNNING,
    AChainKState.FINISHED,
    AChainKState.START
)
annotation class AChainKState {
    companion object {
        const val IDLE = 0 //静止
        const val START = 1 //启动,可能需要等待调度，
        const val RUNNING = 2 //运行
        const val FINISHED = 3 //运行结束
    }
}
