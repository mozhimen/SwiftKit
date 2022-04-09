package com.mozhimen.uicorek.tabk.commons;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @ClassName ITabKLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 12:46
 * @Version 1.0
 */
public interface ITabKLayout<Tab extends ViewGroup, D> {
    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(@NonNull D defaultInfo);

    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, D prevInfo, @NonNull D nextInfo);
    }
}
