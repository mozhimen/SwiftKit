package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.RequiresPermission
import coil.request.ImageRequest
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.net.UtilKNetDeal
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.asAnyBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * @ClassName UtilKStringUrl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 15:42
 * @Version 1.0
 */
object UtilKStringUrl : BaseUtilK() {
    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.INTERNET)
    suspend fun strUrl2bitmap(strUrl: String): Bitmap? =
        (UtilKContext.getImageLoader(_context).execute(ImageRequest.Builder(_context).data(strUrl).build()).drawable as? BitmapDrawable)?.bitmap

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    @AManifestKRequire(CPermission.INTERNET)
    fun strUrl2bitmap2(strUrl: String): Bitmap? {
        val tempURL = URL(strUrl)
        var inputStream: InputStream? = null
        return try {
            inputStream = tempURL.openStream()
            inputStream.asAnyBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }

    @JvmStatic
    @AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE,CPermission.READ_EXTERNAL_STORAGE,CPermission.INTERNET)
    fun strUrl2file(strUrl: String, fileNameWithName: String): String {
        require(strUrl.isNotEmpty()) { "$TAG httpUrl must be not empty" }

        val file = File(fileNameWithName)
        UtilKFile.deleteFile(file)

        var inputStream: InputStream? = null
        var fileOutputStream: FileOutputStream? = null
        var httpURLConnection: HttpURLConnection? = null

        try {
            val url = URL(strUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            if (httpURLConnection is HttpsURLConnection) {
                val sslContext = UtilKNetDeal.getSLLContext()
                if (sslContext != null) {
                    val sslSocketFactory = sslContext.socketFactory
                    httpURLConnection.sslSocketFactory = sslSocketFactory
                }
            }
            httpURLConnection.connectTimeout = 60 * 1000
            httpURLConnection.readTimeout = 60 * 1000
            httpURLConnection.connect()
            inputStream = httpURLConnection.inputStream
            fileOutputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int
            while (inputStream.read(buffer).also { len = it } > 0) {
                fileOutputStream.write(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        } finally {
            inputStream?.close()
            fileOutputStream?.flush()
            fileOutputStream?.close()
            httpURLConnection?.disconnect()
        }
        return file.absolutePath
    }
}