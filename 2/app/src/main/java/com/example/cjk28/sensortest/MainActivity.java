package com.example.cjk28.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;
    Sensor mSensorAcce;
    Sensor mSensorGyro;

    TextView tv_test;

    private float getX, getY, getZ;
    private float getX_gyro = 0, getY_gyro = 0, getZ_gyro= 0 ;

    final static String TAG = "SensorTest";

    double xAngle_gyr = 0,yAngle_gyr = 0,zAngle_gyr = 0;
    double xAngle_acc = 0;
    double yAngle_acc = 0;
    double zAngle_acc = 0;

    double xAngle = 0, yAngle = 0, zAngle = 0;

    boolean isAcc = false, isGyr = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_test = (TextView)findViewById(R.id.testSensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String str = "";
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                getX = event.values[0];    getY = event.values[1];    getZ = event.values[2];
                str = "ACCELEROMETER X: "+String.valueOf(getX) + " " + "Y: "+String.valueOf(getY) + " " + "Z: "+String.valueOf(getZ);
                Log.d(TAG,str);
                tv_test.setText(str);

                xAngle_acc = Math.atan(getY/getZ) * 180 / Math.PI;
                yAngle_acc = Math.atan(getX/getZ) * 180 / Math.PI;
                zAngle_acc = Math.atan(getX/getY) * 180 / Math.PI;

                isAcc = true;

                break;

            case Sensor.TYPE_GYROSCOPE:
                getX_gyro += event.values[0]*180/Math.PI * 0.0215;    getY_gyro += event.values[1]*180/Math.PI * 0.0215;    getZ_gyro += event.values[2]*180/Math.PI * 0.0215;
                str = "GYROSCOPE: X: "+String.valueOf(getX_gyro) + " " + "Y: "+String.valueOf(getY_gyro) + " " + "Z: "+String.valueOf(getZ_gyro);
                Log.d(TAG,str);
                tv_test.setText(str);

                isGyr = true;

                break;
        }
        if(isGyr && isAcc){
            isGyr = isAcc = false;
            xAngle =  ( 0.95 * (xAngle + (getX_gyro))) + (0.05 * xAngle_acc);
            yAngle =  ( 0.95 * (yAngle + (getY_gyro))) + (0.05 * yAngle_acc);
            zAngle =  ( 0.95 * (zAngle + (getZ_gyro))) + (0.05 * zAngle_acc);
            str = "Filter: X: " + String.valueOf(xAngle) + " Y: " + String.valueOf(yAngle) + " Z: " + String.valueOf(zAngle);
            Log.d(TAG,str);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
