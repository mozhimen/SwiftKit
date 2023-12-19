package com.mozhimen.basick.utilk.androidx.core

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.File

/**
 * @ClassName UtilKFileProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/19
 * @Version 1.0
 */
object UtilKFileProvider {

    @JvmStatic
    fun getUriForStrFilePathName(context: Context, authority: String, strFilePathName: String): Uri? =
        if (strFilePathName.isEmpty()) null
        else getUriForFile(context, authority, strFilePathName.strFilePath2file())

    @JvmStatic
    fun getUriForFile(context: Context, authority: String, file: File): Uri =
        FileProvider.getUriForFile(context, authority, file)
}