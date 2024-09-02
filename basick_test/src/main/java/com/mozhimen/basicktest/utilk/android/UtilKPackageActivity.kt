package com.mozhimen.basicktest.utilk.android

import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.kotlin.utilk.android.content.UtilKApplicationInfo
import com.mozhimen.kotlin.utilk.android.content.UtilKPackage
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName UtilKPackageActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/16
 * @Version 1.0
 */
class UtilKPackageActivity : AppCompatActivity() {
    companion object {
        private const val STR_PACKAGE_NAME = "com.ty.lelejoy"
        private const val TAG = "UtilKPackageActivity>>>>>"
    }

    @OptIn(OPermission_QUERY_ALL_PACKAGES::class)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        UtilKLogWrapper.d(TAG, "onCreate: hasPackage_ofPackageManager_enabled ${UtilKPackage.hasPackage_ofPackageManager_enabled( STR_PACKAGE_NAME,0)}")
        UtilKLogWrapper.d(TAG, "onCreate: hasPackage_ofPackageInfo_enabled ${UtilKPackage.hasPackage_ofPackageInfo_enabled( STR_PACKAGE_NAME,0)}")
        UtilKLogWrapper.d(TAG, "onCreate: hasPackage_ofPackageManager ${UtilKPackage.hasPackage_ofPackageManager( STR_PACKAGE_NAME,0)}")
        UtilKLogWrapper.d(TAG, "onCreate: hasPackage_ofPackageInfo ${UtilKPackage.hasPackage_ofPackageInfo( STR_PACKAGE_NAME,0)}")
        UtilKLogWrapper.d(TAG, "onCreate: hasPackage_ofClazz ${UtilKPackage.hasPackage_ofClazz("$STR_PACKAGE_NAME.MainActivity")}")

        UtilKLogWrapper.d(TAG, "onCreate: ${UtilKApplicationInfo.get(this)}")
        UtilKLogWrapper.d(TAG, "onCreate: ${UtilKApplicationInfo.get_ofPackageInfo(this, this.packageName, 0)}")
        UtilKLogWrapper.d(TAG, "onCreate: ${UtilKApplicationInfo.get_ofPackageManager(this, this.packageName, 0)}")
    }
}