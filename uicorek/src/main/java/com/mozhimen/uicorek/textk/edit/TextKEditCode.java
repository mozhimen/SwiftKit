package com.mozhimen.uicorek.textk.edit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.mozhimen.basick.utilk.android.util.UtilKDisplayMetrics;
import com.mozhimen.uicorek.R;

/**
 * @ClassName TextKEditRect
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/3 18:03
 * @Version 1.0
 */
public class TextKEditCode extends androidx.appcompat.widget.AppCompatEditText {

    public interface OnTextFinishListener {

        void onTextFinish(CharSequence text, int length);
    }

    private int mTextColor;
    // 输入的最大长度
    private int mMaxLength;
    // 边框宽度
    private int mStrokeWidth;
    // 边框高度
    private int mStrokeHeight;
    // 边框之间的距离
    private int mStrokePadding;
    // 光标宽度
    private int mCursorWidth;
    // 光标高度
    private int mCursorHeight;
    // 方框的背景
    private Drawable mStrokeDrawable;
    // 光标的背景
    private Drawable mCursorDrawable;
    // 输入结束监听
    private OnTextFinishListener mOnInputFinishListener;
    // 是否光标获取焦点
    private boolean mCursorStateFocused = true;
    // 记录上次光标获取焦点时间
    private long mLastCursorFocusedTimeMillis = System.currentTimeMillis();

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
    }

    public void setStrokeHeight(int strokeHeight) {
        mStrokeHeight = strokeHeight;
    }

    public void setStrokePadding(int strokePadding) {
        mStrokePadding = strokePadding;
    }

    public void setCursorWidth(int cursorWidth) {
        mCursorWidth = cursorWidth;
    }

    public void setCursorHeight(int cursorHeight) {
        mCursorHeight = cursorHeight;
    }

    public void setStrokeDrawable(Drawable strokeDrawable) {
        mStrokeDrawable = strokeDrawable;
    }

    public void setCursorDrawable(Drawable cursorDrawable) {
        mCursorDrawable = cursorDrawable;
    }

    /**
     * 设置最大长度
     */
    public void setMaxLength(int maxLength) {
        mMaxLength = maxLength;
        if (maxLength >= 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            setFilters(new InputFilter[0]);
        }
    }

    /**
     * 构造方法
     */
    public TextKEditCode(Context context) {
        this(context, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public TextKEditCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStrokeWidth =(int) UtilKDisplayMetrics.dp2px( 30);
        mStrokeHeight = (int) UtilKDisplayMetrics.dp2px( 30);
        mStrokePadding = (int) UtilKDisplayMetrics.dp2px(  10);
        mCursorWidth = (int) UtilKDisplayMetrics.dp2px(  1);
        mCursorHeight = (int) UtilKDisplayMetrics.dp2px(  20);
        mStrokeDrawable = context.getResources().getDrawable(R.drawable.textk_edit_rect_bg);
        mCursorDrawable = context.getResources().getDrawable(R.drawable.textk_edit_rect_cursor);
        mMaxLength = 6;

        setMaxLength(mMaxLength);
        setLongClickable(false);
        // 去掉背景颜色
        setBackgroundColor(Color.TRANSPARENT);
        // 不显示光标
        setCursorVisible(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 判断高度是否小于推荐高度
        if (height < mStrokeHeight) {
            height = mStrokeHeight;
        }

        // 判断高度是否小于推荐宽度
        int recommendWidth = mStrokeWidth * mMaxLength + mStrokePadding * (mMaxLength - 1);
        if (width < recommendWidth) {
            width = recommendWidth;
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTextColor = getCurrentTextColor();
        setTextColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        setTextColor(mTextColor);
        // 重绘背景颜色
        drawStrokeBackground(canvas);
        drawCursorBackground(canvas);
        // 重绘文本
        drawText(canvas);
    }


    /**
     * 重绘背景
     */
    private void drawStrokeBackground(Canvas canvas) {
        Rect mRect = new Rect();
        if (mStrokeDrawable != null) {
            // 绘制方框背景
            mRect.left = 0;
            mRect.top = 0;
            mRect.right = mStrokeWidth;
            mRect.bottom = mStrokeHeight;
            int count = canvas.getSaveCount();
            canvas.save();
            for (int i = 0; i < mMaxLength; i++) {
                mStrokeDrawable.setBounds(mRect);
                mStrokeDrawable.setState(new int[]{android.R.attr.state_enabled});
                mStrokeDrawable.draw(canvas);
                float dx = mRect.right + mStrokePadding;
                // 移动画布
                canvas.save();
                canvas.translate(dx, 0);
            }
            canvas.restoreToCount(count);
            canvas.translate(0, 0);

            // 绘制激活状态
            // 当前激活的索引
            int activatedIndex = Math.max(0, getEditableText().length());
            if (activatedIndex < mMaxLength) {
                mRect.left = mStrokeWidth * activatedIndex + mStrokePadding * activatedIndex;
                mRect.right = mRect.left + mStrokeWidth;
                mStrokeDrawable.setState(new int[]{android.R.attr.state_focused});
                mStrokeDrawable.setBounds(mRect);
                mStrokeDrawable.draw(canvas);
            }
        }
    }

    /**
     * 重绘光标
     */
    private void drawCursorBackground(Canvas canvas) {
        Rect mRect = new Rect();
        if (mCursorDrawable != null) {
            // 绘制光标
            mRect.left = (mStrokeWidth - mCursorWidth) / 2;
            mRect.top = (mStrokeHeight - mCursorHeight) / 2;
            mRect.right = mRect.left + mCursorWidth;
            mRect.bottom = mRect.top + mCursorHeight;
            int count = canvas.getSaveCount();
            canvas.save();
            for (int i = 0; i < mMaxLength; i++) {
                mCursorDrawable.setBounds(mRect);
                mCursorDrawable.setState(new int[]{android.R.attr.state_enabled});
                mCursorDrawable.draw(canvas);
                float dx = mRect.right + mStrokePadding;
                // 移动画布
                canvas.save();
                canvas.translate(dx, 0);
            }
            canvas.restoreToCount(count);
            canvas.translate(0, 0);

            // 绘制激活状态
            // 当前激活的索引
            int activatedIndex = Math.max(0, getEditableText().length());
            if (activatedIndex < mMaxLength) {
                mRect.left = mStrokeWidth * activatedIndex + mStrokePadding * activatedIndex + (mStrokeWidth - mCursorWidth) / 2;
                mRect.right = mRect.left + mCursorWidth;
                int[] state = new int[]{isFocusable() && isFocusableInTouchMode() && mCursorStateFocused ? android.R.attr.state_focused : android.R.attr.state_enabled};
                mCursorDrawable.setState(state);
                mCursorDrawable.setBounds(mRect);
                mCursorDrawable.draw(canvas);
                if ((System.currentTimeMillis() - mLastCursorFocusedTimeMillis) >= 800) {
                    mCursorStateFocused = !mCursorStateFocused;
                    mLastCursorFocusedTimeMillis = System.currentTimeMillis();
                }
            }
        }
    }


    /**
     * 重绘文本
     */
    private void drawText(Canvas canvas) {
        Rect mRect = new Rect();
        int count = canvas.getSaveCount();
        canvas.translate(0, 0);
        int length = getEditableText().length();
        for (int i = 0; i < length; i++) {
            String text = String.valueOf(getEditableText().charAt(i));
            TextPaint textPaint = getPaint();
            textPaint.setColor(mTextColor);
            // 获取文本大小
            textPaint.getTextBounds(text, 0, 1, mRect);
            // 计算(x,y) 坐标
            int x = mStrokeWidth / 2 + (mStrokeWidth + mStrokePadding) * i - (mRect.centerX());
            int y = (canvas.getHeight() + mRect.height()) / 2;
            canvas.drawText(text, x, y, textPaint);
        }
        canvas.restoreToCount(count);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        // 当前文本长度
        int textLength = getEditableText().length();
        if (textLength == mMaxLength) {
            clearFocus();
            hideSoftInput();
            if (mOnInputFinishListener != null) {
                mOnInputFinishListener.onTextFinish(getEditableText().toString(), mMaxLength);
            }
        }
    }


    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置输入完成监听
     */
    public void setOnTextFinishListener(OnTextFinishListener onInputFinishListener) {
        this.mOnInputFinishListener = onInputFinishListener;
    }
}
