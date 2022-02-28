package com.mozhimen.swiftmk.helper.adapter;

import java.lang.System;

/**
 * 作用: 带Header的RecyclerView适配器
 * 用法: viewBinding.mainList.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
 * val adapter: RecyclerAdapterMK<User> = object : RecyclerAdapterMK<User>(viewModel.userList, R.layout.item_user, BR.item) {
 * override fun addListener(view: View, itemData: User, position: Int) {
 *     (view.findViewById(R.id.user_pane) as LinearLayout).setOnClickListener {
 *         //逻辑
 * }}}
 * viewBinding.mainList.adapter=adapter
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001%B7\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ%\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\u0007H\u0016\u00a2\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0016J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0007H\u0016J\u0018\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0016J\u0014\u0010\u001e\u001a\u00020\u00102\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005J$\u0010 \u001a\u00020\u00102\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0007J$\u0010#\u001a\u00020\u00102\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0007J$\u0010$\u001a\u00020\u00102\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0007R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\fR\u0012\u0010\b\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/mozhimen/swiftmk/helper/adapter/StuffedRecyclerAdapterMK;", "T", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/mozhimen/swiftmk/helper/adapter/StuffedRecyclerAdapterMK$BaseViewHolder;", "itemDatas", "", "defaultLayout", "", "headerLayout", "footerLayout", "brId", "(Ljava/util/List;ILjava/lang/Integer;Ljava/lang/Integer;I)V", "Ljava/lang/Integer;", "mTag", "", "addListener", "", "view", "Landroid/view/View;", "itemData", "position", "(Landroid/view/View;Ljava/lang/Object;I)V", "getItemCount", "getItemViewType", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onItemDataChanged", "newItemDatas", "onItemRangeChanged", "positionStart", "itemCount", "onItemRangeInserted", "onItemRangeRemoved", "BaseViewHolder", "swiftmk_debug"})
public class StuffedRecyclerAdapterMK<T extends java.lang.Object> extends androidx.recyclerview.widget.RecyclerView.Adapter<com.mozhimen.swiftmk.helper.adapter.StuffedRecyclerAdapterMK.BaseViewHolder> {
    private java.util.List<? extends T> itemDatas;
    private int defaultLayout;
    private java.lang.Integer headerLayout;
    private java.lang.Integer footerLayout;
    private int brId;
    private final java.lang.String mTag = "StuffedRecyclerAdapterMK";
    
    public StuffedRecyclerAdapterMK(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> itemDatas, int defaultLayout, @org.jetbrains.annotations.Nullable()
    java.lang.Integer headerLayout, @org.jetbrains.annotations.Nullable()
    java.lang.Integer footerLayout, int brId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.mozhimen.swiftmk.helper.adapter.StuffedRecyclerAdapterMK.BaseViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.mozhimen.swiftmk.helper.adapter.StuffedRecyclerAdapterMK.BaseViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    public void addListener(@org.jetbrains.annotations.NotNull()
    android.view.View view, T itemData, int position) {
    }
    
    public final void onItemDataChanged(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> newItemDatas) {
    }
    
    public final void onItemRangeChanged(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> newItemDatas, int positionStart, int itemCount) {
    }
    
    public final void onItemRangeInserted(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> newItemDatas, int positionStart, int itemCount) {
    }
    
    public final void onItemRangeRemoved(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> newItemDatas, int positionStart, int itemCount) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\u0007\u00a8\u0006\f"}, d2 = {"Lcom/mozhimen/swiftmk/helper/adapter/StuffedRecyclerAdapterMK$BaseViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "binding", "Landroidx/databinding/ViewDataBinding;", "(Landroidx/databinding/ViewDataBinding;)V", "mBinding", "getMBinding", "()Landroidx/databinding/ViewDataBinding;", "setMBinding", "swiftmk_debug"})
    public static final class BaseViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.Nullable()
        private androidx.databinding.ViewDataBinding mBinding;
        
        @org.jetbrains.annotations.Nullable()
        public final androidx.databinding.ViewDataBinding getMBinding() {
            return null;
        }
        
        public final void setMBinding(@org.jetbrains.annotations.Nullable()
        androidx.databinding.ViewDataBinding p0) {
        }
        
        public BaseViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public BaseViewHolder(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ViewDataBinding binding) {
            super(null);
        }
    }
}