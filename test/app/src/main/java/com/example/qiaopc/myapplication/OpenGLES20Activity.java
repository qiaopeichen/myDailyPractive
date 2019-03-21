package com.example.qiaopc.myapplication;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.example.qiaopc.opengl.Square;
import com.example.qiaopc.opengl.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by qiaopc on 2019/3/18.
 */

public class OpenGLES20Activity extends AppCompatActivity {
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it. 创建一个GLSurfaceView实例并将其设置为此Activity的ContentView。
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(getApplicationContext());
        setContentView(mGLView);
    }

    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView 设置渲染器以在GLSurfaceView上绘制
            setRenderer(mRenderer);

            // Render the view only when there is a change in the drawing data 仅在绘图数据发生更改时才渲染视图
//            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private float mPreviousX;
        private float mPreviousY;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.
            // MotionEvent报告触摸屏和其他输入控件的输入详细信息。
            // 在这种情况下，您只对触摸位置发生变化的事件感兴趣。
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    // reverse direction of rotation above the mid-line 在中线上方反向旋转
                    if (y > getHeight() / 2) {
                        dx = dx * -1;
                    }
                    // reverse direction of rotation to left of the mid-line 反向旋转到中线左侧
                    if (x < getWidth() / 2) {
                        dy = dy * -1;
                    }
                    mRenderer.setAngle(
                            mRenderer.getAngle() +
                                    ((dx + dy) * TOUCH_SCALE_FACTOR));
                    requestRender();
            }
            mPreviousX = x;
            mPreviousY = y;
            return true;
        }
    }

    public static class MyGLRenderer implements GLSurfaceView.Renderer {

        private Triangle mTriangle;
        private Square mSquare;

        public volatile float mAngle;

        public float getAngle() {
            return mAngle;
        }

        public void setAngle(float angle) {
            mAngle = angle;
        }

        // 上面我们创建了着色器的编译代码，代码编写完成，需要写个方法来执行这段代码，这里我们在渲染器中写一个如下方法来执行着色器代码
        public static int loadShader(int type, String shaderCode) {

            // create a vertex shader type (GLES20.GL_VERTEX_SHADER) 创建顶点着色器类型（GLES20.GL_VERTEX_SHADER）
            // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER) 或片段着色器类型（GLES20.GL_FRAGMENT_SHADER）
            int shader = GLES20.glCreateShader(type);

            // add the source code to the shader and compile it 将源代码添加到着色器并进行编译
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);

            return shader;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background frame color
            GLES20.glClearColor(0.0f, 0.0f, 0f, 1.0f);

            // initialize a triangle
            mTriangle = new Triangle();
            // initialize a square
            mSquare = new Square();
        }

        private float[] mRotationMatrix = new float[16];

        @Override
        public void onDrawFrame(GL10 gl) {
            float[] scratch = new float[16];

            // Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            // Set the camera position (View matrix) // 定义一个相机视图
            Matrix.setLookAtM(mViewMatrix, // 接收相机变换矩阵
                    0, // 变换矩阵的起始位置（偏移量）
                    0, 0, -3, // 相机位置
                    0f, 0f, 0f, // 观察点位置
                    0f, 1f, 1.0f); // up向量在xyz上的分量

            // Calculate the projection and view transformation 计算投影和视图转换
            // 转换矩阵用来做什么的呢？是否记得上面我们绘制的图形坐标需要转换为OpenGl中能处理的小端字节序（LittleEdian），
            // 没错，转换矩阵就是用来将数据转为OpenGl ES可用的数据字节，
            // 我们将相机视图和投影设置的数据相乘，便得到一个转换矩阵，然后我们再将此矩阵传给顶点着色器
            Matrix.multiplyMM(mMVPMatrix, // 接收相乘结果
                    0, // 接收矩阵的起始位置
                    mProjectionMatrix, // 左矩阵
                    0, // 左矩阵的起始位置（偏移量）
                    mViewMatrix, // 右矩阵
                    0); // 右矩阵的起始位置（偏移量）
            // 将两个4x4矩阵相乘并将结果存储在第三个4x4中

            // Create a rotation transformation for the triangle 为三角形创建旋转变换
            long time = SystemClock.uptimeMillis() % 4000L;
            float angle = 0.090f * ((int) time);
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

            // 手势旋转
//            Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

            // Combine the rotation matrix with the projection and camera view
            // Note that the mMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            // 将旋转矩阵与投影和摄像机视图结合使用请注意，mMVPMatrix因子 *必须是首位* 才能使矩阵乘法乘积正确。
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

            // Draw shape
            mTriangle.draw(scratch);

        }

        // mMVPMatrix is an abbreviation for "Model View Projection Matrix" mMVPMatrix是“模型视图投影矩阵”的缩写
        private final float[] mMVPMatrix = new float[16];
        private final float[] mProjectionMatrix = new float[16];
        private final float[] mViewMatrix = new float[16];

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            // 设置视图展示窗口
            GLES20.glViewport(0, 0, width, height);

            float ratio = (float) width / height;

            // this projection matrix is applied to object coordinates 此投影矩阵应用于onDrawFrame（）方法中的对象坐标
            // in the onDrawFrame() method
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

    }
}
