package com.mozhimen.componentk.mediak.audio.cons

object CMediaKAudioCons {
    object Event {
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
            PRE + Theme.PROGRESS + CMediaKAudioCons.Event.UPDATE
        const val EVENT_AUDIO_LOAD =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.LOAD
        const val EVENT_AUDIO_START =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.START
        const val EVENT_AUDIO_PAUSE =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.PAUSE
        const val EVENT_AUDIO_COMPLETE =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.COMPLETE
        const val EVENT_AUDIO_POPUP =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.POPUP//MAudio+isHasNext
        const val EVENT_AUDIO_RELEASE =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.RELEASE
        const val EVENT_AUDIO_ERROR =
            PRE + Theme.AUDIO + CMediaKAudioCons.Event.ERROR
    }
}