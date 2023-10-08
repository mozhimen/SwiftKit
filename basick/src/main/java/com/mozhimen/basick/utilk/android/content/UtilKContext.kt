package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.DownloadManager
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
import android.location.LocationManager
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
import com.mozhimen.basick.elemk.android.content.cons.CContext
import com.mozhimen.basick.elemk.android.os.cons.CVersCode


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
    fun getPackageName(context: Context): String =
        context.packageName

    @JvmStatic
    fun getPackageManager(context: Context): PackageManager =
        context.packageManager

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDownloadManager(context: Context): DownloadManager =
        context.getSystemService(CContext.DOWNLOAD_SERVICE) as DownloadManager

    @JvmStatic
    fun getLocationManager(context: Context): LocationManager =
        context.getSystemService(CContext.LOCATION_SERVICE) as LocationManager

    @JvmStatic
    fun getVibrator(context: Context): Vibrator =
        context.getSystemService(CContext.VIBRATOR_SERVICE) as Vibrator

    @RequiresApi(CVersCode.V_31_11_S)
    @JvmStatic
    fun getVibratorManager(context: Context): VibratorManager =
        context.getSystemService(CContext.VIBRATOR_MANAGER_SERVICE) as VibratorManager

    @JvmStatic
    fun getWindowManager(context: Context): WindowManager =
        context.getSystemService(CContext.WINDOW_SERVICE) as WindowManager

    @JvmStatic
    fun getInputMethodManager(context: Context): InputMethodManager =
        context.getSystemService(CContext.INPUT_METHOD_SERVICE) as InputMethodManager

    @JvmStatic
    fun getTelephonyManager(context: Context): TelephonyManager =
        context.getSystemService(CContext.TELEPHONY_SERVICE) as TelephonyManager

    @JvmStatic
    fun getWifiManager(context: Context): WifiManager =
        getApplicationContext(context).getSystemService(CContext.WIFI_SERVICE) as WifiManager

    @JvmStatic
    fun getAudioManager(context: Context): AudioManager =
        context.getSystemService(CContext.AUDIO_SERVICE) as AudioManager

    @JvmStatic
    fun getConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(CContext.CONNECTIVITY_SERVICE) as ConnectivityManager

    @JvmStatic
    fun getDisplayManager(context: Context): DisplayManager =
        context.getSystemService(CContext.DISPLAY_SERVICE) as DisplayManager

    @JvmStatic
    fun getActivityManager(context: Context) =
        context.getSystemService(CContext.ACTIVITY_SERVICE) as ActivityManager

    @JvmStatic
    fun getLayoutInflater(context: Context) =
        context.getSystemService(CContext.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @JvmStatic
    fun getUsbManager(context: Context): UsbManager =
        context.getSystemService(CContext.USB_SERVICE) as UsbManager

    ////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun grantUriPermission(context: Context, uri: Uri, modeFlags: Int) {
        context.grantUriPermission(getPackageName(context), uri, modeFlags)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun checkSelfPermission(context: Context, permission: String): Int =
        context.checkSelfPermission(permission)

    @JvmStatic
    fun checkCallingOrSelfPermission(context: Context, permission: String): Int =
        context.checkCallingOrSelfPermission(permission)
}