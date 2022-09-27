package com.mozhimen.abilityk.transk

import android.Manifest
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.abilityk.transk.mos.TransKText2SpeechConfig
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.componentk.permissionk.PermissionK
import java.util.*

/**
 * @ClassName TransKText2Speech
 * @Description 需要权限FOREGROUND_SERVICE
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/5 21:08
 * @Version 1.0
 */
class TransKText2Speech(owner: LifecycleOwner, config: TransKText2SpeechConfig) : DefaultLifecycleObserver {

    companion object {
        @Volatile
        private var instance: TransKText2Speech? = null

        fun getInstance(owner: LifecycleOwner, config: TransKText2SpeechConfig = TransKText2SpeechConfig(Locale.CHINA, 1.5f, 1.5f)) =
            instance ?: synchronized(this) {
                instance ?: TransKText2Speech(owner, config).also { instance = it }
            }
    }

    private var _transKText2Speech: TextToSpeech? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            require(
                PermissionK.checkPermissions(
                    UtilKGlobal.instance.getApp()!!,
                    Manifest.permission.FOREGROUND_SERVICE
                )
            ) { "FOREGROUND_SERVICE permission denied" }
        }
        owner.lifecycle.addObserver(this)
        _transKText2Speech = TextToSpeech(UtilKGlobal.instance.getApp()!!) {
            if (it == TextToSpeech.SUCCESS) {
                val supportRes = _transKText2Speech!!.setLanguage(config.language)
                require(supportRes != TextToSpeech.LANG_MISSING_DATA && supportRes != TextToSpeech.LANG_NOT_SUPPORTED) {
                    "current lang is not support"
                }
                _transKText2Speech!!.setPitch(config.pitch)// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                _transKText2Speech!!.setSpeechRate(config.speechRate)//设定语速,默认1.0正常语速
            }
        }
    }

    /**
     * 播放
     * @param text String
     */
    fun play(text: String) {
        if (text.isEmpty()) return
        _transKText2Speech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    /**
     * 设置语调
     * @param pitch Float
     */
    fun setPitch(pitch: Float) {
        _transKText2Speech?.setPitch(pitch)
    }

    /**
     * 设置语速
     * @param speechRate Float
     */
    fun setSpeechRate(speechRate: Float) {
        _transKText2Speech?.setSpeechRate(speechRate)
    }

    override fun onPause(owner: LifecycleOwner) {
        _transKText2Speech?.stop()
        _transKText2Speech?.shutdown()
        owner.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}