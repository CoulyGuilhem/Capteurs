package com.example.capteur_td7;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView text;
    SensorManager sm;
    Sensor barometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        barometer = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(barometer != null){
            sm.registerListener(this,barometer,sm.SENSOR_DELAY_NORMAL);
        } else {
            text.setText("No barometer");
            onPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this,barometer,sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
            float hPa = event.values[0];
            text.setText(""+hPa);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}