package com.mozhimen.basick.utilk.res

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.UtilKContextCompat

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
object UtilKRes {
    private val _context = UtilKApplication.instance.get()

    /**
     * 获取系统Resources
     * @return Resources
     */
    @JvmStatic
    fun getSystemResources(): Resources =
        Resources.getSystem()

    /**
     * 获取App的Resources
     * @return Resources
     */
    @JvmStatic
    fun getAppResources(): Resources =
        UtilKContext.getResources(_context)

    /**
     * 获取字符串
     * @param resId Int
     * @return String
     */
    @JvmStatic
    fun getString(@StringRes resId: Int): String =
        UtilKContext.getString(_context, resId)

    /**
     * 获取字符串
     * @param resId Int
     * @param formatArgs Array<out Any?>
     * @return String
     */
    @JvmStatic
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(_context, resId, formatArgs)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    fun getColor(@ColorRes resId: Int): Int =
        UtilKContext.getColor(_context, resId)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    fun getColor2(@ColorRes resId: Int): Int =
        UtilKContextCompat.getColor(_context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    fun getColorStateList(@ColorRes resId: Int): ColorStateList =
        UtilKContext.getColorStateList(_context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    fun getColorStateList2(@ColorRes resId: Int): ColorStateList? =
        UtilKContextCompat.getColorStateList(_context, resId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    fun getDrawable(@DrawableRes drawableId: Int): Drawable? =
        UtilKContext.getDrawable(_context, drawableId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    fun getDrawable2(@DrawableRes drawableId: Int): Drawable? =
        UtilKContextCompat.getDrawable(_context, drawableId)

    /**
     * 获取DimensionPixelSize
     * @param dimensionId Int
     * @return Int
     */
    @JvmStatic
    fun getDimensionPixelSize(dimensionId: Int): Int =
        getDimensionPixelSize(dimensionId, getAppResources())

    /**
     * 获取DimensionPixelSize
     * @param dimensionId Int
     * @param resources Resources
     * @return Int
     */
    @JvmStatic
    fun getDimensionPixelSize(dimensionId: Int, resources: Resources): Int =
        resources.getDimensionPixelSize(dimensionId)

    /**
     * 获取StringArray
     * @param resId Int
     * @return Array<String>
     */
    @JvmStatic
    fun getStringArray(resId: Int): Array<String> =
        getStringArray(resId, getAppResources())

    /**
     * 获取StringArray
     * @param resId Int
     * @param resources Resources
     * @return Array<String>
     */
    @JvmStatic
    fun getStringArray(resId: Int, resources: Resources): Array<String> =
        resources.getStringArray(resId)

    /**
     * 获取Integer
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    fun getInteger(resId: Int): Int =
        getInteger(resId, getAppResources())

    /**
     * 获取Integer
     * @param resId Int
     * @param resources Resources
     * @return Int
     */
    @JvmStatic
    fun getInteger(resId: Int, resources: Resources): Int =
        resources.getInteger(resId)
}