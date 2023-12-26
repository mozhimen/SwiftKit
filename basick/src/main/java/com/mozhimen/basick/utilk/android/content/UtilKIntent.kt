package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.commons.IExtension_Listener
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CIntentFilter

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

fun Intent.getQueryParameter(key: String): String? =
    UtilKIntent.getQueryParameter(this, key)

object UtilKIntent {
    @JvmStatic
    fun get(context: Context, clazz: Class<*>): Intent =
        Intent(context, clazz)

    inline fun <reified T> get(context: Context): Intent =
        Intent(context, T::class.java)

    inline fun <reified T> get(context: Context, block: IExtension_Listener<Intent>): Intent =
        Intent(context, T::class.java).apply(block)

    @JvmStatic
    @AManifestKRequire(CIntentFilter.ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE)
    fun getQueryParameter(intent: Intent, key: String): String? =
        intent.data?.getQueryParameter(key)

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 要启动的intent是否可用
     */
    @JvmStatic
    fun isIntentAvailable(intent: Intent, context: Context): Boolean =
        resolveActivity(intent, context) != null

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun resolveActivity(intent: Intent, context: Context): ComponentName? =
        intent.resolveActivity(UtilKPackageManager.get(context))

    @JvmStatic
    fun createChooser(target: Intent, title: CharSequence): Intent =
        Intent.createChooser(target, title)
}