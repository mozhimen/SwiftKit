package com.mozhimen.uicoremk.tabmk.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.tabmk.bottom.mos.TabMKBottomInfo;
import com.mozhimen.uicoremk.tabmk.commons.ITabMK;

/**
 * @ClassName TabMKBottom
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 13:09
 * @Version 1.0
 */

/**
 * override fun onCreate(savedInstanceState: Bundle?) {
 *  ...
 *  setContentView(R.layout.activity_main)
 *  val tabBottom = findViewById<TabMKBottom>(R.id.main_tab)
 *  val homeInfo = TabMKBottomInfo("首页","fonts/iconfont.ttf",
 *      getString(R.string.icon_home),null,"#ff656667","#ffd44949")
 *  tabBottom.tabInfo = homeInfo;
 */
public class TabMKBottom extends RelativeLayout implements ITabMK<TabMKBottomInfo<?>> {

    private TabMKBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public TabMKBottom(Context context) {
        this(context, null);
    }

    public TabMKBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabMKBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tabmk_bottom, this);
        tabImageView = findViewById(R.id.tabmk_bottom_image);
        tabIconView = findViewById(R.id.tabmk_bottom_icon);
        tabNameView = findViewById(R.id.tabmk_bottom_name);
    }

    @Override
    public void setTabInfo(@NonNull TabMKBottomInfo tabMKBottomInfo) {
        this.tabInfo = tabMKBottomInfo;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabMKBottomInfo.TabMKType.ICON) {
            if (init) {
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectedIconName) ? tabInfo.defaultIconName : tabInfo.selectedIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {
                tabIconView.setText(tabInfo.defaultIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == TabMKBottomInfo.TabMKType.BITMAP) {
            if (init) {
                tabImageView.setVisibility(VISIBLE);
                tabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                tabImageView.setImageBitmap(tabInfo.selectedBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }
    }

    /**
     * 改变某个Tab的高度
     *
     * @param height
     */
    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull TabMKBottomInfo<?> prevInfo, @NonNull TabMKBottomInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    public TabMKBottomInfo<?> getTabMKInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public TextView getTabIconView() {
        return tabIconView;
    }

    public TextView getTabNameView() {
        return tabNameView;
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}
