package com.mozhimen.abilityk.transk

import android.app.Activity
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.abilityk.transk.mos.MText2SpeechConfig
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CQuery
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
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
@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
@AManifestKRequire(CPermission.FOREGROUND_SERVICE, CQuery.TTS_SERVICE)
class TransKTTS<O>(owner: O, config: MText2SpeechConfig = MText2SpeechConfig(Locale.CHINA, 1.5f, 1.5f)) : BaseWakeBefDestroyLifecycleObserver() where O : LifecycleOwner, O : Activity {
    private var _transKText2Speech: TextToSpeech? = null

    init {
        if (UtilKBuildVers.isAfterV_28_9_P()) {
            if (!ManifestKPermission.checkPermission(CPermission.FOREGROUND_SERVICE)) {
                UtilKLaunchActivity.startSettingAppDetails(owner)
            }
        }
        _transKText2Speech = TextToSpeech(owner) {
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
        _transKText2Speech?.apply {
            stop()
            shutdown()
        }
        super.onDestroy(owner)
    }
}