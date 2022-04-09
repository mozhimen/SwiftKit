package com.mozhimen.uicorek.tabk.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mozhimen.basicsk.utilk.UtilKDisplay;
import com.mozhimen.basicsk.utilk.UtilKView;
import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomInfo;
import com.mozhimen.uicorek.tabk.commons.ITabKLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TabKBottomLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 16:32
 * @Version 1.0
 */
public class TabKBottomLayout extends FrameLayout implements ITabKLayout<TabKBottom, TabKBottomInfo<?>> {
    private final List<OnTabSelectedListener<TabKBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabKBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<TabKBottomInfo<?>> infoList;

    public TabKBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabKBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabKBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TabKBottom findTab(@NonNull TabKBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabKBottom) {
                TabKBottom tab = (TabKBottom) child;
                if (tab.getTabKInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabKBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull TabKBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    @Override
    public void inflateInfo(@NonNull List<TabKBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();
        //清除之前添加的TabMkBottom listener,同时遍历和删除问题,采用迭代器
        Iterator<OnTabSelectedListener<TabKBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabKBottom) {
                iterator.remove();
            }
        }
        int width = UtilKDisplay.INSTANCE.getDisplayWidthInPx() / infoList.size();
        int height = UtilKDisplay.INSTANCE.dp2px(tabBottomHeight);
        //不用LinearLayout的原因: 当动态改变child大小后Gravity.Bottom会失效.
        FrameLayout ll = new FrameLayout(getContext());
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            final TabKBottomInfo<?> info = infoList.get(i);
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            TabKBottom tabKBottom = new TabKBottom(getContext());
            tabSelectedChangeListeners.add(tabKBottom);
            tabKBottom.setTabInfo(info);
            ll.addView(tabKBottom, params);
            tabKBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(ll, flParams);
        fixContentView();
    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams bottomLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UtilKDisplay.INSTANCE.dp2px(bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = UtilKDisplay.INSTANCE.dp2px(tabBottomHeight - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NonNull TabKBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabKBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tabk_bottom_layout, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, UtilKDisplay.INSTANCE.dp2px(tabBottomHeight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }

    /**
     * 修复内容区域的底部padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = UtilKView.INSTANCE.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = UtilKView.INSTANCE.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = UtilKView.INSTANCE.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, UtilKDisplay.INSTANCE.dp2px(tabBottomHeight));
            targetView.setClipToPadding(false);
        }
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    public void resizeTabKBottomLayout() {
        int width = UtilKDisplay.INSTANCE.getDisplayWidthInPx() / infoList.size();
        ViewGroup frameLayout = (ViewGroup) getChildAt(getChildCount() - 1);
        int childCount = frameLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View button = frameLayout.getChildAt(i);
            FrameLayout.LayoutParams params = (LayoutParams) button.getLayoutParams();
            params.width = width;
            params.leftMargin = i * width;
            button.setLayoutParams(params);
        }
    }
}
