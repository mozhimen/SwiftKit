package com.mozhimen.basick.utilk.java.util

import android.util.Pair
import java.util.Locale

/**
 * @ClassName UtilKLocaleWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/4
 * @Version 1.0
 */
object UtilKLocaleWrapper {
    @JvmStatic
    fun getOSLang(): Pair<String, String> {
        val language = Locale.getDefault().language
        //        Log.d(TAG, "getOSLang: language" + language);
        var languageTag = Locale.getDefault().toLanguageTag().replace("-Hant", "")
        //        Log.d(TAG, "getOSLang: toLanguageTag " + languageTag);
        try {
            val tags = languageTag.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var newTag = ""
            if (tags.size > 1) {
                newTag = "r" + tags[1]
            }
            languageTag = tags[0] + "-" + newTag
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val pair = Pair(language, languageTag)
        //        Log.d(TAG, "getOSLang: language " + pair.first + " " + pair.second);
        return pair
    }
}