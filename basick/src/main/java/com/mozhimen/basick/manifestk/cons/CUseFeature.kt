package com.mozhimen.basick.manifestk.cons


/**
 * @ClassName UseFeature
 * @Description

 * AndroidManifest.xml (example)

<uses-feature android:name="android.hardware.camera" />

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 16:48
 * @Version 1.0
 */
object CUseFeature {
    /**
     *     <uses-feature
     *         android:name="android.hardware.camera"
     *         android:required="true" />
     */
    const val HARDWARE_CAMERA = "android.hardware.camera"

    /**
    <uses-feature android:name="android.hardware.camera.autofocus" />
     *
     */
    const val HARDWARE_CAMERA_AUTOFOCUS = "android.hardware.camera.autofocus"

    const val HARDWARE_CAMERA_ANY = "android.hardware.camera.any"
}