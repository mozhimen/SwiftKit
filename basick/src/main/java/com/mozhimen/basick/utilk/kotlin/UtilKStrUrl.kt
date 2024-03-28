package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_EXTERNAL_STORAGE
import com.mozhimen.basick.lintk.optins.permission.OPermission_WRITE_EXTERNAL_STORAGE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapAny_use
import com.mozhimen.basick.utilk.java.net.UtilKHttpURLConnection
import com.mozhimen.basick.utilk.java.net.UtilKURI
import java.io.File
import java.net.URL

/**
 * @ClassName UtilKStringUrl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 15:42
 * @Version 1.0
 */
fun String.isStrUrlConnectable(): Boolean =
    UtilKStrUrl.isStrUrlConnectable(this)

/////////////////////////////////////////////////////////////////////////

@RequiresPermission(CPermission.INTERNET)
@OPermission_INTERNET
fun String.strUrl2bitmapAny(): Bitmap =
    UtilKStrUrl.strUrl2bitmapAny(this)

@OPermission_INTERNET
@OPermission_READ_EXTERNAL_STORAGE
@OPermission_WRITE_EXTERNAL_STORAGE
@RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
fun String.strUrl2file(strFileNameDest: String, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, strFileNameDest, isAppend)

@OPermission_INTERNET
@OPermission_READ_EXTERNAL_STORAGE
@OPermission_WRITE_EXTERNAL_STORAGE
@RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
fun String.strUrl2file(fileDest: File, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, fileDest, isAppend)

/////////////////////////////////////////////////////////////////////////

object UtilKStrUrl : BaseUtilK() {

    @JvmStatic
    fun isStrUrlConnectable(strUrl: String): Boolean =
        UtilKURI.isStrUrlConnectable(strUrl)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @OPermission_INTERNET
    fun strUrl2bitmapAny(strUrl: String): Bitmap =
        URL(strUrl).openStream().inputStream2bitmapAny_use()

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_WRITE_EXTERNAL_STORAGE
    @OPermission_READ_EXTERNAL_STORAGE
    @OPermission_INTERNET
    @RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    fun strUrl2file(strUrl: String, strFileNameDest: String, isAppend: Boolean = false): File? =
        strUrl2file(strUrl, strFileNameDest.strFilePath2file(), isAppend)

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    @OPermission_WRITE_EXTERNAL_STORAGE
    @OPermission_READ_EXTERNAL_STORAGE
    @OPermission_INTERNET
    fun strUrl2file(strUrl: String, fileDest: File, isAppend: Boolean = false): File? =
        UtilKHttpURLConnection.getFileForStrUrl(strUrl, fileDest, isAppend)
}