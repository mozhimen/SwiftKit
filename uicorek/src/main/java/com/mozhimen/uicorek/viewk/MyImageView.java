package com.example.jnitest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {

    private Paint paint;
    private RectF rect;

    public MyImageView(Context context) {
        this(context, null);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect == null) {
            return;
        }
        canvas.drawRect(rect, paint);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);


    }


    //添加矩形
    public void addRect() {
        rect = new RectF(0, 0, 200, 200);
        invalidate();
    }


    float startX;
    float startY;
    PointType pointType;

    private enum PointType {
        CENTER,
        LEFT,
        LEFTTOP,
        LEFTBOTTOM,
        TOP,
        RIGHT,
        RIGHTTOP,
        RIGHTBOTTOM,
        BOTTOM;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
            if (!rect.contains(startX, startY)) {
                return super.dispatchTouchEvent(event);
            }
            pointType = delType(startX, startY);


        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float distanceX = event.getX() - startX;
            float distanceY = event.getY() - startY;
            delEvent(distanceX, distanceY, event.getX(), event.getY());
            if (pointType != PointType.CENTER){

                startX = Math.abs(event.getX());
                startY = Math.abs(event.getY());
            }
            invalidate();
        }
        return true;
    }

    private void delEvent(float endX, float endY, float pointX, float pointY) {
        switch (pointType) {
            case LEFT:
                left(endX, endY);
                break;
            case LEFTTOP:
                leftTop(endX, endY);
                break;

            case LEFTBOTTOM:

                leftBottom(endX, endY);
                break;

            case TOP:

                top(endX, endY);
                break;

            case RIGHT:

                right(endX, endY);
                break;

            case RIGHTTOP:

                rightTop(endX, endY);
                break;

            case RIGHTBOTTOM:

                rightBottom(endX, endY);
                break;

            case BOTTOM:

                bottom(endX, endY);
                break;
            default:
                center(endX, endY, pointX, pointY);
                break;
        }
    }

    //左边缩放
    private void left(float endX, float endY) {
        rect.left = rect.left + endX;
    }

    //左上边缩放
    private void leftTop(float endX, float endY) {

        rect.left = rect.left + endX;
        rect.top = rect.top + endY;
    }

    //左下角缩放
    private void leftBottom(float distanceX, float distanceY) {
        rect.left = rect.left + distanceX;
        rect.bottom = rect.bottom + distanceY;
    }

    //上边缩放
    private void top(float distanceX, float distanceY) {
        rect.top = rect.top + distanceY;
    }

    //右上边缩放
    private void rightTop(float distanceX, float distanceY) {
        rect.right = rect.right + distanceX;
        rect.top = rect.top + distanceY;
    }

    //右边缩放
    private void right(float distanceX, float distanceY) {
        rect.right = rect.right + distanceX;
    }

    //右下边缩放
    private void rightBottom(float distanceX, float distanceY) {
        rect.right = rect.right + distanceX;
        rect.bottom = rect.bottom + distanceY;
    }

    //下边缩放
    private void bottom(float distanceX, float distanceY) {
        rect.bottom = rect.bottom + distanceY;
    }


    private void center(float distanceX, float distanceY, float pointX, float pointY) {
        float newLeft = rect.left + distanceX;
        float newTop = rect.top + distanceY;
        float newRight = rect.right + distanceX;
        float newBottom = rect.bottom + distanceY;
        if (newLeft < 0 || newTop < 0 || newRight > getWidth() || newBottom > getHeight()){
            return ;
        }


        startX = Math.abs(pointX);
        startY = Math.abs(pointY);
        rect.set(newLeft, newTop, newRight, newBottom);
        invalidate();
    }


    private int distance = 20;
    private PointType delType(float x, float y) {
        if (Math.abs(x - rect.left) < distance && Math.abs(y - rect.top) > distance && Math.abs(y - rect.bottom) > distance) {
            return PointType.LEFT;
        } else if (Math.abs(x - rect.left) < distance && Math.abs(y - rect.top) < distance) {
            return PointType.LEFTTOP;
        } else if (Math.abs(x - rect.left) < distance && Math.abs(y - rect.bottom) < distance) {
            return PointType.LEFTBOTTOM;
        } else if (Math.abs(x - rect.right) < distance && Math.abs(y - rect.top) > distance && Math.abs(y - rect.bottom) > distance) {
            return PointType.RIGHT;
        } else if (Math.abs(x - rect.right) < distance && Math.abs(y - rect.top) < distance) {
            return PointType.RIGHTTOP;
        } else if (Math.abs(x - rect.right) < distance && Math.abs(y - rect.bottom) < distance) {
            return PointType.RIGHTBOTTOM;
        } else if(Math.abs(x - rect.left) > distance && Math.abs(y - rect.top) < distance && Math.abs(x - rect.right) > distance){
            return PointType.TOP;
        } else if(Math.abs(x - rect.right) > distance && Math.abs(y - rect.bottom) < distance && Math.abs(x - rect.left) > distance){
            return PointType.BOTTOM;
        } else {
            return PointType.CENTER;
        }
    }

}
