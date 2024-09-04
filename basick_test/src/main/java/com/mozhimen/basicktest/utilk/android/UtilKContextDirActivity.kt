package com.mozhimen.basicktest.utilk.android

import com.mozhimen.kotlin.elemk.android.cons.CPermission
import com.mozhimen.manifestk.permission.ManifestKPermission
import com.mozhimen.manifestk.permission.annors.APermissionCheck
import com.mozhimen.kotlin.utilk.android.app.UtilKActivityStart
import com.mozhimen.kotlin.utilk.android.content.UtilKContextDir
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.i
import com.mozhimen.basicktest.databinding.ActivityUtilkContextDirBinding
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB

/**
 * @ClassName UtilKContextDirActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/17 16:24
 * @Version 1.0
 */
@APermissionCheck(CPermission.READ_EXTERNAL_STORAGE, CPermission.READ_EXTERNAL_STORAGE)
class UtilKContextDirActivity : BaseActivityVDB<ActivityUtilkContextDirBinding>() {
    override fun initData(savedInstanceState: android.os.Bundle?) {
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKActivityStart.startSettingApplicationDetailsSettings(this)
            }
        }
    }

    override fun initView(savedInstanceState: android.os.Bundle?) {
        UtilKContextDir.Internal.getCacheDir(this).absolutePath.i()///data/user/0/com.mozhimen.basicktest/cache
        UtilKContextDir.Internal.getFilesDir(this).absolutePath.i()///data/user/0/com.mozhimen.basicktest/files
        if (UtilKBuildVersion.isAfterV_24_7_N()) {
            UtilKContextDir.Internal.getDataDir(this).absolutePath.i()///data/user/0/com.mozhimen.basicktest
        }
        UtilKContextDir.Internal.getObbDir(this).absolutePath.i()///storage/emulated/0/Android/obb/com.mozhimen.basicktest
        UtilKContextDir.Internal.getObbDirs(this)?.asList()?.joinToString { it.absolutePath }?.i()///storage/emulated/0/Android/obb/com.mozhimen.basicktest
        UtilKContextDir.Internal.getCodeCacheDir(this).absolutePath.i()///data/user/0/com.mozhimen.basicktest/code_cache
        UtilKContextDir.Internal.getNoBackupFilesDir(this).absolutePath.i()///data/user/0/com.mozhimen.basicktest/no_backup
        UtilKContextDir.Internal.getPackageCodeDir(this).i()///data/app/com.mozhimen.basicktest-2/base.apk
        UtilKContextDir.Internal.getPackageResourceDir(this).i()///data/app/com.mozhimen.basicktest-2/base.apk

        UtilKContextDir.External.getCacheDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/cache
        UtilKContextDir.External.getFilesDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files
        UtilKContextDir.External.getFilesAlarmsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Alarms
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            UtilKContextDir.External.getFilesAudiobooksDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Audiobooks
        }
        UtilKContextDir.External.getFilesDCIMDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/DCIM
        UtilKContextDir.External.getFilesDocumentsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Documents
        UtilKContextDir.External.getFilesDownloadsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Download
        UtilKContextDir.External.getFilesMoviesDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Movies
        UtilKContextDir.External.getFilesMusicDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Music
        UtilKContextDir.External.getFilesNotificationsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Notifications
        UtilKContextDir.External.getFilesPicturesDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Pictures
        UtilKContextDir.External.getFilesRingtonesDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Ringtones
        if (UtilKBuildVersion.isAfterV_31_12_S()) {
            UtilKContextDir.External.getFilesRecordingsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Recordings
        }
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            UtilKContextDir.External.getFilesScreenshotsDir(this)?.absolutePath?.i()///storage/emulated/0/Android/data/com.mozhimen.basicktest/files/Recordings
        }
    }
}