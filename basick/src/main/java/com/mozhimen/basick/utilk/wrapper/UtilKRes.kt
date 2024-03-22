package com.mozhimen.basick.utilk.wrapper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.content.res.XmlResourceParser
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.AnimRes
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.content.UtilKContextCompat
import com.mozhimen.basick.utilk.android.content.UtilKResources
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.androidx.core.UtilKResourcesCompat
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKRes
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/25 16:56
 * @Version 1.0
 */
fun Context.gainString(@StringRes intResStr: Int): String =
    UtilKRes.gainString(this, intResStr)

fun Context.gainString(@StringRes intResStr: Int, vararg formatArgs: Any): String =
    UtilKRes.gainString(this, intResStr, formatArgs)

fun Context.gainStringArray(@ArrayRes intResArray: Int): Array<String> =
    UtilKRes.gainStringArray(this, intResArray)

fun Context.gainColor(@ColorRes intResColor: Int): Int =
    UtilKRes.gainColor(this, intResColor)

fun Context.gainColor(@ColorRes intResColor: Int, theme: Theme?): Int =
    UtilKRes.gainColor(this, intResColor, theme)

fun Context.gainColorStateList(@ColorRes intResColor: Int): ColorStateList? =
    UtilKRes.gainColorStateList(this, intResColor)

fun Context.gainColorStateList(@ColorRes intResColor: Int, theme: Theme?): ColorStateList? =
    UtilKRes.gainColorStateList(this, intResColor, theme)

fun Context.gainDrawable(@DrawableRes intResDrawable: Int): Drawable? =
    UtilKRes.gainDrawable(this, intResDrawable)

fun Context.gainDrawable(@DrawableRes intResDrawable: Int, theme: Theme?): Drawable? =
    UtilKRes.gainDrawable(this, intResDrawable, theme)

fun Context.gainDrawableForDensity(@DrawableRes intResDrawable: Int, density: Int): Drawable? =
    UtilKRes.gainDrawableForDensity(this, intResDrawable, density)

