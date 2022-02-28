package com.mozhimen.swiftmk.utils.verification;

import java.lang.System;

/**
 * 密码校验
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0006J\u0012\u0010\u0010\u001a\u00020\n*\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u0014\u0010\u0012\u001a\u00020\n*\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/mozhimen/swiftmk/utils/verification/VerifyUtil;", "", "()V", "DEFAULT", "", "REGEX_DOMAIN", "", "REGEX_IP", "REGEX_PORT", "isDoMainValid", "", "domain", "isIPValid", "ip", "isPortValid", "port", "RegexValid", "degree", "isPasswordValid", "swiftmk_debug"})
public final class VerifyUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.mozhimen.swiftmk.utils.verification.VerifyUtil INSTANCE = null;
    private static final int DEFAULT = 0;
    
    /**
     * IP 验证
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGEX_IP = "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)";
    
    /**
     * 域名验证
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGEX_DOMAIN = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
    
    /**
     * 端口号验证
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGEX_PORT = "^[-\\+]?[\\d]{1,6}$";
    
    private VerifyUtil() {
        super();
    }
    
    /**
     * 作用: 校验密码
     * 用法1: "...".isPasswordValid()
     *      "...".isPasswordValid(DEFAULT)
     */
    public final boolean isPasswordValid(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$isPasswordValid, int degree) {
        return false;
    }
    
    /**
     * 作用: 正则校验
     */
    public final boolean RegexValid(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$RegexValid, @org.jetbrains.annotations.NotNull()
    java.lang.String degree) {
        return false;
    }
    
    public final boolean isIPValid(@org.jetbrains.annotations.NotNull()
    java.lang.String ip) {
        return false;
    }
    
    public final boolean isDoMainValid(@org.jetbrains.annotations.NotNull()
    java.lang.String domain) {
        return false;
    }
    
    public final boolean isPortValid(@org.jetbrains.annotations.NotNull()
    java.lang.String port) {
        return false;
    }
}