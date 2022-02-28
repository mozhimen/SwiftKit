package com.mozhimen.uicoremk.refreshmk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mozhimen.uicoremk.R;
import com.mozhimen.uicoremk.refreshmk.commons.RefreshMKOverView;

public class TextOverView extends RefreshMKOverView {
    private TextView mText;
    private View mRotateView;

    public TextOverView(@NonNull Context context) {
        super(context);
    }

    public TextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.refreshmk_overview_text, this, true);
        mText = findViewById(R.id.refreshmk_overview_text_content);
        mRotateView = findViewById(R.id.refreshmk_overview_text_icon);
    }

    @Override
    public void onScroll(int scrollY, int pullRefreshHeight) {
    }

    @Override
    public void onVisible() {
        mText.setText("下拉刷新");
    }

    @Override
    public void onOver() {
        mText.setText("松开刷新");
    }

    @Override
    public void onRefresh() {
        mText.setText("正在刷新...");
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.refreshmk_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(interpolator);
        mRotateView.setAnimation(operatingAnim);
    }

    @Override
    public void onFinish() {
        mRotateView.clearAnimation();
    }
}
