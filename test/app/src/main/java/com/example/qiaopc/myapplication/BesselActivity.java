package com.example.qiaopc.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BesselActivity extends AppCompatActivity {

    private android.widget.Button btnstart;
    private MagicCircle circleH;
    private MagicCircle circleV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel);
        circleH = (MagicCircle) findViewById(R.id.circleH);
        circleV = (MagicCircle) findViewById(R.id.circleV);
        this.btnstart = (Button) findViewById(R.id.btn_start);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleH.startAnimation();
                circleV.startAnimation();
            }
        });
        init();
    }

    private void init() {

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //指定监听的传感器类型
        //all为全部，ACCELEROMETER为加速度，ORIENTATION为方向
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //方式2：自定义传感器监听器，实现SensorEventListener
        MycustomSensorListener mycustomSensorListener = new MycustomSensorListener();
        manager.registerListener(mycustomSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private class MycustomSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            circleH.setInterpolatedTime(event.values[2] * 0.05f + 0.5f);
            circleV.setInterpolatedTime(event.values[1] * -0.05f + 0.5f);
            Log.d("onSensorChanged:", event.values[2] * 0.025f + 0.5f + " v:" + event.values[1] * 0.025f + 0.5f);
            Log.d("test11", " v:" + event.values[1] + " ::::" + (event.values[1] * -0.0055f + 0.5f) + " aaaaa");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d("onAccuracyChanged:", "" + accuracy);

        }
    }
}