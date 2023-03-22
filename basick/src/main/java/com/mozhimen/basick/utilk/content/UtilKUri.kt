package com.mozhimen.basick.utilk.content

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import java.io.File


/**
 * @ClassName UtilKFileUri
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 18:58
 * @Version 1.0
 */
object UtilKUri {
    private val TAG = "UtilKFileUri>>>>>"
    private val _context by lazy { UtilKApplication.instance.get() }

    /**
     * 获取PackageUri
     * @param context Context
     * @return Uri
     */
    @JvmStatic
    fun getPackageUri(context: Context): Uri =
        Uri.parse("package:${UtilKContext.getPackageName(context)}")

    /**
     * 获取PackageUri
     * @param context Context
     * @return Uri
     */
    @JvmStatic
    fun getPackageUri2(context: Context): Uri =
        Uri.fromParts("package", UtilKContext.getPackageName(context), null)


    /**
     * 文件转Uri
     * @param filePathWithName String
     * @return Uri
     */
    @JvmStatic
    fun filePathStr2Uri(filePathWithName: String): Uri? {
        if (filePathWithName.isEmpty()) {
            Log.e(TAG, "file2Uri: isEmpty true")
            return null
        }
        return file2Uri(File(filePathWithName))
    }

    /**
     * @param file File
     * @return Uri?
     */
    @JvmStatic
    @ADescription(
        Intent.FLAG_GRANT_READ_URI_PERMISSION.toString(),
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION.toString()
    )
    fun file2Uri(file: File): Uri? {
        if (!UtilKFile.isFileExist(file)) {
            Log.e(TAG, "file2Uri: file isFileExist false")
            return null
        }
        return if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {
            val authority = "${UtilKContext.getPackageName(_context)}.fileProvider"
            Log.d(TAG, "file2Uri: authority $authority")
            FileProvider.getUriForFile(_context, authority, file).also {
                UtilKContext.grantUriPermission(_context, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else Uri.fromFile(file)
    }
}
