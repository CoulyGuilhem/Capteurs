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

        float[] mGeomagnetic = null;
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];

        if (x < -2){
            texte.setText("DROITE");
        } else if (x > 2){
            texte.setText("GAUCHE");
        } else if(y < -2){
            texte.setText("HAUT");
        } else if( y > 2){
            texte.setText("BAS");
        } else {
            texte.setText("NO DIRECTION");
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