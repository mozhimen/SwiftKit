package com.mozhimen.uicorek.tabk.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.mozhimen.basicsk.utilk.UtilKDisplay;
import com.mozhimen.uicorek.tabk.commons.ITabKLayout;
import com.mozhimen.uicorek.tabk.top.mos.TabKTopInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TabKTopLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/3 11:51
 * @Version 1.0
 */
public class TabKTopLayout extends HorizontalScrollView implements ITabKLayout<TabKTop, TabKTopInfo<?>> {
    private List<OnTabSelectedListener<TabKTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabKTopInfo<?> selectedInfo;
    private List<TabKTopInfo<?>> infoList;

    public TabKTopLayout(Context context) {
        this(context, null);
    }

    public TabKTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabKTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalScrollBarEnabled(false);
    }

    @Override
    public TabKTop findTab(@NonNull TabKTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabKTop) {
                TabKTop tab = (TabKTop) child;
                if (tab.getTabKInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabKTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull TabKTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<TabKTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;
        //清除之前添加的TabKTop listener,Tips: Java foreach remove问题
        Iterator<OnTabSelectedListener<TabKTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabKTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            TabKTopInfo<?> info = infoList.get(i);
            TabKTop tab = new TabKTop(getContext());
            tabSelectedChangeListeners.add(tab);
            tab.setTabInfo(info);
            linearLayout.addView(tab);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
    }

    private void onSelected(@NonNull TabKTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabKTopInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    /**
     * 自动滚动,实现点击的位置能够自动滚动以展示前后2个
     *
     * @param nextInfo
     */
    private void autoScroll(TabKTopInfo nextInfo) {
        TabKTop tabKTop = findTab(nextInfo);
        if (tabKTop == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取点击控件在屏幕的位置
        tabKTop.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWidth == 0) {
            tabWidth = tabKTop.getWidth();
        }
        //判断点击了屏幕左侧还是右侧
        if ((loc[0] + tabWidth / 2) > UtilKDisplay.INSTANCE.getDisplayWidthInPx() / 2) {
            scrollWidth = rangeScrollWidth(index, 2);
        } else {
            scrollWidth = rangeScrollWidth(index, -2);
        }
        smoothScrollTo(getScrollX() + scrollWidth, 0);
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前后的范围
     * @return
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) {
                next = range + i + index;
            } else {
                next = range - i + index;
            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private int scrollWidth(int index, boolean toRight) {
        TabKTop target = findTab(infoList.get(index));
        if (target == null) return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {//点击了屏幕右侧
            if (rect.right > tabWidth) {//right坐标大于控件的宽度时,说明完全没有显示
                return tabWidth;
            } else {//显示部分,减去已显示的宽度
                return tabWidth - rect.right;
            }
        } else {
            if (rect.left <= -tabWidth) {//left坐标小于等于-控件的宽度,说明完全没有显示
                return tabWidth;
            } else if (rect.left > 0) {//显示部分
                return rect.left;
            }
            return 0;
        }
    }

    int tabWidth;

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
