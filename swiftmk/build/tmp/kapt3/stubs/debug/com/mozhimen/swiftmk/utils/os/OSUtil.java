package com.mozhimen.swiftmk.utils.os;

import java.lang.System;

/**
 * @ClassName OSUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:12
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004H\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u0004H\u0002J\u0006\u0010\u0018\u001a\u00020\u0014J\u0006\u0010\u0019\u001a\u00020\u0014J\u0006\u0010\u001a\u001a\u00020\u0014J\u0006\u0010\u001b\u001a\u00020\u0014J\u0006\u0010\u001c\u001a\u00020\u0014J\u0006\u0010\u001d\u001a\u00020\u0014J\u0006\u0010\u001e\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/mozhimen/swiftmk/utils/os/OSUtil;", "", "()V", "KEY_VERSION_EMUI", "", "KEY_VERSION_MIUI", "KEY_VERSION_OPPO", "KEY_VERSION_SMARTISAN", "KEY_VERSION_VIVO", "ROM_EMUI", "ROM_FLYME", "ROM_MIUI", "ROM_OPPO", "ROM_QIKU", "ROM_SMARTISAN", "ROM_VIVO", "Tag", "xName", "xVersion", "check", "", "rom", "getProp", "name", "is360", "isEmui", "isFlyme", "isMiui", "isOppo", "isSmartisan", "isVivo", "swiftmk_debug"})
public final class OSUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.os.OSUtil INSTANCE = null;
    private static final java.lang.String Tag = "Rom";
    private static final java.lang.String ROM_EMUI = "EMUI";
    private static final java.lang.String ROM_MIUI = "MIUI";
    private static final java.lang.String ROM_FLYME = "FLYME";
    private static final java.lang.String ROM_OPPO = "OPPO";
    private static final java.lang.String ROM_SMARTISAN = "SMARTISAN";
    private static final java.lang.String ROM_VIVO = "VIVO";
    private static final java.lang.String ROM_QIKU = "QIKU";
    private static final java.lang.String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final java.lang.String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final java.lang.String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final java.lang.String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final java.lang.String KEY_VERSION_VIVO = "ro.vivo.os.version";
    private static java.lang.String xName;
    private static java.lang.String xVersion;
    
    private OSUtil() {
        super();
    }
    
    public final boolean isEmui() {
        return false;
    }
    
    public final boolean isMiui() {
        return false;
    }
    
    public final boolean isVivo() {
        return false;
    }
    
    public final boolean isOppo() {
        return false;
    }
    
    public final boolean isFlyme() {
        return false;
    }
    
    public final boolean is360() {
        return false;
    }
    
    public final boolean isSmartisan() {
        return false;
    }
    
    private final boolean check(java.lang.String rom) {
        return false;
    }
    
    private final java.lang.String getProp(java.lang.String name) {
        return null;
    }
}