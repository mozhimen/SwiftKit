package com.mozhimen.abilityk.transk

import android.Manifest
import android.app.Activity
import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.abilityk.transk.mos.MText2SpeechConfig
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.VersionCode
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import java.util.*

/**
 * @ClassName TransKText2Speech
 * @Description 需要权限FOREGROUND_SERVICE
 *
 * AndroidManifest.xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<queries>
<intent>
<action android:name="android.intent.action.TTS_SERVICE" />
</intent>
</queries>

 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/5 21:08
 * @Version 1.0
 */
@ADescription(
    """
    need add this queries in your AndroidManifest.xml
    <queries>
    <intent>
    <action android:name="android.intent.action.TTS_SERVICE" />
    </intent>
    </queries>
"""
)
@APermissionK(Manifest.permission.FOREGROUND_SERVICE)
class TransKTTS<T>(owner: T, config: MText2SpeechConfig = MText2SpeechConfig(Locale.CHINA, 1.5f, 1.5f)) :
    DefaultLifecycleObserver where T : LifecycleOwner, T : Activity {
    private var _transKText2Speech: TextToSpeech? = null
    private val _context = UtilKApplication.instance.get()
    private val TAG = "TransKTTS>>>>>"

    init {
        if (Build.VERSION.SDK_INT >= VersionCode.V_28_9_P) {
            if (!PermissionK.checkPermission(Manifest.permission.FOREGROUND_SERVICE)) {
                UtilKPermission.openSettingSelf(owner)
            }
        }
        owner.lifecycle.addObserver(this)
        _transKText2Speech = TextToSpeech(_context) {
            if (it == TextToSpeech.SUCCESS) {
                val supportRes = _transKText2Speech!!.setLanguage(config.language)
                require(supportRes != TextToSpeech.LANG_MISSING_DATA && supportRes != TextToSpeech.LANG_NOT_SUPPORTED) { "$TAG current language is not support" }
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

    override fun onDestroy(owner: LifecycleOwner) {
        _transKText2Speech?.stop()
        _transKText2Speech?.shutdown()
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }
}