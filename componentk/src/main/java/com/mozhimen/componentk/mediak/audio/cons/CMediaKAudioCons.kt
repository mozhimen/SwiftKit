package com.mozhimen.componentk.mediak.audio.cons

object CMediaKAudioCons {
    object Event {
        private const val PRE = "AUDIOK_"

        private const val PROGRESS = "PROGRESS_"
        private const val AUDIO = "AUDIO_"

        private const val UPDATE = "UPDATE"
        private const val START = "START"
        private const val LOAD = "LOAD"
        private const val PAUSE = "PAUSE"
        private const val ERROR = "ERROR"
        private const val COMPLETE = "COMPLETE"
        private const val POPUP = "POPUP"
        private const val RELEASE = "RELEASE"

        const val PROGRESS_UPDATE = PRE + PROGRESS + UPDATE
        const val AUDIO_LOAD = PRE + AUDIO + LOAD
        const val AUDIO_START = PRE + AUDIO + START
        const val AUDIO_PAUSE = PRE + AUDIO + PAUSE
        const val AUDIO_COMPLETE = PRE + AUDIO + COMPLETE
        const val AUDIO_POPUP = PRE + AUDIO + POPUP//MAudio+isHasNext
        const val AUDIO_RELEASE = PRE + AUDIO + RELEASE
        const val AUDIO_ERROR = PRE + AUDIO + ERROR
    }
}