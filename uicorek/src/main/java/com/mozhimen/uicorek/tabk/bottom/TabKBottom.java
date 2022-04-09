package com.mozhimen.uicorek.tabk.bottom;

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

import com.mozhimen.uicorek.R;
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomInfo;
import com.mozhimen.uicorek.tabk.commons.ITabK;

/**
 * @ClassName TabKBottom
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/27 13:09
 * @Version 1.0
 */

/**
 * override fun onCreate(savedInstanceState: Bundle?) {
 *  ...
 *  setContentView(R.layout.activity_main)
 *  val tabBottom = findViewById<TabKBottom>(R.id.main_tab)
 *  val homeInfo = TabKBottomInfo("首页","fonts/iconfont.ttf",
 *      getString(R.string.icon_home),null,"#ff656667","#ffd44949")
 *  tabBottom.tabInfo = homeInfo;
 */
public class TabKBottom extends RelativeLayout implements ITabK<TabKBottomInfo<?>> {

    private TabKBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public TabKBottom(Context context) {
        this(context, null);
    }

    public TabKBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabKBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tabk_bottom, this);
        tabImageView = findViewById(R.id.tabk_bottom_image);
        tabIconView = findViewById(R.id.tabk_bottom_icon);
        tabNameView = findViewById(R.id.tabk_bottom_name);
    }

    @Override
    public void setTabInfo(@NonNull TabKBottomInfo tabKBottomInfo) {
        this.tabInfo = tabKBottomInfo;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabKBottomInfo.TabKType.ICON) {
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
        } else if (tabInfo.tabType == TabKBottomInfo.TabKType.BITMAP) {
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
    public void onTabSelectedChange(int index, @NonNull TabKBottomInfo<?> prevInfo, @NonNull TabKBottomInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        if (prevInfo == tabInfo) {
            inflateInfo(false, false);
        } else {
            inflateInfo(true, false);
        }
    }

    public TabKBottomInfo<?> getTabKInfo() {
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
