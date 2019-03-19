package com.example.qiaopc.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;

public class CameraResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        String path = getIntent().getStringExtra("picPath");
        ImageView imageView = findViewById(R.id.pic);
        try {
            FileInputStream fis = new FileInputStream(path);

            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
