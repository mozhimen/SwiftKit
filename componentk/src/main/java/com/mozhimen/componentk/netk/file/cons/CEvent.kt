package com.mozhimen.componentk.netk.file.cons


/**
 * @ClassName CEvent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 18:22
 * @Version 1.0
 */
object CEvent {
    private const val pre_ = "netk_"

    private object Theme {
        const val download_ = "download_"
    }

    private object Event {
        const val start = "start"
        const val fail = "fail"
        const val load = "load"
        const val pause = "pause"
        const val error = "error"
        const val complete = "complete"
        const val popup = "popup"
        const val release = "release"
    }

    const val download_start = pre_ + Theme.download_ + Event.start
    const val download_fail = pre_ + Theme.download_ + Event.fail
}