package com.mozhimen.swiftmk.base;

import java.lang.System;

/**
 * @ClassName BasePreferences
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 18:56
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003J\u0006\u0010\f\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003J\u0010\u0010\u000e\u001a\f\u0012\u0004\u0012\u00020\u0003\u0012\u0002\b\u00030\u000fJ\u0016\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\nJ\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0015J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0003J\u0016\u0010\u0019\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\nJ\u0016\u0010\u001b\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0013J\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0015J\u0016\u0010\u001d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0017J\u0016\u0010\u001e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0003R\u0010\u0010\u0007\u001a\u00020\b8\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/mozhimen/swiftmk/base/BasePreferences;", "", "preferencesName", "", "application", "Lcom/mozhimen/swiftmk/base/BaseApplication;", "(Ljava/lang/String;Lcom/mozhimen/swiftmk/base/BaseApplication;)V", "preferences", "Landroid/content/SharedPreferences;", "clear", "", "entry", "clearAll", "contains", "getAll", "", "getBoolean", "defaultValue", "getFloat", "", "getInt", "", "getLong", "", "getString", "setBoolean", "value", "setFloat", "setInt", "setLong", "setString", "swiftmk_debug"})
public class BasePreferences {
    @android.annotation.SuppressLint(value = {"SharedPreferences"})
    private final android.content.SharedPreferences preferences = null;
    
    public BasePreferences(@org.jetbrains.annotations.NotNull()
    java.lang.String preferencesName, @org.jetbrains.annotations.NotNull()
    com.mozhimen.swiftmk.base.BaseApplication application) {
        super();
    }
    
    public final boolean setString(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return false;
    }
    
    public final boolean setBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, boolean value) {
        return false;
    }
    
    public final boolean setInt(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, int value) {
        return false;
    }
    
    public final boolean setLong(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, long value) {
        return false;
    }
    
    public final boolean setFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, float value) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, ?> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getString(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, @org.jetbrains.annotations.NotNull()
    java.lang.String defaultValue) {
        return null;
    }
    
    public final int getInt(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, int defaultValue) {
        return 0;
    }
    
    public final float getFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, float defaultValue) {
        return 0.0F;
    }
    
    public final long getLong(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, long defaultValue) {
        return 0L;
    }
    
    public final boolean getBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String entry, boolean defaultValue) {
        return false;
    }
    
    public final boolean contains(@org.jetbrains.annotations.NotNull()
    java.lang.String entry) {
        return false;
    }
    
    public final boolean clear(@org.jetbrains.annotations.NotNull()
    java.lang.String entry) {
        return false;
    }
    
    public final boolean clearAll() {
        return false;
    }
}