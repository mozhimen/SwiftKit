package com.mozhimen.swiftmk.helper.databinding;

import java.lang.System;

/**
 * @ClassName DataBindingAdapter
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 15:35
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2 = {"Lcom/mozhimen/swiftmk/helper/databinding/DataBindingAdapter;", "", "()V", "loadImage", "", "view", "Landroid/widget/ImageView;", "url", "", "setWidthHeightRatio", "Landroid/view/View;", "ratio", "", "swiftmk_debug"})
public final class DataBindingAdapter {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.helper.databinding.DataBindingAdapter INSTANCE = null;
    
    private DataBindingAdapter() {
        super();
    }
    
    @androidx.databinding.BindingAdapter(value = {"widthHeightRatio"})
    @kotlin.jvm.JvmStatic()
    public static final void setWidthHeightRatio(@org.jetbrains.annotations.NotNull()
    android.view.View view, float ratio) {
    }
    
    @kotlin.jvm.JvmStatic()
    @androidx.databinding.BindingAdapter(value = {"imageUrl"})
    public static final void loadImage(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView view, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
}