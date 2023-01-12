package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName CManifest
 * @Description

 * AndroidManifest.xml (example)

<provider
android:name="androidx.core.content.FileProvider"
android:authorities="包名.fileProvider"
android:exported="false"
android:grantUriPermissions="true">
<meta-data
android:name="android.support.FILE_PROVIDER_PATHS"
android:resource="@xml/file_paths"  />
</provider>

 * file_paths.xml sdk>24
<paths>
<files-path
name="files-path"
path="." />
</paths>

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 18:47
 * @Version 1.0
 */
object CManifest {
    const val PROVIDER = """
            android:name="androidx.core.content.FileProvider"
    android:authorities="包名.fileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"  />
    """
}