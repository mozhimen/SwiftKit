package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName CManifest
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 18:47
 * @Version 1.0
 */
object CManifest {
    /**
     * AndroidManifest.xml sdk>24

    <provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileProvider"
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
     */
    const val PROVIDER = """
            android:name="androidx.core.content.FileProvider"
    android:authorities="${'$'}{applicationId}.fileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"  />
    """

    /**
     * AndroidManifest.xml
    <service
    android:name="service :继承 BaseInstallKSmartService"
    android:enabled="true"
    android:exported="true"
    android:label="@string/installk_label"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
    <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
    android:name="android.accessibilityservice"
    android:resource="@xml/installk_smart_accessibility_service_config" />
    </service>

     * strings.xml
    <string name="install_label">InstallK程序更新</string>
    <string name="installk_auto_accessibility_service_description">应用自动安装服务</string>

     * installk_smart_accessibility_service_config.xml
    <accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"
    android:description="@string/installk_auto_accessibility_service_description"/>
     */
    const val SERVICE_ACCESSIBILITY = """
        android:name="com.mozhimen.componentk.installk.bases.BaseInstallKSmartService"
android:enabled="true"
android:exported="true"
android:label="@string/installk_label"
android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
<intent-filter>
<action android:name="android.accessibilityservice.AccessibilityService" />
</intent-filter>
<meta-data
android:name="android.accessibilityservice"
android:resource="@xml/installk_smart_accessibility_service_config" />
    """
}