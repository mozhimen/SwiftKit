package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall

/**
 * @ClassName UtilKAppInstallActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/17 19:46
 * @Version 1.0
 */
class UtilKAppInstallActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        UtilKAppInstall.installSilence()
    }
}