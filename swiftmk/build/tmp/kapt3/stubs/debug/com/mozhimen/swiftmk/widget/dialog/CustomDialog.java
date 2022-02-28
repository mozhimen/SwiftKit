package com.mozhimen.swiftmk.widget.dialog;

import java.lang.System;

/**
 * 作用: 自定义弹框
 * 用法: val builder = CustomDialog.Builder(this)
 * builder.setPositiveButton(DialogInterface.OnClickListener { dialogInterface, _ ->
 * dialogInterface.dismiss()
 * //具体逻辑
 * })
 * builder.setNegativeButton(DialogInterface.OnClickListener { dialogInterface, _ ->
 * dialogInterface.dismiss()
 * //具体逻辑
 * })
 * builder.create().show()
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\bB\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\t"}, d2 = {"Lcom/mozhimen/swiftmk/widget/dialog/CustomDialog;", "Landroid/app/Dialog;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "theme", "", "(Landroid/content/Context;I)V", "Builder", "swiftmk_debug"})
public final class CustomDialog extends android.app.Dialog {
    
    public CustomDialog(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    public CustomDialog(@org.jetbrains.annotations.Nullable()
    android.content.Context context, int theme) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0005J\u0018\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\tJ\u0016\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0005J\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\tR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/mozhimen/swiftmk/widget/dialog/CustomDialog$Builder;", "", "context", "Landroid/content/Context;", "theme", "", "layout", "(Landroid/content/Context;II)V", "content", "", "negativeButtonClickListener", "Landroid/content/DialogInterface$OnClickListener;", "negativeButtonText", "positiveButtonClickListener", "positiveButtonText", "create", "Lcom/mozhimen/swiftmk/widget/dialog/CustomDialog;", "setContent", "resId", "setNegativeButton", "listener", "setPositiveButton", "swiftmk_debug"})
    public static final class Builder {
        private final android.content.Context context = null;
        private final int theme = 0;
        private final int layout = 0;
        private java.lang.String content = "\u786e\u5b9a\u5417";
        private java.lang.String positiveButtonText = "\u786e\u5b9a";
        private java.lang.String negativeButtonText = "\u53d6\u6d88";
        private android.content.DialogInterface.OnClickListener positiveButtonClickListener;
        private android.content.DialogInterface.OnClickListener negativeButtonClickListener;
        
        public Builder(@org.jetbrains.annotations.NotNull()
        android.content.Context context, int theme, int layout) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setContent(@org.jetbrains.annotations.NotNull()
        java.lang.String content) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setContent(int resId) {
            return null;
        }
        
        /**
         * 按钮部分
         */
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setPositiveButton(@org.jetbrains.annotations.NotNull()
        android.content.DialogInterface.OnClickListener listener, @org.jetbrains.annotations.NotNull()
        java.lang.String positiveButtonText) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setPositiveButton(@org.jetbrains.annotations.NotNull()
        android.content.DialogInterface.OnClickListener listener, int resId) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setNegativeButton(@org.jetbrains.annotations.NotNull()
        android.content.DialogInterface.OnClickListener listener, @org.jetbrains.annotations.NotNull()
        java.lang.String negativeButtonText) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog.Builder setNegativeButton(@org.jetbrains.annotations.NotNull()
        android.content.DialogInterface.OnClickListener listener, int resId) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.mozhimen.swiftmk.widget.dialog.CustomDialog create() {
            return null;
        }
    }
}