package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
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
import android.os.VibratorManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import coil.ImageLoader
import coil.imageLoader
import com.mozhimen.basick.elemk.cons.CVersCode


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

    @RequiresApi(CVersCode.V_31_11_S)
    @JvmStatic
    fun getVibratorManager(context: Context): VibratorManager =
        context.getSystemService(Activity.VIBRATOR_MANAGER_SERVICE) as VibratorManager

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
        getApplicationContext(context).getSystemService(Context.WIFI_SERVICE) as WifiManager

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

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColor(context: Context, @ColorRes resId: Int): Int =
        context.getColor(resId)

    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getColorStateList(context: Context, @ColorRes resId: Int): ColorStateList =
        context.getColorStateList(resId)

    @SuppressLint("UseCompatLoadingForDrawables")
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