package com.mozhimen.swiftmk.helper.mvvm;

import java.lang.System;

/**
 * @ClassName ObservableLiveDataField
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/29 18:23
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001a*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u001aB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0002J\r\u0010\f\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ \u0010\u0011\u001a\u00020\u000b2\b\b\u0001\u0010\u0012\u001a\u00020\u00132\u000e\b\u0001\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015J\u0018\u0010\u0016\u001a\u00020\u000b2\u000e\b\u0001\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0007J\u0013\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0004J\u0015\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0004R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/mozhimen/swiftmk/helper/mvvm/ObservableLiveDataField;", "T", "Landroidx/databinding/ObservableField;", "value", "(Ljava/lang/Object;)V", "()V", "mVersion", "", "nMutableLiveData", "Landroidx/lifecycle/MutableLiveData;", "activeMutableLiveData", "", "getValue", "()Ljava/lang/Object;", "hasActiveObservers", "", "hasObservers", "observe", "owner", "Landroidx/lifecycle/LifecycleOwner;", "observer", "Landroidx/lifecycle/Observer;", "observeForever", "postValue", "set", "setValue", "Companion", "swiftmk_debug"})
public final class ObservableLiveDataField<T extends java.lang.Object> extends androidx.databinding.ObservableField<T> {
    private androidx.lifecycle.MutableLiveData<T> nMutableLiveData;
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.mvvm.ObservableLiveDataField.Companion Companion = null;
    public static final int START_VERSION = -1;
    private int mVersion = -1;
    
    public ObservableLiveDataField() {
        super(null);
    }
    
    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public ObservableLiveDataField(T value) {
        super(null);
    }
    
    public final void setValue(T value) {
    }
    
    /**
     * 可在子线程调用
     */
    public final void postValue(T value) {
    }
    
    @java.lang.Override()
    public void set(T value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final T getValue() {
        return null;
    }
    
    public final void observe(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    androidx.lifecycle.LifecycleOwner owner, @org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    androidx.lifecycle.Observer<T> observer) {
    }
    
    @androidx.annotation.MainThread()
    public final void observeForever(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    androidx.lifecycle.Observer<T> observer) {
    }
    
    public final boolean hasActiveObservers() {
        return false;
    }
    
    public final boolean hasObservers() {
        return false;
    }
    
    private final void activeMutableLiveData() {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/mozhimen/swiftmk/helper/mvvm/ObservableLiveDataField$Companion;", "", "()V", "START_VERSION", "", "swiftmk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}