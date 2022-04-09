package com.mozhimen.uicorek.tabk.top.mos;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @ClassName TabKTopInfo
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 12:56
 * @Version 1.0
 */
public class TabKTopInfo<Color> {
    public enum TabKType {
        BITMAP,
        TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabKType tabType;

    public TabKTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabKType.BITMAP;
    }

    public TabKTopInfo(String name, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabKType.TEXT;
    }
}
