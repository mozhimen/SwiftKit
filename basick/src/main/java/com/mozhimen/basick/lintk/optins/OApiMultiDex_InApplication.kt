package com.mozhimen.basick.lintk.optins

/**
 * @ClassName AOptLazyInit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/7 14:43
 * @Version 1.0
 */
/**
 * A lint k opt in_api multi dex_in application
 *
 * ```
 * defaultConfig {
 *     applicationId "com.xxx.xx"
 *     minSdkVersion x
 *     targetSdkVersion x
 *     versionCode x
 *     versionName xxx
 *     multiDexEnabled true
 *     testInstrumentationRunner xxx
 * }
 *
 * dexOptions {
 *     javaMaxHeapSize "4g"
 *     jumboMode = true
 * }
 *
 * @Override
 * protected void onCreate(Context context) {
 *     super.onCreate(base);
 *     MultiDex.install(base);
 * }
 * ```
 * @constructor Create empty A lint k opt in_api multi dex_in application
 */
@RequiresOptIn("The api need write multiDex in application, gradle < dexOptions > and < defaultConfig >. 需要在gradle中声明分包的API", RequiresOptIn.Level.WARNING)
annotation class OApiMultiDex_InApplication
