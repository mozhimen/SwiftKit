package com.mozhimen.basick.utilk.res

import android.content.res.Resources.Theme
import com.mozhimen.basick.utilk.content.UtilKApplication

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/18 20:57
 * @Version 1.0
 */
object UtilKTheme {
    /**
     * 获取主题
     * @return Theme
     */
    @JvmStatic
    fun getTheme(): Theme =
        UtilKApplication.instance.get().theme
}