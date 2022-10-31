package com.mozhimen.componentk.audiok.cons

object CAudioKEvent {
    private const val pre_ = "audiok_"

    private object Theme {
        const val progress_ = "progress_"
        const val audio_ = "audio_"
    }

    private object Event {
        const val update = "update"
        const val start = "start"
        const val load = "load"
        const val pause = "pause"
        const val error = "error"
        const val complete = "complete"
        const val popup = "popup"
        const val release = "release"
    }

    const val progress_update = pre_ + Theme.progress_ + Event.update
    const val audio_load = pre_ + Theme.audio_ + Event.load
    const val audio_start = pre_ + Theme.audio_ + Event.start
    const val audio_pause = pre_ + Theme.audio_ + Event.pause
    const val audio_complete = pre_ + Theme.audio_ + Event.complete
    const val audio_popup = pre_ + Theme.audio_ + Event.popup
    const val audio_release = pre_ + Theme.audio_ + Event.release
    const val audio_error = pre_ + Theme.audio_ + Event.error
}