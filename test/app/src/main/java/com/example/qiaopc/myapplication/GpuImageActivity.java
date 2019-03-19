package com.example.qiaopc.myapplication;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.utils.GPUImageUtil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;

public class GpuImageActivity extends AppCompatActivity {

    private GPUImage gpuImage;
    // 显示处理结果
    private ImageView resultIv;
    // 进度条
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpu_image);
        resultIv = findViewById(R.id.resultIv);
        gpuImage = new GPUImage(getApplicationContext());
//        seekBar = this.findViewById(R.id.seekbar);
//        seekBar.setMax(10);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                //通过进度条的值更改饱和度
//                resultIv.setImageBitmap(getGPUImageFromAssets(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        //初始化图片
//        resultIv.setImageBitmap(getGPUImageFromAssets(0));

        // 开启异步线程加载图片并处理
//        MyAsynTask asynTask = new MyAsynTask();
//        asynTask.execute();

        Bitmap bitmap = GPUImageUtil.getGPUImageFromAssets(getApplicationContext(), gpuImage, 12);

        resultIv.setImageBitmap(bitmap);
    }


    private class MyAsynTask extends AsyncTask<Integer, Integer, Bitmap>{

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            Bitmap bitmap = getGPUImageFromURL("http://pic36.nipic.com/20131225/15361977_174053547194_2.jpg");
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 使用GPUImage处理图像
            gpuImage = new GPUImage(getApplicationContext());
            gpuImage.setImage(bitmap);
            gpuImage.setFilter(new GPUImageGrayscaleFilter());
            bitmap = gpuImage.getBitmapWithFilterApplied();
            // 显示处理后的图片
            resultIv.setImageBitmap(bitmap);
        }
    }

    private Bitmap getGPUImageFromURL(String url) {
        Bitmap bitmap = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 根据传进来的树枝设置素材饱和度
    private Bitmap getGPUImageFromAssets(int progress) {
        //获得Assets资源文件
        AssetManager as = getAssets();
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            // 注意名字要与图片名字一致
            is = as.open("test.jpg");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            Log.e("GPUImage", "Error:" + e);
        }

        // 使用GPUImage处理图像
        gpuImage = new GPUImage(this);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageBrightnessFilter(progress * 0.1f));
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

}
