package com.mozhimen.basick.utilk.android.media

import android.content.Context
import android.media.AudioManager
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKAudioManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 23:02
 * @Version 1.0
 */
object UtilKAudioManager {
    @JvmStatic
    fun get(context: Context): AudioManager =
        UtilKContext.getAudioManager(context)

    /////////////////////////////////////////////////////////////////////////

}