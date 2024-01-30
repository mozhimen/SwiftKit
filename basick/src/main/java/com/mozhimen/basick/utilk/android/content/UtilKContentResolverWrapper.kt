package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.elemk.android.provider.cons.COpenableColumns
import com.mozhimen.basick.utilk.android.database.getColumnString
import com.mozhimen.basick.utilk.android.provider.UtilKMediaStore
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.file2strFilePath
import com.mozhimen.basick.utilk.kotlin.getStrFileExtension
import com.mozhimen.basick.utilk.kotlin.getStrFileName
import com.mozhimen.basick.utilk.kotlin.getStrFileParentPath
import com.mozhimen.basick.utilk.kotlin.strFileExtension2strMineTypeImage
import java.io.File

/**
 * @ClassName UtilKContentResolverWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
fun Uri.getMediaColumns(selection: String? = null, selectionArgs: Array<String>? = null): String? =
    UtilKContentResolverWrapper.getMediaColumns(this, selection, selectionArgs)

object UtilKContentResolverWrapper : BaseUtilK() {
    @JvmStatic
    fun getOpenableColumns(uri: Uri, projection: Array<String>? = null, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val cursor = UtilKContentResolver.query(_context, uri, projection, selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(COpenableColumns.DISPLAY_NAME)
                    if (index == -1) return null
                    val data = cursor.getColumnString(COpenableColumns.DISPLAY_NAME)
                    if (data != "null")
                        return data
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    /**
     * api 24?
     */
    @JvmStatic
    fun getMediaColumns(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val cursor = UtilKContentResolver.query(_context, uri, arrayOf(CMediaStore.MediaColumns.DATA), selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(CMediaStore.MediaColumns.DATA)
                    if (index == -1) return null
                    val data = cursor.getColumnString(CMediaStore.MediaColumns.DATA)
                    if (data != "null")
                        return data
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun insertImageAfter29(context: Context, file: File): Uri? =
        insertImageAfter29(context, file.file2strFilePath())

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun insertImageAfter29(context: Context, strFilePathName: String): Uri? {
        val strFilePath = strFilePathName.getStrFileParentPath() ?: run {
            Log.w(TAG, "insertImageAfter29: strFilePath get fail")
            return null
        }
        val strFileName = strFilePathName.getStrFileName() ?: run {
            Log.w(TAG, "insertImageAfter29: strFileName get fail")
            return null
        }
        val strFileExtension = strFilePathName.getStrFileExtension() ?: run {
            Log.w(TAG, "insertImageAfter29: strFileExtension get fail")
            return null
        }
        val strMineType = strFileExtension.strFileExtension2strMineTypeImage()
        return insertImageAfter29(context, strMineType, strFilePath, strFileName)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun insertImageAfter29(context: Context, mineType: String, strFilePath: String, strFileName: String): Uri? =
        UtilKContentResolver.insert(context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, UtilKContentValues.get(mineType, strFilePath, strFileName))

    @JvmStatic
    fun deleteImageFile(strFilePathName: String) {
        UtilKContentResolver.delete(_context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${CMediaStore.Images.Media.DATA}='${strFilePathName}'", null)
    }
}