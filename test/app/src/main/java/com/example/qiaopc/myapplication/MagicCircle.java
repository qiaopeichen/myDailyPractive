package com.example.qiaopc.myapplication;

/**
 * Created by qiaopc on 2019/2/20.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * 代码写的比较仓猝,以后再优化写法和补充那些数值的具体含义,求勿喷QAQ
 */
public class MagicCircle extends View {

    private Path mPath;
    private Paint mFillCirclePaint;

    /**
     * View的宽度
     **/
    private int width;
    /**
     * View的高度，这里View应该是正方形，所以宽高是一样的
     **/
    private int height;
    /**
     * View的中心坐标x
     **/
    private int centerX;
    /**
     * View的中心坐标y
     **/
    private int centerY;

    private float maxLength;
    private float mInterpolatedTime;
    private float stretchDistance;
    private float moveDistance;
    private float cDistance;
    private float radius;
    private float c;
    private float blackMagic = 0.551915024494f;
    private VPoint p2, p4; // 水平方向
    private HPoint p1, p3; // 竖直方向

    public MagicCircle(Context context) {
        this(context, null, 0);
    }

    public MagicCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagicCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFillCirclePaint = new Paint();
        mFillCirclePaint.setColor(0xFFfe626d);
        mFillCirclePaint.setStyle(Paint.Style.FILL);
        mFillCirclePaint.setStrokeWidth(1);
        mFillCirclePaint.setAntiAlias(true);
        mPath = new Path();
        p2 = new VPoint();
        p4 = new VPoint();

        p1 = new HPoint();
        p3 = new HPoint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        centerX = width / 2;
        centerY = height / 2;
        radius = height/2;
        c = radius * blackMagic;
        stretchDistance = radius;
        moveDistance = radius * (3 / 5f);
        cDistance = c * 0.45f;
        maxLength = width - radius - radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        canvas.translate(radius / 2, radius); // 画布事先平移了

        if (mInterpolatedTime >= 0 && mInterpolatedTime * width <= radius / 2) {
            model1(mInterpolatedTime * width);
        } else if (mInterpolatedTime * width > radius / 2 && mInterpolatedTime * width <= radius) {
            model2(mInterpolatedTime * width);
        } else if (mInterpolatedTime * width > radius && mInterpolatedTime * width <= width - radius * 1.5) {
            model3(mInterpolatedTime * width);
        } else if (mInterpolatedTime * width > width - radius * 1.5 && mInterpolatedTime * width <= width - radius) {
            model4(mInterpolatedTime * width);
        } else if (mInterpolatedTime * width > width - radius && mInterpolatedTime * width <= width - radius/2) {
            model5(mInterpolatedTime * width);
        } else if (mInterpolatedTime * width > width - radius/2 && mInterpolatedTime * width <= width) {
            model5(width - radius/2);
        }

        mPath.moveTo(p1.x, p1.y);
        mPath.cubicTo(p1.right.x, p1.right.y, p2.bottom.x, p2.bottom.y, p2.x, p2.y);
        mPath.cubicTo(p2.top.x, p2.top.y, p3.right.x, p3.right.y, p3.x, p3.y);
        mPath.cubicTo(p3.left.x, p3.left.y, p4.top.x, p4.top.y, p4.x, p4.y);
        mPath.cubicTo(p4.bottom.x, p4.bottom.y, p1.left.x, p1.left.y, p1.x, p1.y);

        Log.d("qiaopc1", p1.x + " " + p1.y);
        Log.d("qiaopc2", p1.right.x + " " + p1.right.y + " " + p2.bottom.x + " " + p2.bottom.y + " " + p2.x + " " + p2.y);
        Log.d("qiaopc3", p3.left.x + " " + p3.left.y + " " + p4.top.x + " " + p4.top.y + " " + p4.x + " " + p4.y);

        canvas.drawPath(mPath, mFillCirclePaint);

    }

    private void model0() { // 初始状态靠墙
        p1.setY(radius);
        p3.setY(-radius);
        p3.x = p1.x = 0;
        p3.left.x = p1.left.x = -c;
        p3.right.x = p1.right.x = c;

        p2.setX(radius);
        p4.setX(-radius / 2);
        p2.y = p4.y = 0;
        p2.top.y = p4.top.y = -c;
        p2.bottom.y = p4.bottom.y = c;
    }

    private void model1(float distance) { // 刚离开墙 变成圆
        model0();
        p3.adjustAllX(distance);
        p1.adjustAllX(distance);
        p2.adjustAllX(distance);
        Log.d("qiaopcdistance1", "distance:" + distance);
    }

    private void model2(float distance) { // 圆变椭圆
        model1(radius / 2);
        p2.adjustAllX(distance - radius / 2);
        p3.adjustAllX((distance - radius / 2) / 2);
        p1.adjustAllX((distance - radius / 2) / 2);
        p2.adjustY(distance * 0.1f);
        p4.adjustY(distance * 0.1f);
        Log.d("qiaopcdistance2", "distance:" + distance);

    }

    private void model3(float distance) { // 椭圆滑行
        model2(radius);
        p2.adjustAllX(distance - radius);
        p3.adjustAllX(distance - radius);
        p1.adjustAllX(distance - radius);
        p4.adjustAllX(distance - radius);
    }

    private void model4(float distance) { // 椭圆变圆
        Log.d("model4", "start");
        model3(width - radius * 1.5f);
        p4.adjustAllX(distance - (width - radius * 1.5f));
        p3.adjustAllX((distance - (width - radius * 1.5f)) / 2);
        p1.adjustAllX((distance - (width - radius * 1.5f)) / 2);
        p2.adjustY(-(width - distance) * 0.1f);
        p4.adjustY(-(width - distance) * 0.1f);
    }

    private void model5(float distance) {
        model4(width - radius);
        p3.adjustAllX(distance - (width - radius));
        p4.adjustAllX(distance - (width - radius));
        p1.adjustAllX(distance - (width - radius));
    }

    class VPoint {
        public float x;
        public float y;
        public PointF top = new PointF();
        public PointF bottom = new PointF();

        public void setX(float x) {
            this.x = x;
            top.x = x;
            bottom.x = x;
        }

        public void adjustY(float offset) {
            top.y -= offset;
            bottom.y += offset;
        }

        public void adjustAllX(float offset) {
            this.x += offset;
            top.x += offset;
            bottom.x += offset;
        }
    }

    class HPoint {
        public float x;
        public float y;
        public PointF left = new PointF();
        public PointF right = new PointF();

        public void setY(float y) {
            this.y = y;
            left.y = y;
            right.y = y;
        }

        public void adjustAllX(float offset) {
            this.x += offset;
            left.x += offset;
            right.x += offset;
        }
    }

    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }

    public void startAnimation() {
        mPath.reset();
        mInterpolatedTime = 0;
        MoveAnimation move = new MoveAnimation();
        move.setDuration(3000);
        move.setInterpolator(new LinearInterpolator());
        move.setRepeatCount(Animation.INFINITE);
        move.setRepeatMode(Animation.REVERSE);
        startAnimation(move);
    }

    public void setInterpolatedTime(float interpolatedTime) {
        interpolatedTime = interpolatedTime > 1 ? 1 : interpolatedTime;
        interpolatedTime = interpolatedTime < 0 ? 0 : interpolatedTime;
        mInterpolatedTime = interpolatedTime;
        Log.d("mInterpolatedTime", " " + mInterpolatedTime);
        invalidate();
    }
}