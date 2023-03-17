package com.mozhimen.basick.utilk.res

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.mozhimen.basick.utilk.content.UtilKApplication

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
object UtilKRes {
    private val _context = UtilKApplication.instance.get()
    private val _resourcesSys = Resources.getSystem()
    private val _resourcesApp = _context.resources

    /**
     * 获取系统Resources
     * @return Resources
     */
    @JvmStatic
    fun getSystemResource(): Resources =
        _resourcesSys

    /**
     * 获取App的Resources
     * @return Resources
     */
    @JvmStatic
    fun getAppResource(): Resources =
        _resourcesApp

    /**
     * 获取字符串
     * @param resId Int
     * @return String
     */
    @JvmOverloads
    @JvmStatic
    fun getString(@StringRes resId: Int, context: Context = _context): String =
        context.getString(resId)

    /**
     * 获取字符串
     * @param resId Int
     * @param formatArgs Array<out Any?>
     * @return String
     */
    @JvmOverloads
    @JvmStatic
    fun getString(@StringRes resId: Int, context: Context = _context, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    fun getColor(@ColorRes resId: Int, context: Context = _context): Int =
        context.getColor(resId)

    /**
     * 获取颜色
     * @param resId Int
     * @return Int
     */
    @JvmStatic
    fun getColor2(@ColorRes resId: Int, context: Context = _context): Int =
        ContextCompat.getColor(context, resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    fun getColorStateList(@ColorRes resId: Int, context: Context = _context): ColorStateList =
        context.getColorStateList(resId)

    /**
     * 获取颜色list
     * @param resId Int
     * @return ColorStateList?
     */
    @JvmStatic
    fun getColorStateList2(@ColorRes resId: Int, context: Context = _context): ColorStateList? =
        ContextCompat.getColorStateList(context, resId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    fun getDrawable(@DrawableRes drawableId: Int, context: Context = _context): Drawable? =
        context.getDrawable(drawableId)

    /**
     * 获取Drawable
     * @param drawableId Int
     * @return Drawable?
     */
    @JvmStatic
    fun getDrawable2(@DrawableRes drawableId: Int, context: Context = _context): Drawable? =
        ContextCompat.getDrawable(context, drawableId)

    /**
     * 获取DimensionPixelSize
     * @param dimensionId Int
     * @return Int
     */
    @JvmStatic
    fun getDimensionPixelSize(dimensionId: Int, context: Context = _context): Int =
        getDimensionPixelSize(dimensionId, context.resources)

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
    fun getStringArray(resId: Int, context: Context = _context): Array<String> =
        getStringArray(resId, context.resources)

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
    fun getInteger(resId: Int, context: Context = _context): Int =
        getInteger(resId, context.resources)

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