fun Context.gainDrawableForDensity(@DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
    UtilKRes.gainDrawableForDensity(this, intResDrawable, density, theme)

fun Context.gainBoolean(@BoolRes intResBool: Int): Boolean =
    UtilKRes.gainBoolean(this, intResBool)

fun Context.gainInteger(@IntegerRes intResInt: Int): Int =
    UtilKRes.gainInteger(this, intResInt)

fun Context.gainFloat(@DimenRes intResDimen: Int): Float =
    UtilKRes.gainFloat(this, intResDimen)

fun Context.gainDimensionPixelOffset(@DimenRes intResDimen: Int): Int =
    UtilKRes.gainDimensionPixelOffset(this, intResDimen)

fun Context.gainDimensionPixelSize(@DimenRes intResDimen: Int): Int =
    UtilKRes.gainDimensionPixelSize(this, intResDimen)

fun Context.gainDimension(@DimenRes intResDimen: Int): Float =
    UtilKRes.gainDimension(this, intResDimen)

fun Context.gainIntArray(@ArrayRes intResArray: Int): IntArray =
    UtilKRes.gainIntArray(this, intResArray)

fun Context.gainXml(@XmlRes intResXml: Int): XmlResourceParser =
    UtilKRes.gainXml(this, intResXml)

fun Context.gainAnimation(@AnimRes intResAnim: Int): XmlResourceParser =
    UtilKRes.gainAnimation(this, intResAnim)

fun Context.gainLayout(@LayoutRes intResLayout: Int): XmlResourceParser =
    UtilKRes.gainLayout(this, intResLayout)

fun Context.gainFont(@FontRes intResFont: Int): Typeface? =
    UtilKRes.gainFont(this, intResFont)

///////////////////////////////////////////////////////////////////////////////////

object UtilKRes : BaseUtilK() {

    @JvmStatic
    fun gainString(context: Context, @StringRes intResStr: Int): String =
        getString_ofContext(context, intResStr)

    @JvmStatic
    fun gainString(@StringRes intResStr: Int): String =
        gainString(_context, intResStr)

    //////////////////////////

    @JvmStatic
    fun getString_ofContext(context: Context, @StringRes intResStr: Int): String =
        UtilKContext.getString(context, intResStr)

    @JvmStatic
    fun getString_ofContext(@StringRes intResStr: Int): String =
        getString_ofContext(_context, intResStr)

    @JvmStatic
    fun getString_ofResources(context: Context, @StringRes intResStr: Int): String =
        UtilKResources.getString_ofApp(context, intResStr)

    @JvmStatic
    fun getString_ofResources(@StringRes intResStr: Int): String =
        getString_ofResources(_context, intResStr)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainString(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        getString_ofContext(context, intResStr, formatArgs)

    @JvmStatic
    fun gainString(@StringRes intResStr: Int, vararg formatArgs: Any): String =
        gainString(_context, intResStr, formatArgs)

    //////////////////////////

    @JvmStatic
    fun getString_ofContext(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(context, intResStr, formatArgs)

    @JvmStatic
    fun getString_ofContext(@StringRes intResStr: Int, vararg formatArgs: Any): String =
        getString_ofContext(_context, intResStr, formatArgs)

    @JvmStatic
    fun getString_ofResources(context: Context, @StringRes intResStr: Int, vararg formatArgs: Any): String =
        UtilKContext.getString(context, intResStr, formatArgs)

    @JvmStatic
    fun getString_ofResources(@StringRes intResStr: Int, vararg formatArgs: Any): String =
        getString_ofResources(_context, intResStr, formatArgs)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainStringArray(context: Context, @ArrayRes intResArray: Int): Array<String> =
        getStringArray_ofResources(context, intResArray)

    @JvmStatic
    fun gainStringArray(@ArrayRes intResArray: Int): Array<String> =
        gainStringArray(_context, intResArray)

    //////////////////////////

    @JvmStatic
    fun getStringArray_ofResources(context: Context, @ArrayRes intResArray: Int): Array<String> =
        UtilKResources.getStringArray_ofApp(context, intResArray)

    @JvmStatic
    fun getStringArray_ofResources(@ArrayRes intResArray: Int): Array<String> =
        getStringArray_ofResources(_context, intResArray)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainColor(context: Context, @ColorRes intResColor: Int): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) getColor_ofContext(context, intResColor)
        else getColor_ofContextCompat(intResColor)

    @JvmStatic
    fun gainColor(@ColorRes intResColor: Int): Int =
        gainColor(_context, intResColor)

    //////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getColor_ofContext(context: Context, @ColorRes intResColor: Int): Int =
        UtilKContext.getColor(context, intResColor)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getColor_ofContext(@ColorRes intResColor: Int): Int =
        getColor_ofContext(_context, intResColor)

    @JvmStatic
    fun getColor_ofResources(context: Context, @ColorRes intResColor: Int): Int =
        UtilKResources.getColor_ofApp(context, intResColor)

    @JvmStatic
    fun getColor_ofResources(@ColorRes intResColor: Int): Int =
        getColor_ofResources(_context, intResColor)

    @JvmStatic
    fun getColor_ofContextCompat(context: Context, @ColorRes intResColor: Int): Int =
        UtilKContextCompat.getColor(context, intResColor)

    @JvmStatic
    fun getColor_ofContextCompat(@ColorRes intResColor: Int): Int =
        getColor_ofContextCompat(_context, intResColor)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainColor(context: Context, @ColorRes intResColor: Int, theme: Theme?): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) getColor_ofResources(context, intResColor, theme)
        else getColor_ofResourcesCompat(intResColor, theme)

    @JvmStatic
    fun gainColor(@ColorRes intResColor: Int, theme: Theme?): Int =
        gainColor(_context, intResColor, theme)

    //////////////////////////

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColor_ofResources(context: Context, @ColorRes intResColor: Int, theme: Theme?): Int =
        UtilKResources.getColor_ofApp(context, intResColor, theme)

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColor_ofResources(@ColorRes intResColor: Int, theme: Theme?): Int =
        getColor_ofResources(_context, intResColor, theme)

    @JvmStatic
    fun getColor_ofResourcesCompat(context: Context, @ColorRes intResColor: Int, theme: Theme?): Int =
        UtilKResourcesCompat.getColor(UtilKResources.get_ofApp(context), intResColor, theme)

    @JvmStatic
    fun getColor_ofResourcesCompat(@ColorRes intResColor: Int, theme: Theme?): Int =
        getColor_ofResourcesCompat(_context, intResColor, theme)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainColorStateList(context: Context, @ColorRes intResColor: Int): ColorStateList? =
        if (UtilKBuildVersion.isAfterV_23_6_M()) getColorStateList_ofContext(context, intResColor)
        else getColorStateList_ofContextCompat(intResColor)

    @JvmStatic
    fun gainColorStateList(@ColorRes intResColor: Int): ColorStateList? =
        gainColorStateList(_context, intResColor)

    //////////////////////////

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColorStateList_ofContext(context: Context, @ColorRes intResColor: Int): ColorStateList =
        UtilKContext.getColorStateList(context, intResColor)

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColorStateList_ofContext(@ColorRes intResColor: Int): ColorStateList =
        getColorStateList_ofContext(_context, intResColor)

    @JvmStatic
    fun getColorStateList_ofResources(context: Context, @ColorRes intResColor: Int): ColorStateList =
        UtilKResources.getColorStateList_ofApp(context, intResColor)

    @JvmStatic
    fun getColorStateList_ofResources(@ColorRes intResColor: Int): ColorStateList =
        getColorStateList_ofResources(_context, intResColor)

    @JvmStatic
    fun getColorStateList_ofContextCompat(context: Context, @ColorRes intResColor: Int): ColorStateList? =
        UtilKContextCompat.getColorStateList(context, intResColor)

    @JvmStatic
    fun getColorStateList_ofContextCompat(@ColorRes intResColor: Int): ColorStateList? =
        getColorStateList_ofContextCompat(_context, intResColor)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainColorStateList(context: Context, @ColorRes intResColor: Int, theme: Theme?): ColorStateList? =
        if (UtilKBuildVersion.isAfterV_23_6_M()) getColorStateList_ofResources(context, intResColor, theme)
        else getColorStateList_ofResourcesCompat(intResColor, theme)

    @JvmStatic
    fun gainColorStateList(@ColorRes intResColor: Int, theme: Theme?): ColorStateList? =
        gainColorStateList(_context, intResColor, theme)

    //////////////////////////

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColorStateList_ofResources(context: Context, @ColorRes intResColor: Int, theme: Theme?): ColorStateList =
        UtilKResources.getColorStateList_ofApp(context, intResColor, theme)

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColorStateList_ofResources(@ColorRes intResColor: Int, theme: Theme?): ColorStateList =
        getColorStateList_ofResources(_context, intResColor, theme)

    @JvmStatic
    fun getColorStateList_ofResourcesCompat(context: Context, @ColorRes intResColor: Int, theme: Theme?): ColorStateList? =
        UtilKResourcesCompat.getColorStateList(UtilKResources.get_ofApp(context), intResColor, theme)

    @JvmStatic
    fun getColorStateList_ofResourcesCompat(@ColorRes intResColor: Int, theme: Theme?): ColorStateList? =
        getColorStateList_ofResourcesCompat(_context, intResColor, theme)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDrawable(context: Context, @DrawableRes intResDrawable: Int): Drawable? =
        if (UtilKBuildVersion.isAfterV_21_5_L()) getDrawable_ofContext(context, intResDrawable)
        else getDrawable_ofContextCompat(context, intResDrawable)

    @JvmStatic
    fun gainDrawable(@DrawableRes intResDrawable: Int): Drawable? =
        gainDrawable(_context, intResDrawable)

    //////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable_ofContext(context: Context, @DrawableRes intResDrawable: Int): Drawable? =
        UtilKContext.getDrawable(context, intResDrawable)

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable_ofContext(@DrawableRes intResDrawable: Int): Drawable? =
        getDrawable_ofContext(_context, intResDrawable)

    @JvmStatic
    fun getDrawable_ofResources(context: Context, @DrawableRes intResDrawable: Int): Drawable =
        UtilKResources.getDrawable_ofApp(context, intResDrawable)

    @JvmStatic
    fun getDrawable_ofResources(@DrawableRes intResDrawable: Int): Drawable =
        getDrawable_ofResources(_context, intResDrawable)

    @JvmStatic
    fun getDrawable_ofContextCompat(context: Context, @DrawableRes intResDrawable: Int): Drawable? =
        UtilKContextCompat.getDrawable(context, intResDrawable)

    @JvmStatic
    fun getDrawable_ofContextCompat(@DrawableRes intResDrawable: Int): Drawable? =
        getDrawable_ofContextCompat(_context, intResDrawable)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDrawable(context: Context, @DrawableRes intResDrawable: Int, theme: Theme?): Drawable? =
        if (UtilKBuildVersion.isAfterV_21_5_L()) getDrawable_ofResources(context, intResDrawable, theme)
        else getDrawable_ofContextCompat(context, intResDrawable)

    @JvmStatic
    fun gainDrawable(@DrawableRes intResDrawable: Int, theme: Theme?): Drawable? =
        gainDrawable(_context, intResDrawable, theme)

    //////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable_ofResources(context: Context, @DrawableRes intResDrawable: Int, theme: Theme?): Drawable =
        UtilKResources.getDrawable_ofApp(context, intResDrawable, theme)

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawable_ofResources(@DrawableRes intResDrawable: Int, theme: Theme?): Drawable =
        getDrawable_ofResources(_context, intResDrawable, theme)

    @JvmStatic
    fun getDrawable_ofResourcesCompact(context: Context, @DrawableRes intResDrawable: Int, theme: Theme?): Drawable? =
        UtilKResourcesCompat.getDrawable(UtilKResources.get_ofApp(context), intResDrawable, theme)

    @JvmStatic
    fun getDrawable_ofResourcesCompact(@DrawableRes intResDrawable: Int, theme: Theme?): Drawable? =
        getDrawable_ofResourcesCompact(_context, intResDrawable, theme)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDrawableForDensity(context: Context, @DrawableRes intResDrawable: Int, density: Int): Drawable? =
        getDrawableForDensity_ofResources(context, intResDrawable, density)

    @JvmStatic
    fun gainDrawableForDensity(@DrawableRes intResDrawable: Int, density: Int): Drawable? =
        gainDrawableForDensity(_context, intResDrawable, density)

    //////////////////////////
    @JvmStatic
    fun getDrawableForDensity_ofResources(context: Context, @DrawableRes intResDrawable: Int, density: Int): Drawable? =
        UtilKResources.getDrawableForDensity_ofApp(context, intResDrawable, density)

    @JvmStatic
    fun getDrawableForDensity_ofResources(@DrawableRes intResDrawable: Int, density: Int): Drawable? =
        getDrawableForDensity_ofResources(_context, intResDrawable, density)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDrawableForDensity(context: Context, @DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        if (UtilKBuildVersion.isAfterV_21_5_L()) getDrawableForDensity_ofResources(context, intResDrawable, density, theme)
        else getDrawableForDensity_ofResourcesCompat(context, intResDrawable, density, theme)

    @JvmStatic
    fun gainDrawableForDensity(@DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        gainDrawableForDensity(_context, intResDrawable, density, theme)

    //////////////////////////

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawableForDensity_ofResources(context: Context, @DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        UtilKResources.getDrawableForDensity_ofApp(context, intResDrawable, density, theme)

    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun getDrawableForDensity_ofResources(@DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        getDrawableForDensity_ofResources(_context, intResDrawable, density, theme)

    @JvmStatic
    fun getDrawableForDensity_ofResourcesCompat(context: Context, @DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        UtilKResourcesCompat.getDrawableForDensity(UtilKResources.get_ofApp(context), intResDrawable, density, theme)

    @JvmStatic
    fun getDrawableForDensity_ofResourcesCompat(@DrawableRes intResDrawable: Int, density: Int, theme: Theme?): Drawable? =
        getDrawableForDensity_ofResourcesCompat(_context, intResDrawable, density, theme)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainBoolean(context: Context, @BoolRes intResBool: Int): Boolean =
        getBoolean_ofResources(context, intResBool)

    @JvmStatic
    fun gainBoolean(@BoolRes intResBool: Int): Boolean =
        gainBoolean(_context, intResBool)

    //////////////////////////

    @JvmStatic
    fun getBoolean_ofResources(context: Context, @BoolRes intResBool: Int): Boolean =
        UtilKResources.getBoolean_ofApp(context, intResBool)

    @JvmStatic
    fun getBoolean_ofResources(@BoolRes intResBool: Int): Boolean =
        getBoolean_ofResources(_context, intResBool)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainInteger(context: Context, @IntegerRes intResInt: Int): Int =
        getInteger_ofResources(context, intResInt)

    @JvmStatic
    fun gainInteger(@IntegerRes intResInt: Int): Int =
        gainInteger(_context, intResInt)

    //////////////////////////

    @JvmStatic
    fun getInteger_ofResources(context: Context, @IntegerRes intResInt: Int): Int =
        UtilKResources.getInteger_ofApp(context, intResInt)

    @JvmStatic
    fun getInteger_ofResources(@IntegerRes intResInt: Int): Int =
        getInteger_ofResources(_context, intResInt)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainFloat(context: Context, @DimenRes intResDimen: Int): Float =
        if (UtilKBuildVersion.isAfterV_29_10_Q()) getFloat_ofResources(context, intResDimen)
        else getFloat_ofResourcesCompact(context, intResDimen)

    @JvmStatic
    fun gainFloat(@DimenRes intResDimen: Int): Float =
        gainFloat(_context, intResDimen)

    //////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getFloat_ofResources(context: Context, @DimenRes intResDimen: Int): Float =
        UtilKResources.getFloat_ofApp(context, intResDimen)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun getFloat_ofResources(@DimenRes intResDimen: Int): Float =
        getFloat_ofResources(_context, intResDimen)

    @JvmStatic
    fun getFloat_ofResourcesCompact(context: Context, @DimenRes intResDimen: Int): Float =
        UtilKResourcesCompat.getFloat(UtilKResources.get_ofApp(context), intResDimen)

    @JvmStatic
    fun getFloat_ofResourcesCompact(@DimenRes intResDimen: Int): Float =
        getFloat_ofResourcesCompact(_context, intResDimen)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDimensionPixelOffset(context: Context, @DimenRes intResDimen: Int): Int =
        getDimensionPixelOffset_ofResources(context, intResDimen)

    @JvmStatic
    fun gainDimensionPixelOffset(@DimenRes intResDimen: Int): Int =
        gainDimensionPixelOffset(_context, intResDimen)

    //////////////////////////

    @JvmStatic
    fun getDimensionPixelOffset_ofResources(context: Context, @DimenRes intResDimen: Int): Int =
        UtilKResources.getDimensionPixelOffset_ofApp(context, intResDimen)

    @JvmStatic
    fun getDimensionPixelOffset_ofResources(@DimenRes intResDimen: Int): Int =
        getDimensionPixelOffset_ofResources(_context, intResDimen)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDimensionPixelSize(context: Context, @DimenRes intResDimen: Int): Int =
        getDimensionPixelSize_ofResources(context, intResDimen)

    @JvmStatic
    fun gainDimensionPixelSize(@DimenRes intResDimen: Int): Int =
        gainDimensionPixelSize(_context, intResDimen)

    //////////////////////////
    @JvmStatic
    fun getDimensionPixelSize_ofResources(context: Context, @DimenRes intResDimen: Int): Int =
        UtilKResources.getDimensionPixelSize_ofApp(context, intResDimen)

    @JvmStatic
    fun getDimensionPixelSize_ofResources(@DimenRes intResDimen: Int): Int =
        getDimensionPixelSize_ofResources(_context, intResDimen)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainDimension(context: Context, @DimenRes intResDimen: Int): Float =
        getDimension_ofResources(context, intResDimen)

    @JvmStatic
    fun gainDimension(@DimenRes intResDimen: Int): Float =
        gainDimension(_context, intResDimen)

    //////////////////////////
    @JvmStatic
    fun getDimension_ofResources(context: Context, @DimenRes intResDimen: Int): Float =
        UtilKResources.getDimension_ofApp(context, intResDimen)

    @JvmStatic
    fun getDimension_ofResources(@DimenRes intResDimen: Int): Float =
        getDimension_ofResources(_context, intResDimen)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainIntArray(context: Context, @ArrayRes intResArray: Int): IntArray =
        getIntArray_ofResources(context, intResArray)

    @JvmStatic
    fun gainIntArray(@ArrayRes intResArray: Int): IntArray =
        gainIntArray(_context, intResArray)

    //////////////////////////
    @JvmStatic
    fun getIntArray_ofResources(context: Context, @ArrayRes intResArray: Int): IntArray =
        UtilKResources.getIntArray_ofApp(context, intResArray)

    @JvmStatic
    fun getIntArray_ofResources(@ArrayRes intResArray: Int): IntArray =
        getIntArray_ofResources(_context, intResArray)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainXml(context: Context, @XmlRes intResXml: Int): XmlResourceParser =
        getXml_ofResources(context, intResXml)

    @JvmStatic
    fun gainXml(@XmlRes intResXml: Int): XmlResourceParser =
        gainXml(_context, intResXml)

    //////////////////////////
    @JvmStatic
    fun getXml_ofResources(context: Context, @XmlRes intResXml: Int): XmlResourceParser =
        UtilKResources.getXml_ofApp(context, intResXml)

    @JvmStatic
    fun getXml_ofResources(@XmlRes intResXml: Int): XmlResourceParser =
        getXml_ofResources(_context, intResXml)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainAnimation(context: Context, @AnimRes intResAnim: Int): XmlResourceParser =
        getAnimation_ofResources(context, intResAnim)

    @JvmStatic
    fun gainAnimation(@AnimRes intResAnim: Int): XmlResourceParser =
        gainAnimation(_context, intResAnim)

    //////////////////////////
    @JvmStatic
    fun getAnimation_ofResources(context: Context, @AnimRes intResAnim: Int): XmlResourceParser =
        UtilKResources.getAnimation_ofApp(context, intResAnim)

    @JvmStatic
    fun getAnimation_ofResources(@AnimRes intResAnim: Int): XmlResourceParser =
        UtilKResources.getAnimation_ofApp(_context, intResAnim)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainLayout(context: Context, @LayoutRes intResLayout: Int): XmlResourceParser =
        getLayout_ofResources(context, intResLayout)

    @JvmStatic
    fun gainLayout(@LayoutRes intResLayout: Int): XmlResourceParser =
        gainLayout(_context, intResLayout)

    //////////////////////////
    @JvmStatic
    fun getLayout_ofResources(context: Context, @LayoutRes intResLayout: Int): XmlResourceParser =
        UtilKResources.getLayout_ofApp(context, intResLayout)

    @JvmStatic
    fun getLayout_ofResources(@LayoutRes intResLayout: Int): XmlResourceParser =
        getLayout_ofResources(_context, intResLayout)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun gainFont(context: Context, @FontRes intResFont: Int): Typeface? =
        if (UtilKBuildVersion.isAfterV_26_8_O()) getFont_ofResources(context, intResFont)
        else getFont_ofResourcesCompat(context, intResFont)

    @JvmStatic
    fun gainFont(@FontRes intResFont: Int): Typeface? =
        gainFont(_context, intResFont)

    //////////////////////////

    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getFont_ofResources(context: Context, @FontRes intResFont: Int): Typeface =
        UtilKResources.getFont_ofApp(context, intResFont)

    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getFont_ofResources(@FontRes intResFont: Int): Typeface =
        getFont_ofResources(_context, intResFont)

    @JvmStatic
    fun getFont_ofResourcesCompat(context: Context, @FontRes intResFont: Int): Typeface? =
        UtilKResourcesCompat.getFont(context, intResFont)

    @JvmStatic
    fun getFont_ofResourcesCompat(@FontRes intResFont: Int): Typeface? =
        getFont_ofResourcesCompat(_context, intResFont)
}