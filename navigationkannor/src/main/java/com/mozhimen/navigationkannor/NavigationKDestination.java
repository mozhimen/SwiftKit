package com.mozhimen.navigationkannor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface NavigationKDestination {
    /**
     * 页面路由的名称
     * @return
     */
    String pageUrl();

    /**
     * 是否作为启动页
     * @return
     */
    boolean isStarter() default false;
}