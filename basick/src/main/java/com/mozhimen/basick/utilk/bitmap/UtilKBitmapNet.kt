package com.mozhimen.basick.utilk.bitmap

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.context.UtilKApplication
import java.io.InputStream
import java.net.URL

/**
 * @ClassName UtilKBitmapNet
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/2 23:49
 * @Version 1.0
 */
@APermissionK(Manifest.permission.INTERNET)
object UtilKBitmapNet {
    private val _context = UtilKApplication.instance.get()

    /**
     * 协程方式 获取Bitmap
     * @param url String
     * @return Bitmap
     */
    @JvmStatic
    suspend fun url2BitmapCoroutine(url: String): Bitmap? {
        return (_context.imageLoader.execute(ImageRequest.Builder(_context).data(url).build()).drawable as? BitmapDrawable)?.bitmap
    }

    /**
     * 获取Bitmap
     * @param url String
     * @return Bitmap?
     */
    @JvmStatic
    fun url2Bitmap(url: String): Bitmap? {
        val tempURL = URL(url)
        var inputStream: InputStream? = null
        return try {
            inputStream = tempURL.openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }
}