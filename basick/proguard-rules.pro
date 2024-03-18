# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-dontshrink 关闭压缩
#-dontoptimize  关闭优化
#-optimizationpasses n 表示proguard对代码进行迭代优化的次数，Android一般为5
#-dontobfuscate 关闭混淆

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keep class * implements android.os.Parcelable { # 保持Parcelable不被混淆
    public static final android.os.Parcelable$Creator *;
}

#使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，见第二条规则
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#发布一款应用除了设minifyEnabled为ture，你也应该设置zipAlignEnabled为true，像Google Play强制要求开发者上传的应用必须是经过zipAlign的，zipAlign可以让安装包中的资源按4字节对齐，这样可以减少应用在运行时的内存消耗。
#android.enableR8=false
#android.enableR8.libraries=false