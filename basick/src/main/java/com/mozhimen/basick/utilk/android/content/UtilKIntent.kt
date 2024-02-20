package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import com.mozhimen.basick.elemk.commons.IExtension_Listener
import com.mozhimen.basick.lintk.optins.intent_filter.OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:03
 * @Version 1.0
 */
fun Intent.createChooser(title: CharSequence): Intent =
    UtilKIntent.createChooser(this, title)

fun Intent.isIntentAvailable(context: Context): Boolean =
    UtilKIntent.isIntentAvailable(this, context)

@OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE
fun Intent.getQueryParameter(key: String): String? =
    UtilKIntent.getQueryParameter(this, key)

object UtilKIntent : IUtilK {
    @JvmStatic
    fun get(context: Context, clazz: Class<*>): Intent =
        Intent(context, clazz)

    inline fun <reified T> get(context: Context): Intent =
        Intent(context, T::class.java)

    inline fun <reified T> get(context: Context, block: IExtension_Listener<Intent>): Intent =
        Intent(context, T::class.java).apply(block)

    /**
     * http://com.xxx.xxx?id=?
     */
    @JvmStatic
    @OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE
    fun getQueryParameter(intent: Intent, key: String): String? =
        intent.data?.getQueryParameter(key)

    @JvmStatic
    fun <T> getParcelableArrayListExtra(intent: Intent, name: String, clazz: Class<T>): ArrayList<T>? {
        return if (UtilKBuildVersion.isAfterV_33_13_TIRAMISU()) {
            intent.getParcelableArrayListExtra(name, clazz)
        } else {
            intent.getParcelableArrayListExtra<Parcelable>(name) as? ArrayList<T>?
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 要启动的intent是否可用
     */
    @JvmStatic
    fun isIntentAvailable(intent: Intent, context: Context): Boolean =
        (resolveActivity(intent, context) != null).also { Log.d(TAG, "isIntentAvailable: $it") }

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun resolveActivity(intent: Intent, context: Context): ComponentName? =
        intent.resolveActivity(UtilKPackageManager.get(context))

    @JvmStatic
    fun createChooser(target: Intent, title: CharSequence): Intent =
        Intent.createChooser(target, title)
}