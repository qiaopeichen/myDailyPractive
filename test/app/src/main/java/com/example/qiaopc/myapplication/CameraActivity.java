package com.example.qiaopc.myapplication;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.utils.ScreenUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView picture;

    private Button takePhoto;
    private Camera mCamera;
    private SurfaceHolder mHolder;

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/temp.png");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CameraActivity.this, CameraResultActivity.class);
                intent.putExtra("picPath", tempFile.getAbsolutePath());
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        initViewPos();

        initCameraInfo();
        Log.d("qiaopc", "getNumberOfCameras" + String.valueOf(Camera.getNumberOfCameras()));
    }

    @Nullable
    private Camera.CameraInfo mFrontCameraInfo = null;
    private int mFrontCameraId = -1;

    @Nullable
    private Camera.CameraInfo mBackCameraInfo = null;
    private int mBackCameraId = -1;

    private void initCameraInfo() {
        int numberOfCameras = Camera.getNumberOfCameras(); // 获取摄像头个数
        for (int cameraId = 0; cameraId < numberOfCameras; cameraId++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 后置摄像头信息
                mBackCameraId = cameraId;
                mBackCameraInfo = cameraInfo;
                Log.d("qiaopc", "mBackCameraId:" + mBackCameraId + " " + cameraId + " " + mBackCameraInfo.orientation);
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 前置摄像头信息
                mFrontCameraId = cameraId;
                mFrontCameraInfo = cameraInfo;
                Log.d("qiaopc", "mFrontCameraId:" + mFrontCameraId + " " + cameraId + " " + mFrontCameraInfo.orientation);
            }

        }
    }

    private void initViewPos() {
        ViewGroup.LayoutParams surLP = picture.getLayoutParams();
        surLP.width = ScreenUtil.getScreenSize(getApplicationContext()).widthPixels;
        surLP.height = (int) (surLP.width * 16 / 9f);
    }

    private void initView() {
        takePhoto = findViewById(R.id.take_photo);
        picture = findViewById(R.id.surface_view);
        mHolder = picture.getHolder();
        mHolder.addCallback(this);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder == null) {
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public void onClick(View view) {
        capture(view);
    }


    private void capture(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(800, 400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    /**
     * 获取camera对象
     *
     * @return
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            // 将系统camera预览角度进行调整
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);

        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
            Log.d("qiaopc", "supportedPreviewSizes:" + supportedPreviewSizes.get(i).width + ";" + supportedPreviewSizes.get(i).height);
        }
        Camera.Size closelyPreSize = getCloselyPreSize(width, height, supportedPreviewSizes);
        Log.d("qiaopc", "closelyPreSize:" + closelyPreSize.width + ";" + closelyPreSize.height);
        Log.d("qiaopc", "surfaceWidth:" + width + ";" + height);
        parameters.setPreviewSize(1920, 1080);
        mCamera.setParameters(parameters);
//        Camera.Parameters parameters = mCamera.getParameters();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth
     *            需要被进行对比的原宽
     * @param surfaceHeight
     *            需要被进行对比的原高
     * @param preSizeList
     *            需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    protected Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight,
                                            List<Camera.Size> preSizeList) {

        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (true) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for(Camera.Size size : preSizeList){
            if((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }
}
