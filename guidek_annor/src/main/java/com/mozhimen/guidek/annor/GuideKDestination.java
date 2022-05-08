package com.mozhimen.guidek.annor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface GuideKDestination {
    /**
     * 页面路由的名称
     *
     * @return
     */
    String pageUrl();

    /**
     * 页面顺序index
     * @return
     */
    int pageIndex();

    /**
     * 是否作为默认启动页
     *
     * @return
     */
    boolean isDefault() default false;

    /**
     * 是否启用
     *
     * @return
     */
    boolean isEnable() default true;
}