package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.UtilKApk
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.basick.utilk.java.io.UtilKFileFormat
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basicktest.databinding.ActivityUtilkApkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName UtilKApkActivity
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 13:44
 * @Version 1.0
 */
class UtilKApkActivity : BaseActivityVB<ActivityUtilkApkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            val apkPathWithName = UtilKStrPath.Absolute.Internal.getCacheDir() + "/temp/${UtilKFile.getStrFileNameForStrNowDate()}.apk"
            if (!UtilKFile.isFileExist(apkPathWithName)) {
                UtilKAsset.asset2file("basicktest-debug.apk", apkPathWithName)
            }
            UtilKApk.printApkInfo(apkPathWithName)
        }
    }
}