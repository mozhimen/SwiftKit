package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.basick.lintk.optins.permission.OPermission_WRITE_EXTERNAL_STORAGE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.net.UtilKUri
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapAny_use
import com.mozhimen.basick.utilk.java.net.UtilKHttpURLConnection
import com.mozhimen.basick.utilk.java.net.UtilKHttpURLConnectionWrapper
import com.mozhimen.basick.utilk.java.net.UtilKURI
import com.mozhimen.basick.utilk.java.net.UtilKURL
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrDomain
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrIp
import com.mozhimen.basick.utilk.kotlin.text.matches_ofStrPort
import java.io.File
import java.net.URL

/**
 * @ClassName UtilKStringUrl
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 15:42
 * @Version 1.0
 */
fun String.isStrUrlValid(): Boolean =
    UtilKStrUrl.isStrUrlValid(this)

fun String.isStrUrlConnectable(): Boolean =
    UtilKStrUrl.isStrUrlConnectable(this)

/////////////////////////////////////////////////////////////////////////

@RequiresPermission(CPermission.INTERNET)
@OPermission_INTERNET
fun String.strUrl2bitmapAny(): Bitmap =
    UtilKStrUrl.strUrl2bitmapAny(this)

fun String.strUrl2uri(): Uri =
    UtilKStrUrl.strUrl2uri(this)

fun String.strUrl2uRL(): URL =
    UtilKStrUrl.strUrl2uRL(this)

@OPermission_INTERNET
@OPermission_WRITE_EXTERNAL_STORAGE
@RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
fun String.strUrl2file(strFileNameDest: String, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, strFileNameDest, isAppend)

@OPermission_INTERNET
@OPermission_WRITE_EXTERNAL_STORAGE
@RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
fun String.strUrl2file(fileDest: File, isAppend: Boolean = false): File? =
    UtilKStrUrl.strUrl2file(this, fileDest, isAppend)

/////////////////////////////////////////////////////////////////////////

object UtilKStrUrl : BaseUtilK() {

    //判断url是否合法
    @JvmStatic
    @MainThread
    fun isStrUrlValid(strUrl: String): Boolean {
        UtilKLogWrapper.d(TAG, "isUrlValid: url $strUrl")
        if (strUrl.isEmpty()) {
            "输入为空".showToast()
            return false
        }
        if (!strUrl.startsWith("http://") && !strUrl.startsWith("https://") && !strUrl.startsWith("tcp://")) {
            "请输入正确的协议头".showToast()
            return false
        }
        val splitArray = strUrl.split(":")
        if (splitArray.size < 2) {
            "请输入正确的端口格式".showToast()
            return false
        }
        //first:http
        if (splitArray.getOrNull(0) == null) {
            "请输入正确的端口格式(http/https/tcp)接IP或域名".showToast()
            return false
        }
        val first = splitArray[0]
        if (first != "http" && first != "https" && first != "tcp") {
            "请输入正确的协议(http/https/tcp)".showToast()
            return false
        }
        //second:ip
        if (splitArray.getOrNull(1) == null) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        var second = splitArray[1].replace("//", "")
        if (second.contains("/")) {
            second = second.getSplitFirstIndexToStart("/")
        }
        if (!second.matches_ofStrIp() && !second.matches_ofStrDomain()) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (splitArray.getOrNull(2) != null) {
            var third = splitArray[2]
            if (third.contains("/")) {
                third = third.getSplitFirstIndexToStart("/")
            }
            if (!third.matches_ofStrPort()) {
                "请输入正确的端口".showToast()
                return false
            }
        }
        return true
    }

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
    fun strUrl2uri(strUrl: String): Uri =
        UtilKUri.get(strUrl)

    @JvmStatic
    fun strUrl2uRL(strUrl: String): URL =
        UtilKURL.get(strUrl)

    @JvmStatic
    @OPermission_WRITE_EXTERNAL_STORAGE
    @OPermission_INTERNET
    @RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    fun strUrl2file(strUrl: String, strFileNameDest: String, isAppend: Boolean = false): File? =
        strUrl2file(strUrl, strFileNameDest.strFilePath2file(), isAppend)

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.INTERNET, CPermission.WRITE_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE])
    @OPermission_WRITE_EXTERNAL_STORAGE
    @OPermission_INTERNET
    fun strUrl2file(strUrl: String, fileDest: File, isAppend: Boolean = false): File? =
        UtilKHttpURLConnectionWrapper.getFile_ofStrUrl(strUrl, fileDest, isAppend)
}