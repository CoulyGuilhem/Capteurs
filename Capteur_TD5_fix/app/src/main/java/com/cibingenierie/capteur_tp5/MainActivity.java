package com.cibingenierie.capteur_tp5;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    float last_x,last_y,last_z,last_speed = 0;
    boolean flash;
    long lastUpdate = 0;
    Sensor accelerometer;
    CameraManager cm;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        flash = false;
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        cm = (CameraManager) getSystemService(CAMERA_SERVICE);
        sm.registerListener(this ,accelerometer,sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (accelerometer != null){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float speed = Math.round(Math.abs(x + y + z - last_x - last_y - last_z));
            text.setText("" + speed);
            if (speed > 10 && last_speed <= 8) {
                flash = !flash;
                try {
                    cm.setTorchMode(cm.getCameraIdList()[0], flash);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            last_speed = speed;
            last_x = x;
            last_y = y;
            last_z = z;
        } else {
            text.setText("No accelerometer detected");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}