package com.mozhimen.basick.utilk.content

import android.app.Activity
import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.hardware.display.DisplayManager
import android.hardware.usb.UsbManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import coil.ImageLoader
import coil.imageLoader


/**
 * @ClassName UtilKContext
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 18:19
 * @Version 1.0
 */
object UtilKContext {
    @JvmStatic
    fun getImageLoader(context: Context): ImageLoader =
        context.imageLoader

    @JvmStatic
    fun getClassLoader(context: Context): ClassLoader =
        context.classLoader

    @JvmStatic
    fun getApplicationContext(context: Context): Context =
        context.applicationContext

    @JvmStatic
    fun getApplicationInfo(context: Context): ApplicationInfo =
        context.applicationInfo

    @JvmStatic
    fun getContentResolver(context: Context): ContentResolver =
        context.contentResolver

    @JvmStatic
    fun grantUriPermission(context: Context, uri: Uri, modeFlags: Int) {
        context.grantUriPermission(getPackageName(context), uri, modeFlags)
    }

    @JvmStatic
    fun getPackageName(context: Context): String =
        context.packageName

    @JvmStatic
    fun getVibrator(context: Context): Vibrator =
        context.getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator

    @JvmStatic
    fun getWindowManager(context: Context): WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    @JvmStatic
    fun getInputMethodManager(context: Context): InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    @JvmStatic
    fun getTelephonyManager(context: Context): TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @JvmStatic
    fun getWifiManager(context: Context): WifiManager =
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    @JvmStatic
    fun getAudioManager(context: Context): AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    @JvmStatic
    fun getConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @JvmStatic
    fun getDisplayManager(context: Context): DisplayManager =
        context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager

    @JvmStatic
    fun getActivityManager(context: Context) =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    @JvmStatic
    fun getLayoutInflater(context: Context) =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @JvmStatic
    fun getUsbManager(context: Context): UsbManager =
        context.getSystemService(Context.USB_SERVICE) as UsbManager

//    @JvmStatic
//    fun getFilesAbsolutePath(context: Context): String =
//        context.filesDir.absolutePath
//
//    @JvmStatic
//    fun getExternalRootFilesAbsolutePath(context: Context): String =
//        context.getExternalFilesDir(null)!!.absolutePath
//
//    @JvmStatic
//    fun getCacheAbsolutePath(context: Context): String =
//        context.cacheDir.absolutePath

    @JvmStatic
    fun getPackageManager(context: Context): PackageManager =
        context.packageManager

    @JvmStatic
    fun checkCallingOrSelfPermission(context: Context, permission: String): Int =
        context.checkCallingOrSelfPermission(permission)

    @JvmStatic
    fun getResources(context: Context): Resources =
        context.resources

    @JvmStatic
    fun getString(context: Context, @StringRes resId: Int): String =
        context.getString(resId)

    @JvmStatic
    fun getString(context: Context, @StringRes resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

    @JvmStatic
    fun getColor(context: Context, @ColorRes resId: Int): Int =
        context.getColor(resId)

    @JvmStatic
    fun getColorStateList(context: Context, @ColorRes resId: Int): ColorStateList =
        context.getColorStateList(resId)

    @JvmStatic
    fun getDrawable(context: Context, @DrawableRes drawableId: Int): Drawable? =
        context.getDrawable(drawableId)

    @JvmStatic
    fun getAssets(context: Context): AssetManager =
        context.assets

    @JvmStatic
    fun getTheme(context: Context): Theme =
        context.theme

}