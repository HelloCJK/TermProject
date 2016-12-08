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

    TextView tv_test;

    private float getX, getY, getZ;

    public KalmanFilter mKalmanFilterX;
    public KalmanFilter mKalmanFilterY;
    public KalmanFilter mKalmanFilterZ;

    final static String TAG = "SensorTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_test = (TextView)findViewById(R.id.testSensor);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAcce = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mKalmanFilterX = new KalmanFilter(0.0f);
        mKalmanFilterY = new KalmanFilter(0.0f);
        mKalmanFilterZ = new KalmanFilter(0.0f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String str = "";
        switch(event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                getX = event.values[0];
                getY = event.values[1];
                getZ = event.values[2];
                str = "ACCELEROMETER(no Filter) X: " + String.valueOf(getX) + " " + "Y: " + String.valueOf(getY) + " " + "Z: " + String.valueOf(getZ);
                Log.d(TAG, str);

                float filteredX = (float) mKalmanFilterX.Update((double) getX);
                float filteredY = (float) mKalmanFilterY.Update((double) getY);
                float filteredZ = (float) mKalmanFilterZ.Update((double) getZ);

                str = "ACCELEROMETER(Filter) X: " + String.valueOf(filteredX) + " " + "Y: " + String.valueOf(filteredY) + " " + "Z: " + String.valueOf(filteredZ);
                Log.d(TAG, str);
                tv_test.setText(str);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class KalmanFilter{
        private double Q = 0.00001;
        private double R = 0.001;
        private double X = 0, P = 1, K;

        KalmanFilter(double initVal){
            X = initVal;
        }

        private void measurementUpdate(){
            K = (P + Q)/(P + Q + R);
            P = R * (P+Q)/(R+P+Q);
        }

        public double Update(double measurement){
            measurementUpdate();
            X = X + (measurement - X) * K;

            return X;
        }
    }
}
