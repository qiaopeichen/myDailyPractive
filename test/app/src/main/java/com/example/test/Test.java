package com.example.test;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.example.qiaopc.myapplication.Main0Activity;
import com.example.qiaopc.myapplication.R;

/**
 * Created by qiaopc on 2019/2/15.
 */

public class Test extends Main0Activity {
    public void Test() {
        // 视图动画
        AnimationSet as = new AnimationSet(true);
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);

        View view = new View(getApplicationContext());

        view.startAnimation(as);


        // 属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
                "translationX",
                300);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,
                "rotation",90);
        animator1.setDuration(300);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,
                "pivotX", 0.5f);
        animator2.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, animator1, animator2);

        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 300f);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvh1, pvh2).setDuration(1000).start();

        // valueAnimator
        ValueAnimator va = ValueAnimator.ofFloat(0, 100);
        va.setTarget(view);
        va.setDuration(1000).start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object value = animation.getAnimatedValue();
                // TODO use the value
            }
        });
        Animator animator3 = AnimatorInflater.loadAnimator(this, R.animator.scalex);
        animator3.setTarget(view);
        animator.start();



    }
}
