package com.mozhimen.swiftmk.helper.permission;

import java.lang.System;

/**
 * @ClassName PermissionApplier
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:08
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\'\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\n\"\u00020\u0004\u00a2\u0006\u0002\u0010\u000bJE\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u000e2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\"\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0011\u0012\u0004\u0012\u00020\r0\u0010j\u0002`\u0012\u00a2\u0006\u0002\u0010\u0013JK\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00152\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\n\"\u00020\u00042\"\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0011\u0012\u0004\u0012\u00020\r0\u0010j\u0002`\u0012\u00a2\u0006\u0002\u0010\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/mozhimen/swiftmk/helper/permission/PermissionApplier;", "", "()V", "TAG", "", "checkPermissions", "", "activity", "Landroid/app/Activity;", "permissions", "", "(Landroid/app/Activity;[Ljava/lang/String;)Z", "initPermissions", "", "Landroidx/appcompat/app/AppCompatActivity;", "callback", "Lkotlin/Function2;", "", "Lcom/mozhimen/swiftmk/helper/permission/PermissionCallback;", "(Landroidx/appcompat/app/AppCompatActivity;[Ljava/lang/String;Lkotlin/jvm/functions/Function2;)V", "requestPermissions", "Landroidx/fragment/app/FragmentActivity;", "(Landroidx/fragment/app/FragmentActivity;[Ljava/lang/String;Lkotlin/jvm/functions/Function2;)V", "swiftmk_debug"})
public final class PermissionApplier {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.permission.PermissionApplier INSTANCE = null;
    private static final java.lang.String TAG = "PermissionApplier";
    
    private PermissionApplier() {
        super();
    }
    
    /**
     * 作用: 权限申请
     */
    public final void initPermissions(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.app.AppCompatActivity activity, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.util.List<java.lang.String>, kotlin.Unit> callback) {
    }
    
    /**
     * 作用: 批量申请动态权限
     * 用法: PermissionApplier.requestPermissions(this,Manifest.permission.CALL_PHONE,
     * ...){ allGranted,deniedList ->
     *    if(allGranted){ ... }
     *    else { ... }
     * }
     */
    public final void requestPermissions(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.FragmentActivity activity, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.util.List<java.lang.String>, kotlin.Unit> callback) {
    }
    
    /**
     * 作用: 权限检查
     */
    public final boolean checkPermissions(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    java.lang.String... permissions) {
        return false;
    }
}