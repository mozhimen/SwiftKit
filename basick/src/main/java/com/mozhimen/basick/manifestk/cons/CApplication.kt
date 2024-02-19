package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName ApplicationConfig
 * @Description

 * AndroidManifest.xml (example)

<application
android:usesCleartextTraffic="true"
tools:targetApi="m" />

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 17:19
 * @Version 1.0
 */
object CApplication {
    const val USES_CLEAR_TEXT_TRAFFIC = "android:usesCleartextTraffic=\"true\""
    const val REQUEST_LEGACY_EXTERNAL_STORAGE = "android:requestLegacyExternalStorage=\"true\""
    const val PRESERVE_LEGACY_EXTERNAL_STORAGE = "android:preserveLegacyExternalStorage=\"true\""
}