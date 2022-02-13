package com.example.capteur_td4;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor accelerometer;
    private SensorManager sManager;
    private TextView texte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texte = findViewById(R.id.texte);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(accelerometer != null){
            float x = event.values[0];
            float y = event.values[1];

            if (Math.abs(x) > Math.abs(y)) { // If x axis have higher intensity than y axis we check right and left direction (an none if it's inside the dead zone)
                if (x < -2) {
                    texte.setText("DROITE");
                } else if (x > 2) {
                    texte.setText("GAUCHE");
                } else {
                    texte.setText("NO DIRECTION");
                }
            } else { // same logic with y axis
                if (y < -2) {
                    texte.setText("HAUT");
                } else if (y > 2) {
                    texte.setText("BAS");
                } else {
                    texte.setText("NO DIRECTION");
                }
            }
        } else {
            texte.setText("No accelerometer detected");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        sManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sManager.registerListener(this,accelerometer,sManager.SENSOR_DELAY_NORMAL);
    }
}