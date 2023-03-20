package com.mozhimen.basick.utilk.content

import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.usb.UsbManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import coil.ImageLoader
import coil.imageLoader
import com.mozhimen.basick.utilk.device.UtilKUsb


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
    fun getApplicationContext(context: Context): Context =
        context.applicationContext

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
    fun getWifiManager(context: Context): WifiManager =
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager

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
    fun getUsbManager(context: Context): UsbManager =
        context.getSystemService(Context.USB_SERVICE) as UsbManager

    @JvmStatic
    fun getFilesAbsolutePath(context: Context): String =
        context.filesDir.absolutePath

    @JvmStatic
    fun getPackageManager(context: Context): PackageManager =
        context.packageManager
}