package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkStrPathBinding
import com.mozhimen.kotlin.utilk.android.util.i
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrFile
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrPath
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB

class UtilKStrPathActivity : BaseActivityVDB<ActivityUtilkStrPathBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        UtilKStrPath.Absolute.Internal.getCache().i()///data/user/0/com.mozhimen.basicktest/cache
        UtilKStrPath.Absolute.Internal.getFiles().i()//data/user/0/com.mozhimen.basicktest/files
        UtilKStrPath.Absolute.Internal.getData().i()///data/user/0/com.mozhimen.basicktest
        UtilKStrPath.Absolute.Internal.getObb().i()///storage/emulated/0/Android/obb/com.mozhimen.basicktest
        UtilKStrPath.Absolute.Internal.getCodeCache().i()///data/user/0/com.mozhimen.basicktest/code_cache
        UtilKStrPath.Absolute.Internal.getNoBackupFiles().i()///data/user/0/com.mozhimen.basicktest/no_backup
        UtilKStrPath.Absolute.Internal.getPackageCode().i()//data/app/~~9qqDETIUmUQ3aljFWIZR7Q==/com.mozhimen.basicktest-eR1ICkLMwvv_-10wBAIpMA==/base.apk
        UtilKStrPath.Absolute.Internal.getPackageResource().i()///data/app/~~9qqDETIUmUQ3aljFWIZR7Q==/com.mozhimen.basicktest-eR1ICkLMwvv_-10wBAIpMA==/base.apk
        UtilKStrPath.Absolute.Internal.getPatch().i()///data/user/0/com.mozhimen.basicktest/app_patch
        UtilKStrPath.Absolute.Internal.getDex2opt().i()///data/user/0/com.mozhimen.basicktest/app_dex2opt

        UtilKStrPath.Absolute.External.getCache()?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/cache
        UtilKStrPath.Absolute.External.getFiles().i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files
        UtilKStrPath.Absolute.External.getEnvStorage().i()///storage/emulated/0
        UtilKStrPath.Absolute.External.getEnvData().i()///data
        UtilKStrPath.Absolute.External.getEnvStoragePublicDCIM().i()///storage/emulated/0/DCIM
        UtilKStrPath.Absolute.External.getEnvStoragePublicPictures().i()///storage/emulated/0/Pictures
    }
}