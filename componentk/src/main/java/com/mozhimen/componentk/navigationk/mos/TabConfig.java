package com.mozhimen.componentk.navigationk.mos;

import java.util.List;

/**
 * @ClassName TabConfigMo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/6 16:30
 * @Version 1.0
 */
public class TabConfig {
    public int selectTab;
    public List<TabInfo> tabInfos;

    public static class TabInfo {
        public String pageUrl;
        public String title;
        public int index;
        public int size;
        public boolean enable;
    }
}
