package com.mozhimen.uicoremk.tabmk.bottom;

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

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.tabmk.bottom.mos.TabMKBottomInfo;
import com.mozhimen.uicoremk.tabmk.commons.ITabMKLayout;
import com.mozhimen.uicoremk.utilmk.UtilMKDisplay;
import com.mozhimen.uicoremk.utilmk.MKViewUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TabMKBottomLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 16:32
 * @Version 1.0
 */
public class TabMKBottomLayout extends FrameLayout implements ITabMKLayout<TabMKBottom, TabMKBottomInfo<?>> {
    private final List<OnTabSelectedListener<TabMKBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabMKBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    //TabBottom高度
    private static float tabBottomHeight = 50;
    //TabBottom的头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<TabMKBottomInfo<?>> infoList;

    public TabMKBottomLayout(@NonNull Context context) {
        this(context, null);
    }

    public TabMKBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabMKBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TabMKBottom findTab(@NonNull TabMKBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabMKBottom) {
                TabMKBottom tab = (TabMKBottom) child;
                if (tab.getTabMKInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabMKBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull TabMKBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    @Override
    public void inflateInfo(@NonNull List<TabMKBottomInfo<?>> infoList) {
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
        Iterator<OnTabSelectedListener<TabMKBottomInfo<?>>> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabMKBottom) {
                iterator.remove();
            }
        }
        int width = UtilMKDisplay.INSTANCE.getDisplayWithInPx(getContext()) / infoList.size();
        int height = UtilMKDisplay.INSTANCE.dp2px(tabBottomHeight);
        //不用LinearLayout的原因: 当动态改变child大小后Gravity.Bottom会失效.
        FrameLayout ll = new FrameLayout(getContext());
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            final TabMKBottomInfo<?> info = infoList.get(i);
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            TabMKBottom tabMKBottom = new TabMKBottom(getContext());
            tabSelectedChangeListeners.add(tabMKBottom);
            tabMKBottom.setTabInfo(info);
            ll.addView(tabMKBottom, params);
            tabMKBottom.setOnClickListener(new OnClickListener() {
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

        LayoutParams bottomLineParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UtilMKDisplay.INSTANCE.dp2px(bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = UtilMKDisplay.INSTANCE.dp2px(tabBottomHeight - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NonNull TabMKBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<TabMKBottomInfo<?>> listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tabmk_bottom_layout, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, UtilMKDisplay.INSTANCE.dp2px(tabBottomHeight));
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
        ViewGroup targetView = MKViewUtil.INSTANCE.findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            targetView = MKViewUtil.INSTANCE.findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            targetView = MKViewUtil.INSTANCE.findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, UtilMKDisplay.INSTANCE.dp2px(tabBottomHeight));
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
}
