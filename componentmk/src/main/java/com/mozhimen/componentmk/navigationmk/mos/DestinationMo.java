package com.mozhimen.componentmk.navigationmk.mos;

/**
 * @ClassName NavigationMKMo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/6 14:54
 * @Version 1.0
 */
public class DestinationMo {
    public String pageUrl;  //页面url
    public int pageId;      //路由节点(页面)的id
    public boolean isStarter;//是否作为路由的第一个启动页
    public String destType;//路由界面(页面)的类型,activity,dialog,fragment
    public String clazzName;//全类名
}
