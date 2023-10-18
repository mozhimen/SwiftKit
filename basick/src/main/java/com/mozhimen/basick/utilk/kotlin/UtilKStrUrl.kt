package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.RequiresPermission
import coil.request.ImageRequest
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.net.UtilKNetDeal
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.inputStream2anyBitmap
import com.mozhimen.basick.utilk.java.io.inputStream2outputStream
import com.mozhimen.basick.utilk.java.net.UtilKURI
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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
@AManifestKRequire(CPermission.INTERNET)
suspend fun String.strUrl2bitmap(): Bitmap? =
    UtilKStrUrl.strUrl2bitmap(this)

@RequiresPermission(CPermission.INTERNET)
@AManifestKRequire(CPermission.INTERNET)
fun String.strUrl2bitmap2(): Bitmap =
    UtilKStrUrl.strUrl2bitmap2(this)

@AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE, CPermission.INTERNET)
fun String.strUrl2file(destFileNameWithName: String, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, destFileNameWithName, isAppend)

@RequiresPermission(CPermission.INTERNET)
@AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE, CPermission.INTERNET)
fun String.strUrl2file(destFile: File, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, destFile, isAppend)

object UtilKStrUrl : BaseUtilK() {
    /**
     * 判断url是否可连
     * @param strUrl String
     * @return Boolean
     */
    @JvmStatic
    fun isStrUrlConnectable(strUrl: String): Boolean {
        val uRI: URI?
        try {
            uRI = URI(strUrl)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            e.message?.et(TAG)
            return false
        }
        if (uRI.host == null) {
            return false
        } else if (!UtilKURI.isSchemeValid(uRI)) {
            return false
        }
        return true
    }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.INTERNET)
    suspend fun strUrl2bitmap(strUrl: String): Bitmap? =
        (UtilKContext.getImageLoader(_context).execute(ImageRequest.Builder(_context).data(strUrl).build()).drawable as? BitmapDrawable)?.bitmap

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.INTERNET)
    fun strUrl2bitmap2(strUrl: String): Bitmap =
        URL(strUrl).openStream().inputStream2anyBitmap()

    @JvmStatic
    @AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE, CPermission.INTERNET)
    fun strUrl2file(strUrl: String, destFileNameWithName: String, isAppend: Boolean = false): File? =
        strUrl2file(strUrl, destFileNameWithName.strFilePath2file(), isAppend)

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE, CPermission.INTERNET)
    fun strUrl2file(strUrl: String, destFile: File, isAppend: Boolean = false): File? {
        require(strUrl.isNotEmpty()) { "$TAG httpUrl must be not empty" }
        UtilKFile.deleteFile(destFile)

        var inputStream: InputStream? = null
        var httpURLConnection: HttpURLConnection? = null

        try {
            val url = URL(strUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            if (httpURLConnection is HttpsURLConnection) {
                val sslContext = UtilKNetDeal.getSLLContext()
                if (sslContext != null)
                    httpURLConnection.sslSocketFactory = sslContext.socketFactory
            }
            httpURLConnection.apply {
                connectTimeout = 60 * 1000
                readTimeout = 60 * 1000
                connect()
            }
            inputStream = httpURLConnection.inputStream
            inputStream.inputStream2outputStream(destFile.file2fileOutputStream(isAppend), 1024)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream?.close()
            httpURLConnection?.disconnect()
        }
        return null
    }
}