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

                double xAngle_acc = Math.atan(getY/getZ) * 180 / Math.PI;
                double yAngle_acc = Math.atan(getX/getZ) * 180 / Math.PI;
                double zAngle_acc = Math.atan(getX/getY) * 180 / Math.PI;

                break;

            case Sensor.TYPE_GYROSCOPE:
                getX_gyro += event.values[0]/1.5 * 90 * 0.02;    getY_gyro += event.values[1]/1.5 * 90 * 0.02;    getZ_gyro += event.values[2]/1.5 * 90 * 0.02;
                str = "GYROSCOPE: X: "+String.valueOf(getX_gyro) + " " + "Y: "+String.valueOf(getY_gyro) + " " + "Z: "+String.valueOf(getZ_gyro);
                Log.d(TAG,str);
                tv_test.setText(str);

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
