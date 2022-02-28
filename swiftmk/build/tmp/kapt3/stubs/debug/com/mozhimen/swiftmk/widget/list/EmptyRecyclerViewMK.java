package com.mozhimen.swiftmk.widget.list;

import java.lang.System;

/**
 * @ClassName EmptyRecyclerView
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/28 16:29
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0016\u0010\u0011\u001a\u00020\u00102\f\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\fR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/mozhimen/swiftmk/widget/list/EmptyRecyclerViewMK;", "Landroidx/recyclerview/widget/RecyclerView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "emptyView", "Landroid/view/View;", "observer", "Landroidx/recyclerview/widget/RecyclerView$AdapterDataObserver;", "checkIfEmpty", "", "setAdapter", "adapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "setEmptyView", "Companion", "swiftmk_debug"})
public final class EmptyRecyclerViewMK extends androidx.recyclerview.widget.RecyclerView {
    private android.view.View emptyView;
    private final androidx.recyclerview.widget.RecyclerView.AdapterDataObserver observer = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.widget.list.EmptyRecyclerViewMK.Companion Companion = null;
    private static final java.lang.String mTag = "EmptyRecyclerView";
    
    public EmptyRecyclerViewMK(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    public EmptyRecyclerViewMK(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public EmptyRecyclerViewMK(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyle) {
        super(null);
    }
    
    private final void checkIfEmpty() {
    }
    
    @java.lang.Override()
    public void setAdapter(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.RecyclerView.Adapter<?> adapter) {
    }
    
    /**
     * 作用: 设置空布局
     * 用法:  emptyView=findViewById(R.id.item_empty)
     * vb.recycler.setEmptyView(emptyView)
     */
    public final void setEmptyView(@org.jetbrains.annotations.Nullable()
    android.view.View emptyView) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/mozhimen/swiftmk/widget/list/EmptyRecyclerViewMK$Companion;", "", "()V", "mTag", "", "swiftmk_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}