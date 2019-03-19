package com.example.qiaopc.myapplication;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.utils.Constants;
import com.example.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_120SG;
    private Button btn_ll;
    private Button btn_surface_view;
    private Button btn_recycler_view;
    private Button btn_anim;
    private Button btn_gpu_image;
    private Button btn_bessel;
    private Button btn_camera;
    private Button btn_camera2;
    private Button btn_openGLES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initListener();
        checkRequiredPermission();
    }

    private void initView() {
        btn_120SG = findViewById(R.id.btn_120SG);
        btn_ll = findViewById(R.id.btn_ll);
        btn_surface_view = findViewById(R.id.btn_surface_view);
        btn_recycler_view = findViewById(R.id.btn_recycler_view);
        btn_anim = findViewById(R.id.btn_anim);
        btn_gpu_image = findViewById(R.id.btn_gpu_image);
        btn_bessel = findViewById(R.id.btn_bessel);
        btn_camera = findViewById(R.id.btn_camera);
        btn_camera2 = findViewById(R.id.btn_camera2);
        btn_openGLES = findViewById(R.id.btn_openGLES);
    }

    private void initListener() {
        btn_120SG.setOnClickListener(this);
        btn_ll.setOnClickListener(this);
        btn_surface_view.setOnClickListener(this);
        btn_recycler_view.setOnClickListener(this);
        btn_anim.setOnClickListener(this);
        btn_gpu_image.setOnClickListener(this);
        btn_bessel.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_camera2.setOnClickListener(this);
        btn_openGLES.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_120SG:
                startActivity(new Intent(this, Main0Activity.class));
                break;
            case R.id.btn_ll:
                startActivity(new Intent(this, Main3Activity.class));
                break;
            case R.id.btn_surface_view:
                startActivity(new Intent(this, SurfaceActivity.class));
                break;
            case R.id.btn_recycler_view:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btn_anim:
                startActivity(new Intent(this, AnimActivity.class));
                break;
            case R.id.btn_gpu_image:
                startActivity(new Intent(this, GpuImageActivity.class));
                break;
            case R.id.btn_bessel:
                startActivity(new Intent(this, BesselActivity.class));
                break;
            case R.id.btn_camera:
                startActivity(new Intent(this, CameraActivity.class));
                break;
            case R.id.btn_camera2:
                startActivity(new Intent(this, Camera2Activity.class));
                break;
            case R.id.btn_openGLES:
                startActivity(new Intent(this, OpenGLES20Activity.class));
                break;
        }
    }

    public void checkRequiredPermission() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                });
    }
}
