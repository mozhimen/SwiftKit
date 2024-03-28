package com.mozhimen.basick.utilk.android.graphics

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.mozhimen.basick.utilk.java.net.UtilKURL
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import java.io.InputStream

/**
 * @ClassName UtilKDrawable
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/22 22:42
 * @Version 1.0
 */
fun Drawable.applyColorFilter(@ColorInt intColor: Int) {
    UtilKDrawable.applyColorFilter(this, intColor)
}

object UtilKDrawable {
    //从网络获取图片
    @JvmStatic
    fun getDrawable_ofStrUrl(strUrl: String, drawableName: String = UtilKStrFile.getStrFileName_ofNow()): Drawable? =
        try {
            createFromStream(UtilKURL.openStream(strUrl), drawableName/*"netUrl.jpg"*/)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    /////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun createFromStream(inputStream: InputStream?, srcName: String?): Drawable? =
        Drawable.createFromStream(inputStream, srcName)

    @JvmStatic
    fun mutate(drawable: Drawable): Drawable =
        drawable.mutate()

    /////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyColorFilter(drawable: Drawable, @ColorInt intColor: Int) {
        mutate(drawable).setColorFilter(intColor, PorterDuff.Mode.SRC_IN)
    }
}