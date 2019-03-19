package com.example.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by qiaopc on 2018/11/29.
 */

public abstract class BaseProgressBar {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int CIRCLE = 2;

    protected Paint mPaint;

    /**
     * 构造方法内完成一些具体的初始化信息
     */
    protected BaseProgressBar() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    public abstract int getMeasureHeight();

    public abstract int getMeasureWidth();

    public abstract void draw(View view, Canvas canvas);
}
