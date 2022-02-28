package com.mozhimen.swiftmk.helper.storage;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 2, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a#\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004\u00a2\u0006\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"open", "", "Landroid/content/SharedPreferences;", "block", "Lkotlin/Function1;", "Landroid/content/SharedPreferences$Editor;", "Lkotlin/ExtensionFunctionType;", "swiftmk_debug"})
public final class SharedPreferencesMKKt {
    
    /**
     * 作用: 简化SharePreferences写法
     * 用法:
     * getSharedPreferences("data", Context.MODE_PRIVATE).open {
     * putString("name","Tom")
     * ...
     * }
     */
    public static final void open(@org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences $this$open, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.content.SharedPreferences.Editor, kotlin.Unit> block) {
    }
}