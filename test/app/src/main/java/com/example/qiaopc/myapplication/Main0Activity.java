package com.example.qiaopc.myapplication;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class Main0Activity extends AppCompatActivity {

    public static final int CAMERA_NAME_BACK = 0;  // 前置
    public static final int CAMERA_NAME_FRONT = 1; // 后置

    private int state;

    View imageView;
    View imageViewParent;
    private double mScrollLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        initView();
        setOnClickListener();
    }

    private void initView() {
        imageViewParent = findViewById(R.id.iv_test_parent);
        imageView = findViewById(R.id.iv_test);
    }

    private void setOnClickListener() {
        mScrollLength = 150;


        imageView.setOnTouchListener(new View.OnTouchListener() {
            private int mLastX;
            private int mDownX;
            boolean hasMoved;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("qiaopc", "down");
                        mDownX = (int) event.getRawX();
                        hasMoved = false;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mLastX = (int) event.getRawX();
                        Log.d("qiaopc", "move:" +"downX:" + mDownX + " lastX:" + mLastX);

                        if (Math.abs(mLastX - mDownX) > mScrollLength) {
                            hasMoved = true;
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
                        Log.d("qiaopc", "up");
                        if (!hasMoved) {
                            Log.d("qiaopc", " !hasMoved");
                            if (state == CAMERA_NAME_BACK) {
                                setState(CAMERA_NAME_FRONT, true);
                            } else {
                                setState(CAMERA_NAME_BACK, true);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("qiaopc", "cancel");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void setState(int state, boolean anim) {
        this.state = state;
        int duration = 0;
        if (anim) {
            duration = 200;
        }
        if (state == CAMERA_NAME_BACK) {
            rotateAnimate(imageViewParent, 0, duration);
        } else {
            rotateAnimate(imageViewParent, 30, duration);
        }
    }

    public static void rotateAnimate(View view, float degree, int duration) {
        ViewCompat
                .animate(view)
                .rotation(degree)
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .start();

//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 1f, 0f, 1f);
//        animator.setDuration(5000);
//        animator.start();

//        Animation rotate;
//        if (degree > 0) {
//            rotate = new RotateAnimation(0f, 30f,
//                    Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.2f);
//        } else {
//            rotate = new RotateAnimation(30f, 0f,
//                    Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.2f);
//        }
//        LinearInterpolator lin = new LinearInterpolator();
//        rotate.setInterpolator(lin); //设置插值器
//        rotate.setDuration(200);//设置动画持续周期
//        rotate.setRepeatCount(0);//设置重复次数
//        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//
//        view.setAnimation(rotate);
//        view.startAnimation(rotate);
    }





//    int input = 0;
//    int count = 0;
//    int inputTemp = 0;
//    List gtList = new ArrayList();
//    public static final int MODE_JIA = 0;
//    public static final int MODE_JIAN = 1;
//    public static final int MODE_CHENG = 2;
//    public static final int MODE_CHU = 3;
//    public static final int MODE_EQUAL = 4;
//    public static int MODE_NULL = -1;
//    public static int MODE;
//    public static boolean inCalcuteMode = false;
//    public int btnNum = 0;

//    @Override
//    public void onClick(View v) {
//        Log.d("qiaopc", "v.id = " + v.getId());
//        switch (v.getId()) {
//            case R.id.btn_on:
//                initData();
//                break;
//            case R.id.btn_ce:
//                input = 0;
//                setText(input);
//            case R.id.btn_back:
//                input /= 10;
//                setText(input);
//                break;
//            case R.id.btn_1:
//            case R.id.btn_2:
//            case R.id.btn_3:
//            case R.id.btn_4:
//            case R.id.btn_5:
//            case R.id.btn_6:
//            case R.id.btn_7:
//            case R.id.btn_8:
//            case R.id.btn_9:
//                btnNum = Integer.parseInt(((TextView) v).getText().toString());
//                if ((MODE == MODE_JIA || MODE == MODE_JIAN || MODE == MODE_CHENG || MODE == MODE_CHU)
//                        && !inCalcuteMode) {
//                    inputTemp = input;
//                    input = 0;
//                    inCalcuteMode = true;
//                }
//                if (MODE == MODE_EQUAL) {
//                    input = 0;
//                }
//                input *= 10;
//                input += btnNum;
//                setText(input);
//                break;
//            case R.id.btn_jia:
//                MODE = MODE_JIA;
//                break;
//            case R.id.btn_jian:
//                MODE = MODE_JIAN;
//                break;
//            case R.id.btn_cheng:
//                MODE = MODE_CHENG;
//                break;
//            case R.id.btn_chu:
//                MODE = MODE_CHU;
//                break;
//            case R.id.btn_equal:
//                inCalcuteMode = false;
//                switch (MODE) {
//                    case MODE_JIA:
//                        count = inputTemp + input;
//                        break;
//                    case MODE_JIAN:
//                        count = inputTemp - input;
//                        break;
//                    case MODE_CHENG:
//                        count = inputTemp * input;
//                        break;
//                    case MODE_CHU:
//                        count = inputTemp / input;
//                        break;
//                }
//                input = count;
//                setText(count);
//                MODE = MODE_EQUAL;
//                break;
//        }
//    }

//    private void initData() {
//        MODE = MODE_NULL;
//        text.setText("0");
//        input = 0;
//        inCalcuteMode = false;
//        gtList.clear();
//    }
//
//    private void setText(int value) {
//        text.setText(String.valueOf(value));
//    }
}
