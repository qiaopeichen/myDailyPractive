package com.example.qiaopc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by qiaopc on 2019/1/30.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // SurfaceHolder
    private SurfaceHolder mHolder;
    // 用于绘图的Canvas
    private Canvas mCanvas;
    // 子线程标志位
    private boolean mIsDrawing;
    private Path mPath;
    private Paint mPaint;
    private int x;
    private double y;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
//        mHolder.setFormat(PixelFormat.OPAQUE);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        mPath.moveTo(0, 400);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
            x += 1;
            y = 100 * Math.sin(x * 2 * Math.PI / 180) + 400;
            Log.d("qiaopc", " x:" + x + "  " + "y:" + y);
            mPath.lineTo(x, (float) y);
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            // drawsomthing
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
