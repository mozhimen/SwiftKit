package com.mozhimen.swiftmk.utils.image;

import java.lang.System;

/**
 * @ClassName BlurBitmap
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:23
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/mozhimen/swiftmk/utils/image/BlurBitmap;", "", "()V", "BITMAP_SCALE", "", "BLUR_RADIUS", "blur", "Landroid/graphics/Bitmap;", "context", "Landroid/content/Context;", "bitmap", "swiftmk_debug"})
public final class BlurBitmap {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.image.BlurBitmap INSTANCE = null;
    private static final float BITMAP_SCALE = 0.4F;
    private static final float BLUR_RADIUS = 25.0F;
    
    private BlurBitmap() {
        super();
    }
    
    /**
     * 模糊图片
     */
    @org.jetbrains.annotations.Nullable()
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    public final android.graphics.Bitmap blur(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap) {
        return null;
    }
}