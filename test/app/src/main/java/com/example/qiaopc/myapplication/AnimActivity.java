package com.example.qiaopc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.customAnim.CustomAnim;
import com.example.customAnim.CustomTV;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
    }

    public void btnAnim(View view) {
        CustomAnim customAnim = new CustomAnim();
        customAnim.setRotateY(30);
        view.startAnimation(customAnim);
    }

    public void imgClose(View view) {
        CustomTV customTV = new CustomTV();
        view.startAnimation(customTV);
    }
}
