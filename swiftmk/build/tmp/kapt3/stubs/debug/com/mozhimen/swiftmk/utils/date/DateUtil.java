package com.mozhimen.swiftmk.utils.date;

import java.lang.System;

/**
 * @ClassName DateUtil
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/23 15:40
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004H\u0007J\u001e\u0010\f\u001a\u0004\u0018\u00010\n2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/mozhimen/swiftmk/utils/date/DateUtil;", "", "()V", "format_HHmm", "", "format_yyyyMMdd", "format_yyyyMMddHH", "format_yyyyMMddHHmmss", "formatDateToString", "date", "Ljava/util/Date;", "formatDate", "formatStringToDate", "swiftmk_debug"})
public final class DateUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.date.DateUtil INSTANCE = null;
    
    /**
     * 年月日 时分秒
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String format_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 年月日 时
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String format_yyyyMMddHH = "yyyy-MM-dd HH";
    
    /**
     * 年月日
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String format_yyyyMMdd = "yyyy-MM-dd";
    
    /**
     * 时分
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String format_HHmm = "HH:mm";
    
    private DateUtil() {
        super();
    }
    
    /**
     * 日期转字符串
     */
    @org.jetbrains.annotations.Nullable()
    @android.annotation.SuppressLint(value = {"SimpleDateFormat"})
    public final java.lang.String formatDateToString(@org.jetbrains.annotations.Nullable()
    java.util.Date date, @org.jetbrains.annotations.Nullable()
    java.lang.String formatDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @android.annotation.SuppressLint(value = {"SimpleDateFormat"})
    public final java.util.Date formatStringToDate(@org.jetbrains.annotations.Nullable()
    java.lang.String date, @org.jetbrains.annotations.Nullable()
    java.lang.String formatDate) {
        return null;
    }
}