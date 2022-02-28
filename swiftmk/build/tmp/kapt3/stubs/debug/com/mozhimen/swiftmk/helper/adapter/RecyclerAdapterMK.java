package com.mozhimen.swiftmk.helper.adapter;

import java.lang.System;

/**
 * 作用: 通用RecyclerView适配器
 * 用法: viewBinding.mainList.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
 * val adapter: RecyclerAdapterMK<User> = object : RecyclerAdapterMK<User>(viewModel.userList, R.layout.item_user, BR.item) {
 * override fun addListener(view: View, itemData: User, position: Int) {
 *     (view.findViewById(R.id.user_pane) as LinearLayout).setOnClickListener {
 *         //逻辑
 * }}}
 * viewBinding.mainList.adapter=adapter
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\"B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ%\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u0007H\u0016\u00a2\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\u0015\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0018\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0007H\u0016J\u0014\u0010\u001b\u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005J$\u0010\u001d\u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J$\u0010 \u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J$\u0010!\u001a\u00020\u000b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/mozhimen/swiftmk/helper/adapter/RecyclerAdapterMK;", "T", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/mozhimen/swiftmk/helper/adapter/RecyclerAdapterMK$BaseViewHolder;", "itemDatas", "", "defaultLayout", "", "brId", "(Ljava/util/List;II)V", "addListener", "", "view", "Landroid/view/View;", "itemData", "position", "(Landroid/view/View;Ljava/lang/Object;I)V", "getItemCount", "getItemLayout", "(Ljava/lang/Object;)I", "getItemViewType", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onItemDataChanged", "newItemDatas", "onItemRangeChanged", "positionStart", "itemCount", "onItemRangeInserted", "onItemRangeRemoved", "BaseViewHolder", "swiftmk_debug"})
public class RecyclerAdapterMK<T extends java.lang.Object> extends androidx.recyclerview.widget.RecyclerView.Adapter<com.mozhimen.swiftmk.helper.adapter.RecyclerAdapterMK.BaseViewHolder> {
    private java.util.List<? extends T> itemDatas;
    private final int defaultLayout = 0;
    private final int brId = 0;
    
    public RecyclerAdapterMK(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> itemDatas, int defaultLayout, int brId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.mozhimen.swiftmk.helper.adapter.RecyclerAdapterMK.BaseViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.mozhimen.swiftmk.helper.adapter.RecyclerAdapterMK.BaseViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    private final int getItemLayout(T itemData) {
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
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, d2 = {"Lcom/mozhimen/swiftmk/helper/adapter/RecyclerAdapterMK$BaseViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Landroidx/databinding/ViewDataBinding;", "(Landroidx/databinding/ViewDataBinding;)V", "getBinding", "()Landroidx/databinding/ViewDataBinding;", "setBinding", "swiftmk_debug"})
    public static final class BaseViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private androidx.databinding.ViewDataBinding binding;
        
        public BaseViewHolder(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ViewDataBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.databinding.ViewDataBinding getBinding() {
            return null;
        }
        
        public final void setBinding(@org.jetbrains.annotations.NotNull()
        androidx.databinding.ViewDataBinding p0) {
        }
    }
}