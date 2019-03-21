package com.example.qiaopc.opengl;

import android.opengl.GLES20;
import android.util.Log;

import com.example.qiaopc.myapplication.OpenGLES20Activity;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by qiaopc on 2019/3/18.
 */

public class Triangle {

//    private final String vertexShaderCode =
//            "attribute vec4 vPosition;" +
//                    "void main() {" +
//                    "  gl_Position = vPosition;" +
//                    "}";

    // 绘制图形，因为需要提供很多细节的图形渲染管线，所以绘制图形前至少需要一个顶点着色器来绘制形状和一个片段着色器的颜色，形状。
    // 这些着色器必须被编译，然后加入到一个OpenGL ES程序，然后将其用于绘制形状。

    // 顶点着色器（Vertex Shader）顶点着色器是GPU上运行的小程序，由名字可以知道，通过它来处理顶点，他用于渲染图形顶点的OpenGL ES图形代码。
    // 顶点着色器可用来修改图形的位置，颜色，纹理坐标，不过不能用来创建新的顶点坐标。
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            // 此矩阵成员变量提供了一个钩子来操纵使用此顶点着色器的对象的坐标
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    // 矩阵必须包含在gl_Position的修饰符中注意，uMVPMatrix因子 *必须是首位* 才能使矩阵乘法乘积正确。
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    // 片段着色器（Fragment Shader ) 用于呈现与颜色或纹理的形状的面的OpenGL ES代码。
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array 此数组中每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;

    static float triangleCoords[] = { // in counterclockwise order: 按逆时针顺序：
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final int mProgram;

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates 为形状坐标初始化顶点字节缓冲区 length*4是因为一个float占四个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        // use the device hardware's native byte order 数组排列用nativeOrder
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer 从ByteBuffer创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();

        // add the coordinates to the FloatBuffer 将坐标添加到FloatBuffer
        vertexBuffer.put(triangleCoords);

        // set the buffer to read the first coordinate 设置缓冲区以读取第一个坐标
        vertexBuffer.position(0);

        // 这里有一点需要注意，因为着色器的代码执行很昂贵，所以避免多次执行，需要我们一般将执行代码的逻辑写带图形类的构造方法中。
        int vertexShader = OpenGLES20Activity.MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OpenGLES20Activity.MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        Log.d("qiaopc", " vertexShader:" + vertexShader + " fragmentShader:" + fragmentShader);
        // create empty OpenGL ES Program 创建空的OpenGL ES程序
        // 项目（Program） -包含要用于绘制一个或多个形状着色器的OpenGL ES的对象。
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program 将顶点着色器添加到程序中
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program 将片段着色器添加到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables 创建OpenGL ES程序可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member 获取顶点着色器vPosition成员的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices 启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data 准备三角坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member 获取片段着色器Color成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle 设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        // get handle to shape's transformation matrix 得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }



}
