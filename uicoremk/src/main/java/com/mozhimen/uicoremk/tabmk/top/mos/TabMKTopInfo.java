package com.mozhimen.uicoremk.tabmk.top.mos;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @ClassName TabMKTopInfo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 12:56
 * @Version 1.0
 */
public class TabMKTopInfo<Color> {
    public enum TabMKType {
        BITMAP,
        TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabMKType tabType;

    public TabMKTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabMKType.BITMAP;
    }

    public TabMKTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabMKType.TEXT;
    }
}
