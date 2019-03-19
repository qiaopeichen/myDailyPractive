package com.example.qiaopc.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by qiaopc on 2019/1/7.
 */

public class SwitchCameraName120SGView extends FrameLayout {
    public static final int CAMERA_NAME_BACK = 0;  // 前置
    public static final int CAMERA_NAME_FRONT = 1; // 后置

    private int state;
    private ImageView mIvBg;
    private ImageView mIvBtn;

    private int mDownX;
    private int mLastX;
    private int mScrollLength;

    public SwitchCameraName120SGView(@NonNull Context context) {
        this(context, null);
    }

    public SwitchCameraName120SGView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchCameraName120SGView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_switch_camera_name_120sg, this);
        mIvBg = findViewById(R.id.switch_bg);
        mIvBtn = findViewById(R.id.switch_btn);
//
//        String bgPath = ImageNameManager.getInstance().queryImgPah("", 0);
//        String btnPath = ImageNameManager.getInstance().queryImgPah("", 0);
//
//        Glide.with(getContext()).load(bgPath).dontAnimate().into(mIvBg);
//        Glide.with(getContext()).load(btnPath).dontAnimate().into(mIvBtn);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = (int) event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mLastX = (int) event.getX();
                        if (Math.abs(mLastX - mDownX) > mScrollLength) {
                            if (mLastX - mDownX < 0) {
                                if (state == CAMERA_NAME_BACK) {
                                    setState(CAMERA_NAME_FRONT, true);
                                }
                                mDownX = mLastX;
                            } else if (mLastX - mDownX > 0) {
                                if (state == CAMERA_NAME_FRONT) {
                                    setState(CAMERA_NAME_BACK, true);
                                }
                                mDownX = mLastX;
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScrollLength = (int) (h / 2f);
    }

    public void setState(int state, boolean anim) {
        this.state = state;
        int duration = 0;
        if (anim) {
            duration = 200;
        }
        if (state == CAMERA_NAME_BACK) {
            rotateAnimate(mIvBtn, 0, duration);
        } else {
            rotateAnimate(mIvBtn, 30, duration);
        }
    }

    public static void rotateAnimate(View view, float degree, int duration) {
        ViewCompat
                .animate(view)
                .rotation(degree)
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .start();
    }

    public int getState() {
        return state;
    }
}
