package com.mozhimen.basick.utilk.java.util

import android.util.Pair
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.util.Locale

/**
 * @ClassName UtilKLocaleWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/4
 * @Version 1.0
 */
object UtilKLocaleWrapper : IUtilK {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getOSLang(): Pair<String, String> {
        val language = Locale.getDefault().language
        var languageTag = Locale.getDefault().toLanguageTag().replace("-Hant", "")
        UtilKLogWrapper.d(TAG, "getOSLang: language $language toLanguageTag $languageTag");
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
        UtilKLogWrapper.d(TAG, "getOSLang: language " + pair.first + " " + pair.second)
        return pair
    }
}