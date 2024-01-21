package com.mozhimen.basicktest.utilk.android

import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.widget.showToast

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

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d(TAG, "onCreate: isPackageInstalled ${UtilKPackage.isPackageInstalled(this,STR_PACKAGE_NAME)}")
        android.util.Log.d(TAG, "onCreate: hasPackageOfQuery ${UtilKPackage.hasPackageOfQuery(this,STR_PACKAGE_NAME)}")
        android.util.Log.d(TAG, "onCreate: hasPackage ${UtilKPackage.hasPackage(this,STR_PACKAGE_NAME)}")
        android.util.Log.d(TAG, "onCreate: hasPackageOfClazz ${UtilKPackage.hasPackageOfClazz("$STR_PACKAGE_NAME.MainActivity")}")
        "${UtilKPackage.isPackageInstalled(this,STR_PACKAGE_NAME)} ${UtilKPackage.hasPackageOfQuery(this,STR_PACKAGE_NAME)} ${UtilKPackage.hasPackage(this,STR_PACKAGE_NAME)} ${UtilKPackage.hasPackageOfClazz("$STR_PACKAGE_NAME.MainActivity")}".showToast()
    }
}