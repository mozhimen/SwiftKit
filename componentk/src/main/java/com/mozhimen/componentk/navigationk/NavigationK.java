package com.mozhimen.componentk.navigationk;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mozhimen.componentk.R;
import com.mozhimen.componentk.navigationk.helpers.NavigationKHelper;
import com.mozhimen.componentk.navigationk.mos.Destination;
import com.mozhimen.componentk.navigationk.mos.TabConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName NavigationKManager
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/6 14:48
 * @Version 1.0
 */
public class NavigationK {
    private static final String TYPE_ACTIVITY = "activity";
    private static final String TYPE_FRAGMENT = "fragment";
    private static final String TYPE_DIALOG = "dialog";

    private static final String FILENAME_DESTINATION = "navigationKDestination.json";
    private static final String FILENAME_TAB_CONFIG = "navigationKTabConfig.json";
    private static HashMap<String, Destination> destinations;

    public static String parseFile(Context context, String fileName) {
        AssetManager assets = context.getAssets();
        try {
            InputStream inputStream = assets.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder builder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            inputStream.close();
            bufferedReader.close();

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void buildNavGraph(FragmentActivity activity, FragmentManager childFragmentManager, NavController controller, int containerId) {
        String content = parseFile(activity, FILENAME_DESTINATION);
        destinations = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
        });
        Iterator<Destination> iterator = destinations.values().iterator();
        NavigatorProvider provider = controller.getNavigatorProvider();

        NavGraphNavigator graphNavigator = provider.getNavigator(NavGraphNavigator.class);
        NavGraph navGraph = new NavGraph(graphNavigator);

        NavigationKHelper navigationKHelper = new NavigationKHelper(activity, childFragmentManager, containerId);
        provider.addNavigator(navigationKHelper);
        while (iterator.hasNext()) {
            Destination destination = iterator.next();
            if (destination.destType.equals(TYPE_ACTIVITY)) {
                ActivityNavigator navigator = provider.getNavigator(ActivityNavigator.class);
                ActivityNavigator.Destination node = navigator.createDestination();
                node.setId(destination.pageId);
                node.setComponentName(new ComponentName(activity.getPackageName(), destination.clazzName));
                navGraph.addDestination(node);
            } else if (destination.destType.equals(TYPE_FRAGMENT)) {
                NavigationKHelper.Destination node = navigationKHelper.createDestination();
                node.setId(destination.pageId);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);
            } else if (destination.destType.equals(TYPE_DIALOG)) {
                DialogFragmentNavigator navigator = provider.getNavigator(DialogFragmentNavigator.class);
                DialogFragmentNavigator.Destination node = navigator.createDestination();
                node.setId(destination.pageId);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);
            }

            if (destination.isStarter) {
                navGraph.setStartDestination(destination.pageId);
            }
        }

        controller.setGraph(navGraph);
    }

    public static void buildBottomTab(BottomNavigationView navigationView) {
        String content = parseFile(navigationView.getContext(), FILENAME_TAB_CONFIG);
        TabConfig tabConfig = JSON.parseObject(content, TabConfig.class);

        List<TabConfig.TabInfo> tabInfos = tabConfig.tabInfos;
        Menu menu = navigationView.getMenu();
        for (TabConfig.TabInfo tabInfo : tabInfos) {
            if (!tabInfo.enable) continue;
            Destination destination = destinations.get(tabInfo.pageUrl);
            if (destination != null) {
                MenuItem menuItem = menu.add(0, destination.pageId, tabInfo.index, tabInfo.title);
                menuItem.setIcon(R.drawable.navigationk_home);
            }
        }
    }
}
