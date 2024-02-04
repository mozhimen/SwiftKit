package com.mozhimen.basick.utilk.android.graphics

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKRes

/**
 * @ClassName UtilKStateListDrawable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/26 13:45
 * @Version 1.0
 */
object UtilKStateListDrawable {
    @JvmStatic
    fun get(drawableNormal: Drawable, drawablePressed: Drawable): StateListDrawable =
        StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), drawablePressed)
            addState(intArrayOf(-android.R.attr.state_pressed), drawableNormal)
        }

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun get(@DrawableRes intResDrawableNormal: Int, @DrawableRes intResDrawablePressed: Int): StateListDrawable? {
        val drawableNormal = UtilKRes.getDrawable_ofContext(intResDrawableNormal)
        val drawablePressed = UtilKRes.getDrawable_ofContext(intResDrawablePressed)
        return if (drawableNormal != null && drawablePressed != null) get(drawableNormal, drawablePressed) else null
    }

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @OPermission_INTERNET
    fun getForStrUrls(strUrlDrawableNormal: String, strUrlDrawablePressed: String): Drawable? {
        val drawableNormal = UtilKDrawable.getDrawableForStrUrl(strUrlDrawableNormal)
        val drawablePressed = UtilKDrawable.getDrawableForStrUrl(strUrlDrawablePressed)
        return if (drawableNormal != null && drawablePressed != null) {
            get(drawableNormal, drawablePressed)
        } else null
    }
}