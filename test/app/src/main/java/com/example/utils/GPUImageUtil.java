package com.example.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter;

/**
 * Created by qiaopc on 2019/2/19.
 */

public class GPUImageUtil {

    private static GPUImageFilter filter;

    // 饱和度，亮度等参数指数
    private static int count;

    /**
     * 获取过滤器
     * @param GPUFlag
     * @return 滤镜类型
     */
    public static GPUImageFilter getFilter(int GPUFlag) {
        switch (GPUFlag) {
            case 1:
                filter = new GPUImageGrayscaleFilter();
                break;
            case 2:
                filter = new GPUImageAddBlendFilter();
                break;
            case 3:
                filter = new GPUImageAlphaBlendFilter();
                break;
            case 4:
                filter = new GPUImageBilateralBlurFilter();
                break;
            case 5:
                filter = new GPUImageBoxBlurFilter();
                break;
            case 6:
                filter = new GPUImageBrightnessFilter();
                break;
            case 7:
                filter = new GPUImageBulgeDistortionFilter();
                break;
            case 8:
                filter = new GPUImageCGAColorspaceFilter();
                break;
            case 9:
                filter = new GPUImageChromaKeyBlendFilter();
                break;
            case 10:
                filter = new GPUImageColorBalanceFilter();
                break;
            case 11:
                filter = new GPUImageSaturationFilter(count);
                break;
            case 12:
                filter = new GPUImageBulgeDistortionFilter();
                ((GPUImageBulgeDistortionFilter)filter).setRadius(1.0f);
                ((GPUImageBulgeDistortionFilter)filter).setScale(0.6f);

                break;
        }
        return filter;
    }

    public static Bitmap getGPUImageFromURL(String url) {
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
    public static Bitmap getGPUImageFromAssets(Context context, GPUImage gpuImage, int filterFlag) {
        //获得Assets资源文件
        AssetManager as = context.getAssets();
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            // 注意名字要与图片名字一致
            is = as.open("bike_picture.jpg");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            Log.e("GPUImage", "Error:" + e);
        }

        // 使用GPUImage处理图像
        gpuImage = new GPUImage(context);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(getFilter(filterFlag));
        bitmap = gpuImage.getBitmapWithFilterApplied();
        return bitmap;
    }

    // 调整饱和度，亮度等
    public static void changeSaturation(int curCount) {
        GPUImageUtil.count = curCount;
    }
}
