package com.mozhimen.uicoremk.tabmk.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.mozhimen.basicsmk.utilmk.UtilMKDisplay;
import com.mozhimen.uicoremk.tabmk.commons.ITabMKLayout;
import com.mozhimen.uicoremk.tabmk.top.mos.TabMKTopInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TabMKTopLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/3 11:51
 * @Version 1.0
 */
public class TabMKTopLayout extends HorizontalScrollView implements ITabMKLayout<TabMKTop, TabMKTopInfo<?>> {
    private List<OnTabSelectedListener<TabMKTopInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabMKTopInfo<?> selectedInfo;
    private List<TabMKTopInfo<?>> infoList;

    public TabMKTopLayout(Context context) {
        this(context, null);
    }

    public TabMKTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMKTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalScrollBarEnabled(false);
    }

    @Override
    public TabMKTop findTab(@NonNull TabMKTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabMKTop) {
                TabMKTop tab = (TabMKTop) child;
                if (tab.getTabMKInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabMKTopInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull TabMKTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<TabMKTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;
        //清除之前添加的TabMKTop listener,Tips: Java foreach remove问题
        Iterator<OnTabSelectedListener<TabMKTopInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabMKTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            TabMKTopInfo<?> info = infoList.get(i);
            TabMKTop tab = new TabMKTop(getContext());
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

    private void onSelected(@NonNull TabMKTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabMKTopInfo<?>> listener : tabSelectedChangeListeners) {
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
    private void autoScroll(TabMKTopInfo nextInfo) {
        TabMKTop tabMKTop = findTab(nextInfo);
        if (tabMKTop == null) {
            return;
        }
        int index = infoList.indexOf(nextInfo);
        int[] loc = new int[2];
        //获取点击控件在屏幕的位置
        tabMKTop.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWidth == 0) {
            tabWidth = tabMKTop.getWidth();
        }
        //判断点击了屏幕左侧还是右侧
        if ((loc[0] + tabWidth / 2) > UtilMKDisplay.INSTANCE.getDisplayWithInPx() / 2) {
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
        TabMKTop target = findTab(infoList.get(index));
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
