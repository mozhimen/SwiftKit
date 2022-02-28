package com.mozhimen.swiftmk.helper.activity;

import java.lang.System;

/**
 * @ClassName ActivityCollector
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:04
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/mozhimen/swiftmk/helper/activity/ActivityCollector;", "", "()V", "activities", "Ljava/util/ArrayList;", "Landroid/app/Activity;", "addActivity", "", "activity", "finishAll", "removeActivity", "swiftmk_debug"})
public final class ActivityCollector {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.activity.ActivityCollector INSTANCE = null;
    private static final java.util.ArrayList<android.app.Activity> activities = null;
    
    private ActivityCollector() {
        super();
    }
    
    public final void addActivity(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void removeActivity(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 作用: 销毁所有的Activity
     * 用法: ActivityCollector.finishAll()
     * 进程销毁:android.os.Process.killProcess(android.os.Process.myPid())
     */
    public final void finishAll() {
    }
}