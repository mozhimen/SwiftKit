package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.UtilKApk
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.kotlin.UtilKStrAsset
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.isFileExist
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
            val strPathNameApk = UtilKStrPath.Absolute.Internal.getCache() + "/temp/${UtilKFile.getStrFileNameForStrNowDate()}.apk"
            if (!strPathNameApk.isFileExist()) {
                UtilKStrAsset.strAssetName2file("basicktest-debug.apk", strPathNameApk)
            }
            UtilKApk.printApkInfo(strPathNameApk)
        }
    }
}