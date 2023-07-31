package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import androidx.annotation.RequiresPermission
import coil.request.ImageRequest
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.kotlin.UtilKString
import java.io.*
import java.net.URL

/**
 * @ClassName UtilKBitmapIO
 * @Description Bitmap IO 类
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/20 16:44
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET)
object UtilKBitmapIO : BaseUtilK() {

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    suspend fun urlStr2bitmap(urlStr: String): Bitmap? {
        return (UtilKContext.getImageLoader(_context).execute(ImageRequest.Builder(_context).data(urlStr).build()).drawable as? BitmapDrawable)?.bitmap
    }

    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    fun urlStr2bitmap2(urlStr: String): Bitmap? {
        val tempURL = URL(urlStr)
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

    @JvmStatic
    fun pathStr2bitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || UtilKString.isHasSpace(filePathWithName)) null
        else BitmapFactory.decodeFile(filePathWithName)

    /**
     * 删除图片
     * @param filePathWithName String
     */
    @JvmStatic
    fun deleteBitmapFromAlbum(filePathWithName: String) {
        UtilKContentResolver.delete(_context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${MediaStore.Images.Media.DATA}='${filePathWithName}'", null)
    }
}