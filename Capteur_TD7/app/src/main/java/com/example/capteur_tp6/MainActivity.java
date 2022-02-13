package com.example.capteur_tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView image;
    SensorManager sm;
    Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.image);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this,light,sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(light != null){
            float x = event.values[0];
            if (x < 15) {
                image.setImageResource(R.drawable.lune);
            } else {
                image.setImageResource(R.drawable.soleil);
            }
        } else {
            TextView text = findViewById(R.id.text);
            text.setText("No captor");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this,light,sm.SENSOR_DELAY_NORMAL);
    }
}