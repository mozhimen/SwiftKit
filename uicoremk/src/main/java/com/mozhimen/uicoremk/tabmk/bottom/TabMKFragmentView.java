package com.mozhimen.uicoremk.tabmk.bottom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mozhimen.uicoremk.tabmk.bottom.helpers.TabMKViewAdapter;

/**
 * @ClassName FragmentTabMKView
 * @Description 1.将Fragment的操作内聚
 * 2.提供通用的API
 * @Author Kolin Zhao
 * @Date 2021/8/2 12:57
 * @Version 1.0
 */
public class TabMKFragmentView extends FrameLayout {

    private TabMKViewAdapter mAdapter;
    private int currentPosition;

    public TabMKFragmentView(@NonNull Context context) {
        super(context);
    }

    public TabMKFragmentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabMKFragmentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TabMKViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(TabMKViewAdapter adapter) {
        if (this.mAdapter != null || adapter == null) {
            return;
        }
        this.mAdapter = adapter;
        currentPosition = -1;
    }

    public void setCurrentItem(int position) {
        if (position < 0 || position >= mAdapter.getCount()) {
            return;
        }
        if (currentPosition != position) {
            currentPosition = position;
            mAdapter.instantiateItem(this, position);
        }
    }

    public int getCurrentItem() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        if (this.mAdapter == null) {
            throw new IllegalArgumentException("please call setAdapter first.");
        }
        return mAdapter.getCurrentFragment();
    }
}
