package com.mozhimen.uicorek.layoutk.slider;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.MotionEvent.ACTION_UP;

import com.mozhimen.basick.utilk.UtilKDisplay;
import com.mozhimen.uicorek.layoutk.slider.commons.BubbleClickedListener;
import com.mozhimen.uicorek.layoutk.slider.commons.EditListener;
import com.mozhimen.uicorek.layoutk.slider.commons.Listener;
import com.mozhimen.uicorek.layoutk.slider.commons.RegionTextFormatter;
import com.mozhimen.uicorek.layoutk.slider.commons.TextFormatter;
import com.mozhimen.uicorek.layoutk.slider.mos.Bubble;
import com.mozhimen.uicorek.layoutk.slider.mos.Settings;
import com.mozhimen.uicorek.layoutk.slider.mos.Step;
import com.mozhimen.uicorek.layoutk.slider.temps.EurosTextFormatter;
import com.mozhimen.uicorek.viewk.ViewKTouch;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class Slidr extends FrameLayout {
    private static final float DISTANCE_TEXT_BAR = 10;
    private static final float BUBBLE_PADDING_HORIZONTAL = 15;
    private static final float BUBBLE_PADDING_VERTICAL = 10;

    public static final float BUBBLE_ARROW_HEIGHT = 10;
    public static final float BUBBLE_ARROW_WIDTH = 20;
    boolean moving = false;
    private Listener listener;
    private BubbleClickedListener bubbleClickedListener;
    private GestureDetectorCompat detector;
    private Settings settings;
    private float max = 1000;
    private float min = 0;
    private float currentValue = 0;
    private float oldValue = Float.MIN_VALUE;
    private List<Step> steps = new ArrayList<>();
    private float barY;
    private float barWidth;
    private float indicatorX;
    private int indicatorRadius;
    private float barCenterY;
    private Bubble bubble = new Bubble();
    private TextFormatter textFormatter = new EurosTextFormatter();
    private RegionTextFormatter regionTextFormatter = null;

    private String textMax = "";
    private String textMin = "";
    private int calculatedHieght = 0;
    private boolean isEditing = false;
    private String textEditing = "";
    private EditText editText;
    private ViewKTouch touchView;
    private EditListener editListener;

    @Nullable
    private ViewGroup parentScroll;

    public Slidr(Context context) {
        this(context, null);
    }

    public Slidr(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Slidr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setBubbleClickedListener(BubbleClickedListener bubbleClickedListener) {
        this.bubbleClickedListener = bubbleClickedListener;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
        updateValues();
        update();
    }

    public void setMin(float min) {
        this.min = min;
        updateValues();
        update();
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float value) {
        this.currentValue = value;
        updateValues();
        update();
    }

    public void setTextMax(String textMax) {
        this.textMax = textMax;
        postInvalidate();
    }

    public void setTextMin(String textMin) {
        this.textMin = textMin;
        postInvalidate();
    }

    public void setTextFormatter(TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
        update();
    }

    public void setRegionTextFormatter(RegionTextFormatter regionTextFormatter) {
        this.regionTextFormatter = regionTextFormatter;
        update();
    }

    public void setEditListener(EditListener editListener) {
        this.editListener = editListener;
    }

    public void addStep(List<Step> steps) {
        this.steps.addAll(steps);
        Collections.sort(steps);
        update();
    }

    public void addStep(Step step) {
        this.steps.add(step);
        Collections.sort(steps);
        update();
    }

    public void clearSteps() {
        this.steps.clear();
        update();
    }

    public void update() {
        if (barWidth > 0f) {
            float currentPercent = indicatorX / barWidth;
            currentValue = currentPercent * (max - min) + min;
            currentValue = Math.round(currentValue);

            if (listener != null && oldValue != currentValue) {
                oldValue = currentValue;
                listener.valueChanged(Slidr.this, currentValue);
            } else {

            }

            updateBubbleWidth();
            editBubbleEditPosition();
        }
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return handleTouch(event);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            dispatchKeyEvent(event);
            closeEditText();
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        parentScroll = (ViewGroup) getScrollableParentView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        {

            final float paddingLeft = settings.getPaddingCorners();
            final float paddingRight = settings.getPaddingCorners();


            if (isRegions()) {
                if (steps.isEmpty()) {
                    settings.getPaintIndicator().setColor(settings.getRegionColorLeft());
                    settings.getPaintBubble().setColor(settings.getRegionColorLeft());
                } else {
                    settings.getPaintIndicator().setColor(settings.getRegionColorRight());
                    settings.getPaintBubble().setColor(settings.getRegionColorRight());
                }
            } else {
                final Step stepBeforeCustor = findStepOfCustor();
                if (stepBeforeCustor != null) {
                    settings.getPaintIndicator().setColor(stepBeforeCustor.getColorBefore());
                    settings.getPaintBubble().setColor(stepBeforeCustor.getColorBefore());
                } else {
                    if (settings.getStep_colorizeAfterLast()) {
                        final Step beforeCustor = findStepBeforeCustor();
                        if (beforeCustor != null) {
                            settings.getPaintIndicator().setColor(beforeCustor.getColorAfter());
                            settings.getPaintBubble().setColor(beforeCustor.getColorAfter());
                        }
                    } else {
                        settings.getPaintIndicator().setColor(settings.getColorBackground());
                        settings.getPaintBubble().setColor(settings.getColorBackground());
                    }
                }
            }

            final float radiusCorner = settings.getBarHeight() / 2f;

            final float indicatorCenterX = indicatorX + paddingLeft;

            { //background
                final float centerCircleLeft = paddingLeft;
                final float centerCircleRight = getWidth() - paddingRight;

                //grey background
                if (isRegions()) {
                    if (steps.isEmpty()) {
                        settings.getPaintBar().setColor(settings.getColorBackground());
                    } else {
                        settings.getPaintBar().setColor(settings.getRegionColorRight());
                    }
                } else {
                    settings.getPaintBar().setColor(settings.getColorBackground());
                }
                canvas.drawCircle(centerCircleLeft, barCenterY, radiusCorner, settings.getPaintBar());
                canvas.drawCircle(centerCircleRight, barCenterY, radiusCorner, settings.getPaintBar());
                canvas.drawRect(centerCircleLeft, barY, centerCircleRight, barY + settings.getBarHeight(), settings.getPaintBar());

                if (isRegions()) {
                    settings.getPaintBar().setColor(settings.getRegionColorLeft());

                    canvas.drawCircle(centerCircleLeft, barCenterY, radiusCorner, settings.getPaintBar());
                    canvas.drawRect(centerCircleLeft, barY, indicatorCenterX, barY + settings.getBarHeight(), settings.getPaintBar());
                } else {
                    float lastX = centerCircleLeft;
                    boolean first = true;
                    for (Step step : steps) {
                        settings.getPaintBar().setColor(step.getColorBefore());
                        if (first) {
                            canvas.drawCircle(centerCircleLeft, barCenterY, radiusCorner, settings.getPaintBar());
                        }

                        final float x = step.getStartPos() + paddingLeft;
                        if (!settings.getStep_colorizeOnlyBeforeIndicator()) {
                            canvas.drawRect(lastX, barY, x, barY + settings.getBarHeight(), settings.getPaintBar());
                        } else {
                            canvas.drawRect(lastX, barY, Math.min(x, indicatorCenterX), barY + settings.getBarHeight(), settings.getPaintBar());
                        }
                        lastX = x;

                        first = false;
                    }


                    if (settings.getStep_colorizeAfterLast()) {
                        //find the step just below currentValue
                        for (int i = steps.size() - 1; i >= 0; i--) {
                            final Step step = steps.get(i);
                            if ((currentValue - min) > step.getValue()) {
                                settings.getPaintBar().setColor(step.getColorAfter());
                                canvas.drawRect(step.getStartPos() + paddingLeft, barY, indicatorCenterX, barY + settings.getBarHeight(), settings.getPaintBar());
                                break;
                            }
                        }
                    }
                }
            }


            { //texts top (values)
                if (settings.getDrawTextOnTop()) {
                    final float textY = barY - UtilKDisplay.INSTANCE.dp2px(DISTANCE_TEXT_BAR);
                    if (isRegions()) {
                        float leftValue;
                        float rightValue;

                        if (settings.getRegions_centerText()) {
                            leftValue = currentValue;
                            rightValue = max - leftValue;
                        } else {
                            leftValue = min;
                            rightValue = max;
                        }

                        if (settings.getRegions_textFollowRegionColor()) {
                            settings.getPaintTextTop().setColor(settings.getRegionColorLeft());
                        }

                        float textX;
                        if (settings.getRegions_centerText()) {
                            textX = (indicatorCenterX - paddingLeft) / 2f + paddingLeft;
                        } else {
                            textX = paddingLeft;
                        }

                        drawIndicatorsTextAbove(canvas, formatRegionValue(0, leftValue), settings.getPaintTextTop(), textX, textY, Layout.Alignment.ALIGN_CENTER);

                        if (settings.getRegions_textFollowRegionColor()) {
                            settings.getPaintTextTop().setColor(settings.getRegionColorRight());
                        }

                        if (settings.getRegions_centerText()) {
                            textX = indicatorCenterX + (barWidth - indicatorCenterX - paddingLeft) / 2f + paddingLeft;
                        } else {
                            textX = paddingLeft + barWidth;
                        }
                        drawIndicatorsTextAbove(canvas, formatRegionValue(1, rightValue), settings.getPaintTextTop(), textX, textY, Layout.Alignment.ALIGN_CENTER);
                    } else {
                        drawIndicatorsTextAbove(canvas, formatValue(min), settings.getPaintTextTop(), 0 + paddingLeft, textY, Layout.Alignment.ALIGN_CENTER);
                        for (Step step : steps) {
                            drawIndicatorsTextAbove(canvas, formatValue(step.getValue()), settings.getPaintTextTop(), step.getStartPos() + paddingLeft, textY, Layout.Alignment.ALIGN_CENTER);
                        }
                        drawIndicatorsTextAbove(canvas, formatValue(max), settings.getPaintTextTop(), canvas.getWidth(), textY, Layout.Alignment.ALIGN_CENTER);
                    }
                }
            }


            { //steps + bottom text
                final float bottomTextY = barY + settings.getBarHeight() + 15;

                for (Step step : steps) {
                    if (settings.getStep_drawLines()) {
                        canvas.drawLine(step.getStartPos() + paddingLeft, barY - settings.getBarHeight() / 4f, step.getStartPos() + paddingLeft, barY + settings.getBarHeight() + settings.getBarHeight() / 4f, settings.getPaintStep());
                    }

                    if (settings.getDrawTextOnBottom()) {
                        //drawMultilineText(canvas, maxText, canvas.getWidth() - settings.paintText.measureText(maxText), textY, settings.paintText, Layout.Alignment.ALIGN_OPPOSITE);
                        drawMultilineText(canvas, step.getName(), step.getStartPos() + paddingLeft, bottomTextY, settings.getPaintTextBottom(), Layout.Alignment.ALIGN_CENTER);
                    }
                }

                if (settings.getDrawTextOnBottom()) {
                    if (!TextUtils.isEmpty(textMax)) {
                        drawMultilineText(canvas, textMax, canvas.getWidth(), bottomTextY, settings.getPaintTextBottom(), Layout.Alignment.ALIGN_CENTER);
                    }

                    if (!TextUtils.isEmpty(textMin)) {
                        drawMultilineText(canvas, textMin, 0, bottomTextY, settings.getPaintTextBottom(), Layout.Alignment.ALIGN_CENTER);
                    }
                }
            }

            //indicator
            {
                final int color = settings.getPaintIndicator().getColor();
                canvas.drawCircle(indicatorCenterX, this.barCenterY, indicatorRadius, settings.getPaintIndicator());
                settings.getPaintIndicator().setColor(Color.WHITE);
                canvas.drawCircle(indicatorCenterX, this.barCenterY, indicatorRadius * 0.85f, settings.getPaintIndicator());
                settings.getPaintIndicator().setColor(color);
            }

            //bubble
            {
                if (settings.getDrawBubble()) {
                    float bubbleCenterX = indicatorCenterX;
                    float trangleCenterX;

                    bubble.setX(bubbleCenterX - bubble.getWidth() / 2f);
                    bubble.setY(0f);

                    if (bubbleCenterX > canvas.getWidth() - bubble.getWidth() / 2f) {
                        bubbleCenterX = canvas.getWidth() - bubble.getWidth() / 2f;
                    } else if (bubbleCenterX - bubble.getWidth() / 2f < 0) {
                        bubbleCenterX = bubble.getWidth() / 2f;
                    }

                    trangleCenterX = (bubbleCenterX + indicatorCenterX) / 2f;

                    drawBubble(canvas, bubbleCenterX, trangleCenterX, 0);
                }
            }
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateValues();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        updateValues();
        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(calculatedHieght, MeasureSpec.EXACTLY));
    }

    private void closeEditText() {
        editText.clearFocus();

        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        ((ViewGroup) touchView.getParent()).removeView(touchView);
        removeView(editText);

        isEditing = false;
        if (TextUtils.isEmpty(textEditing)) {
            textEditing = String.valueOf(currentValue);
        }
        Float value;
        try {
            value = Float.valueOf(textEditing);
        } catch (Exception e) {
            e.printStackTrace();
            value = min;
        }


        value = Math.min(value, max);
        value = Math.max(value, min);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, value);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setCurrentValueNoUpdate(((float) animation.getAnimatedValue()));
                postInvalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
        editText = null;
        touchView = null;
        postInvalidate();
    }

    private ViewGroup getActivityDecorView() {
        return (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
    }

    private void editBubbleEditPosition() {
        if (isEditing) {
            editText.setX(Math.min(bubble.getX(), getWidth() - editText.getWidth()));
            editText.setY(bubble.getY());

            final ViewGroup.LayoutParams params = editText.getLayoutParams();
            params.width = (int) bubble.getWidth();
            params.height = (int) bubble.getSliderHeight();
            editText.setLayoutParams(params);

            editText.animate().alpha(1f);
        }
    }

    private void onBubbleClicked() {
        if (settings.getEditOnBubbleClick()) {
            isEditing = true;
            editText = new AppCompatEditText(getContext()) {
                @Override
                public boolean onKeyPreIme(int keyCode, KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        dispatchKeyEvent(event);
                        closeEditText();
                        return false;
                    }
                    return super.onKeyPreIme(keyCode, event);
                }

            };

            final int editMaxCharCount = 9;
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(editMaxCharCount)});


            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.setSelectAllOnFocus(true);

            editText.setSingleLine(true);
            editText.setGravity(Gravity.CENTER);
            //editText.setRawInputType(Configuration.KEYBOARD_12KEY);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            editText.setTextColor(settings.getPaintIndicator().getColor());
            editText.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            editText.setPadding(0, 0, 0, 0);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, UtilKDisplay.INSTANCE.dp2px(settings.getTextSizeBubbleCurrent()));

            textEditing = String.valueOf((int) currentValue);
            editText.setText(textEditing);

            final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = (int) bubble.getWidth();
            params.height = (int) bubble.getSliderHeight();
            editText.setLayoutParams(params);

            final Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            this.touchView = new ViewKTouch(getContext());
            touchView.setTouchArea(rect);
            getActivityDecorView().addView(touchView);

            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

                    touchView.setListener(
                            () -> {
                                closeEditText();
                                return null;
                            });
                }
            }, 300);

            addView(editText);
            editText.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    editBubbleEditPosition();
                    editText.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });


            editText.requestFocus();
            editText.requestFocusFromTouch();
            editBubbleEditPosition();

            if (editListener != null) {
                editListener.onEditStarted(editText);
            }

            editText.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        closeEditText();
                        return true;
                    }
                    return false;
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    textEditing = editText.getText().toString();
                    updateBubbleWidth();
                    invalidate();
                    editBubbleEditPosition();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            postInvalidate();
        }
        if (bubbleClickedListener != null) {
            bubbleClickedListener.bubbleClicked(this);
        }
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setWillNotDraw(false);

        detector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            //some callbacks

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onClick(e);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onContextClick(MotionEvent e) {
                return super.onContextClick(e);
            }
        });

        this.settings = new Settings(this);
        this.settings.init(context, attrs);
    }

    private void setCurrentValueNoUpdate(float value) {
        this.currentValue = value;
        listener.valueChanged(Slidr.this, currentValue);
        updateValues();

    }

    private View getScrollableParentView() {
        View view = this;
        while (view.getParent() != null && view.getParent() instanceof View) {
            view = (View) view.getParent();
            if (view instanceof ScrollView || view instanceof RecyclerView || view instanceof NestedScrollView) {
                return view;
            }
        }
        return null;
    }

    private boolean handleTouch(MotionEvent event) {
        if (isEditing) {
            return false;
        }
        boolean handledByDetector = this.detector.onTouchEvent(event);
        if (!handledByDetector) {

            final int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (parentScroll != null) {
                        parentScroll.requestDisallowInterceptTouchEvent(false);
                    }
                    moving = false;
                    break;
                case MotionEvent.ACTION_DOWN:
                    final float evY = event.getY();
                    if (evY <= barY || evY >= (barY + barWidth)) {
                        return true;
                    } else {
                        moving = true;
                    }
                    if (parentScroll != null) {
                        parentScroll.requestDisallowInterceptTouchEvent(true);
                    }
                case MotionEvent.ACTION_MOVE: {
                    if (moving) {
                        float evX = event.getX();

                        evX = evX - settings.getPaddingCorners();
                        if (evX < 0) {
                            evX = 0;
                        }
                        if (evX > barWidth) {
                            evX = barWidth;
                        }
                        this.indicatorX = evX;

                        update();
                    }
                }
                break;
            }
        }

        return true;
    }

    private void updateBubbleWidth() {
        this.bubble.setWidth(calculateBubbleTextWidth() + UtilKDisplay.INSTANCE.dp2px(BUBBLE_PADDING_HORIZONTAL) * 2f);
        this.bubble.setWidth(Math.max(150, this.bubble.getWidth()));
    }

    private boolean isRegions() {
        return settings.getModeRegion() || steps.isEmpty();
    }

    private void updateValues() {

        if (currentValue < min) {
            currentValue = min;
        }

        settings.setPaddingCorners(settings.getBarHeight());

        barWidth = getWidth() - this.settings.getPaddingCorners() * 2;

        if (settings.getDrawBubble()) {
            updateBubbleWidth();
            this.bubble.setHeight(UtilKDisplay.INSTANCE.dp2px(settings.getTextSizeBubbleCurrent()) + UtilKDisplay.INSTANCE.dp2px(BUBBLE_PADDING_VERTICAL) * 2f + UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT));
        } else {
            this.bubble.setHeight(0f);
        }

        this.barY = 0;
        if (settings.getDrawTextOnTop()) {
            barY += DISTANCE_TEXT_BAR * 2;
            if (isRegions()) {
                float topTextHeight = 0;
                final String tmpTextLeft = formatRegionValue(0, 0);
                final String tmpTextRight = formatRegionValue(1, 0);
                topTextHeight = Math.max(topTextHeight, calculateTextMultilineHeight(tmpTextLeft, settings.getPaintTextTop()));
                topTextHeight = Math.max(topTextHeight, calculateTextMultilineHeight(tmpTextRight, settings.getPaintTextTop()));

                this.barY += topTextHeight + 3;
            } else {
                float topTextHeight = 0;

                for (Step step : steps) {
                    topTextHeight = Math.max(
                            topTextHeight,
                            calculateTextMultilineHeight(formatValue(step.getValue()), settings.getPaintTextBottom())
                    );
                }
                this.barY += topTextHeight;
            }
        } else {
            if (settings.getDrawBubble()) {
                this.barY -= UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT) / 1.5f;
            }
        }

        this.barY += bubble.getHeight();

        this.barCenterY = barY + settings.getBarHeight() / 2f;

        if (settings.getIndicatorInside()) {
            this.indicatorRadius = (int) (settings.getBarHeight() * .5f);
        } else {
            this.indicatorRadius = (int) (settings.getBarHeight() * .9f);
        }

        for (Step step : steps) {
            final float stoppoverPercent = step.getValue() / (max - min);
            step.setStartPos(stoppoverPercent * barWidth);
        }

        indicatorX = (currentValue - min) / (max - min) * barWidth;

        calculatedHieght = (int) (barCenterY + indicatorRadius);

        float bottomTextHeight = 0;
        if (!TextUtils.isEmpty(textMax)) {
            bottomTextHeight = Math.max(
                    calculateTextMultilineHeight(textMax, settings.getPaintTextBottom()),
                    calculateTextMultilineHeight(textMin, settings.getPaintTextBottom())
            );
        }
        for (Step step : steps) {
            bottomTextHeight = Math.max(
                    bottomTextHeight,
                    calculateTextMultilineHeight(step.getName(), settings.getPaintTextBottom())
            );
        }

        calculatedHieght += bottomTextHeight;

        calculatedHieght += 10; //padding bottom

    }

    private Step findStepBeforeCustor() {
        for (int i = steps.size() - 1; i >= 0; i--) {
            final Step step = steps.get(i);
            if ((currentValue - min) >= step.getValue()) {
                return step;
            }
            break;
        }
        return null;
    }

    private Step findStepOfCustor() {
        for (int i = 0; i < steps.size(); ++i) {
            final Step step = steps.get(i);
            if ((currentValue - min) <= step.getValue()) {
                return step;
            }
        }
        return null;
    }

    private void onClick(MotionEvent e) {
        if (bubble.onClicked(e)) {
            onBubbleClicked();
        }
    }

    private String formatValue(float value) {
        return textFormatter.format(value);
    }

    private String formatRegionValue(int region, float value) {
        if (regionTextFormatter != null) {
            return regionTextFormatter.format(region, value);
        } else {
            return formatValue(value);
        }
    }

    private void drawText(Canvas canvas, String text, float x, float y, TextPaint paint, Layout.Alignment aligment) {
        canvas.save();
        {
            canvas.translate(x, y);
            final StaticLayout staticLayout = new StaticLayout(text, paint, (int) paint.measureText(text), aligment, 1.0f, 0, false);
            staticLayout.draw(canvas);
        }
        canvas.restore();
    }

    private void drawMultilineText(Canvas canvas, String text, float x, float y, TextPaint paint, Layout.Alignment aligment) {
        final float lineHeight = paint.getTextSize();
        float lineY = y;
        for (CharSequence line : text.split("\n")) {
            canvas.save();
            {
                final float lineWidth = (int) paint.measureText(line.toString());
                float lineX = x;
                if (aligment == Layout.Alignment.ALIGN_CENTER) {
                    lineX -= lineWidth / 2f;
                }
                if (lineX < 0) {
                    lineX = 0;
                }

                final float right = lineX + lineWidth;
                if (right > canvas.getWidth()) {
                    lineX = canvas.getWidth() - lineWidth - settings.getPaddingCorners();
                }

                canvas.translate(lineX, lineY);
                final StaticLayout staticLayout = new StaticLayout(line, paint, (int) lineWidth, aligment, 1.0f, 0, false);
                staticLayout.draw(canvas);

                lineY += lineHeight;
            }
            canvas.restore();
        }

    }

    private void drawIndicatorsTextAbove(Canvas canvas, String text, TextPaint paintText, float x, float y, Layout.Alignment alignment) {

        final float textHeight = calculateTextMultilineHeight(text, paintText);
        y -= textHeight;

        final int width = (int) paintText.measureText(text);
        if (x >= getWidth() - settings.getPaddingCorners()) {
            x = (getWidth() - width - settings.getPaddingCorners() / 2f);
        } else if (x <= 0) {
            x = width / 2f;
        } else {
            x = (x - width / 2f);
        }

        if (x < 0) {
            x = 0;
        }

        if (x + width > getWidth()) {
            x = getWidth() - width;
        }

        drawText(canvas, text, x, y, paintText, alignment);
    }

    private float calculateTextMultilineHeight(String text, TextPaint textPaint) {
        return text.split("\n").length * textPaint.getTextSize();
    }

    private float calculateBubbleTextWidth() {
        String bubbleText = formatValue(getCurrentValue());
        if (isEditing) {
            bubbleText = textEditing;
        }
        return settings.getPaintBubbleTextCurrent().measureText(bubbleText);
    }

    private void drawBubblePath(Canvas canvas, float triangleCenterX, float height, float width) {
        final Path path = new Path();

        int padding = 3;
        final Rect rect = new Rect(padding, padding, (int) width - padding, (int) (height - UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT)) - padding);

        final float roundRectHeight = (height - UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT)) / 2;

        path.moveTo(rect.left + roundRectHeight, rect.top);
        path.lineTo(rect.right - roundRectHeight, rect.top);
        path.quadTo(rect.right, rect.top, rect.right, rect.top + roundRectHeight);
        path.lineTo(rect.right, rect.bottom - roundRectHeight);
        path.quadTo(rect.right, rect.bottom, rect.right - roundRectHeight, rect.bottom);

        path.lineTo(triangleCenterX + UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_WIDTH) / 2f, height - UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT) - padding);
        path.lineTo(triangleCenterX, height - padding);
        path.lineTo(triangleCenterX - UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_WIDTH) / 2f, height - UtilKDisplay.INSTANCE.dp2px(BUBBLE_ARROW_HEIGHT) - padding);

        path.lineTo(rect.left + roundRectHeight, rect.bottom);
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - roundRectHeight);
        path.lineTo(rect.left, rect.top + roundRectHeight);
        path.quadTo(rect.left, rect.top, rect.left + roundRectHeight, rect.top);
        path.close();

        canvas.drawPath(path, settings.getPaintBubble());
    }

    private void drawBubble(Canvas canvas, float centerX, float triangleCenterX, float y) {
        final float width = this.bubble.getWidth();
        final float height = this.bubble.getHeight();

        canvas.save();
        {
            canvas.translate(centerX - width / 2f, y);
            triangleCenterX -= (centerX - width / 2f);

            if (!isEditing) {
                drawBubblePath(canvas, triangleCenterX, height, width);
            } else {
                final int savedColor = settings.getPaintBubble().getColor();

                settings.getPaintBubble().setColor(settings.getBubbleColorEditing());
                settings.getPaintBubble().setStyle(Paint.Style.FILL);
                drawBubblePath(canvas, triangleCenterX, height, width);

                settings.getPaintBubble().setStyle(Paint.Style.STROKE);
                settings.getPaintBubble().setColor(settings.getPaintIndicator().getColor());
                drawBubblePath(canvas, triangleCenterX, height, width);

                settings.getPaintBubble().setStyle(Paint.Style.FILL);
                settings.getPaintBubble().setColor(savedColor);
            }

            if (!isEditing) {
                final String bubbleText = formatValue(getCurrentValue());
                drawText(canvas, bubbleText, UtilKDisplay.INSTANCE.dp2px(BUBBLE_PADDING_HORIZONTAL), UtilKDisplay.INSTANCE.dp2px(BUBBLE_PADDING_VERTICAL) - 3, settings.getPaintBubbleTextCurrent(), Layout.Alignment.ALIGN_NORMAL);
            }
        }

        canvas.restore();

    }
}
