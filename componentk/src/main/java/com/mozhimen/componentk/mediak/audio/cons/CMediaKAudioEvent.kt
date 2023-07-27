package com.mozhimen.componentk.mediak.audio.cons

object CMediaKAudioEvent {
    private const val PRE = "AUDIOK_"

    private object Theme {
        const val PROGRESS = "PROGRESS_"
        const val AUDIO = "AUDIO_"
    }

    private object Event {
        const val UPDATE = "UPDATE"
        const val START = "START"
        const val LOAD = "LOAD"
        const val PAUSE = "PAUSE"
        const val ERROR = "ERROR"
        const val COMPLETE = "COMPLETE"
        const val POPUP = "POPUP"
        const val RELEASE = "RELEASE"
    }

    const val EVENT_PROGRESS_UPDATE =
        PRE + Theme.PROGRESS + Event.UPDATE
    const val EVENT_AUDIO_LOAD =
        PRE + Theme.AUDIO + Event.LOAD
    const val EVENT_AUDIO_START =
        PRE + Theme.AUDIO + Event.START
    const val EVENT_AUDIO_PAUSE =
        PRE + Theme.AUDIO + Event.PAUSE
    const val EVENT_AUDIO_COMPLETE =
        PRE + Theme.AUDIO + Event.COMPLETE
    const val EVENT_AUDIO_POPUP =
        PRE + Theme.AUDIO + Event.POPUP//MAudio+isHasNext
    const val EVENT_AUDIO_RELEASE =
        PRE + Theme.AUDIO + Event.RELEASE
    const val EVENT_AUDIO_ERROR =
        PRE + Theme.AUDIO + Event.ERROR
}