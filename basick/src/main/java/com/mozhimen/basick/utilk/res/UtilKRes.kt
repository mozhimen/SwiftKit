package com.mozhimen.basick.utilk.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
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
    @JvmOverloads
    fun getAppResources(context: Context = _context): Resources =
        UtilKContext.getResources(context)

    @JvmStatic
    fun getDisplayMetrics(): DisplayMetrics =
        getSystemResources().displayMetrics

    /**
     * 获取字符串
     * @param resId Int
     * @return String
     */
    @JvmStatic
    @JvmOverloads
    fun getString(@StringRes resId: Int, context: Context = _context): String =
        UtilKContext.getString(context, resId)

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
     * 获取字符串
     * @param resId Int
     * @param formatArgs Array<out Any?>
     * @return String
     */
    @JvmStatic
    fun getString(@StringRes resId: Int, context: Context, vararg formatArgs: Any): String =
        UtilKContext.getString(context, resId, formatArgs)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    @JvmOverloads
    fun getColor(@ColorRes resId: Int, context: Context = _context): Int =
        UtilKContext.getColor(context, resId)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    @JvmOverloads
    fun getColor2(@ColorRes resId: Int, context: Context = _context): Int =
        UtilKContextCompat.getColor(context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    @JvmOverloads
    fun getColorStateList(@ColorRes resId: Int, context: Context = _context): ColorStateList =
        UtilKContext.getColorStateList(context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    @JvmOverloads
    fun getColorStateList2(@ColorRes resId: Int, context: Context = _context): ColorStateList? =
        UtilKContextCompat.getColorStateList(context, resId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    @JvmOverloads
    fun getDrawable(@DrawableRes drawableId: Int, context: Context = _context): Drawable? =
        UtilKContext.getDrawable(context, drawableId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    @JvmOverloads
    fun getDrawable2(@DrawableRes drawableId: Int, context: Context = _context): Drawable? =
        UtilKContextCompat.getDrawable(context, drawableId)

    /**
     * 获取DimensionPixelOffset
     * @param dimensionId Int
     * @return Int
     */
    @JvmStatic
    fun getDimensionPixelOffset(dimensionId: Int): Int =
        getDimensionPixelOffset(dimensionId, getAppResources())

    /**
     * 获取DimensionPixelOffset
     * @param dimensionId Int
     * @param resources Resources
     * @return Int
     */
    @JvmStatic
    fun getDimensionPixelOffset(dimensionId: Int, resources: Resources): Int =
        resources.getDimensionPixelOffset(dimensionId)

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
     * 获取getDimension
     * @param dimensionId Int
     * @return Int
     */
    @JvmStatic
    fun getDimension(dimensionId: Int): Float =
        getDimension(dimensionId, getAppResources())

    /**
     * 获取getDimension
     * @param dimensionId Int
     * @param resources Resources
     * @return Int
     */
    @JvmStatic
    fun getDimension(dimensionId: Int, resources: Resources): Float =
        resources.getDimension(dimensionId)

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

    /**
     * 获取主题
     * @param context Context
     * @return Theme
     */
    @JvmStatic
    @JvmOverloads
    fun getTheme(context: Context = _context): Theme =
        UtilKContext.getTheme(context)
}