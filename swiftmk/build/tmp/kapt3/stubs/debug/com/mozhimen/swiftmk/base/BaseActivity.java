package com.mozhimen.swiftmk.base;

import java.lang.System;

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:03
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nH\u0016\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH&J\u0012\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\bH&J\u0012\u0010\u0013\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0014\u001a\u00020\bH\u0014R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0015"}, d2 = {"Lcom/mozhimen/swiftmk/base/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "applySetts", "", "getPermissions", "", "()[Ljava/lang/String;", "getViewBinding", "Landroidx/viewbinding/ViewBinding;", "initData", "savedInstanceState", "Landroid/os/Bundle;", "initFlag", "initView", "onCreate", "onDestroy", "swiftmk_debug"})
public abstract class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    
    /**
     * 作用: 打印日志
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = null;
    
    public BaseActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTAG() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * 设置申请权限
     */
    private final void applySetts() {
    }
    
    /**
     * 作用: 回调ViewBinding
     * 用法: private lateinit var viewBinding: Activity???Binding(申明)
     * override fun getViewBinding(): ViewBinding {
     *  viewBinding = Activity???Binding.inflate(layoutInflater)
     *  return viewBinding}
     */
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.viewbinding.ViewBinding getViewBinding();
    
    /**
     * 作用: 回调权限数组
     * 用法: override fun getPermissions(): Array<String> = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,...)
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String[] getPermissions() {
        return null;
    }
    
    /**
     * 作用: 特殊标志声明
     */
    public void initFlag() {
    }
    
    /**
     * 作用: 初始化数据
     */
    public void initData(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * 作用: 初始化界面
     */
    public abstract void initView();
    
    /**
     * 作用: 销毁Activity
     */
    @java.lang.Override()
    protected void onDestroy() {
    }
}