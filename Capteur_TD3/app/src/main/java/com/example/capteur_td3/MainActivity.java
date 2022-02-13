package com.example.capteur_td3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

    public TextView t;
    public ConstraintLayout v;
    SensorManager sManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(R.id.background);
        t = findViewById(R.id.texte);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener((SensorEventListener) this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(accelerometer != null){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            convertColor(x,y,z);
        } else {
            t.setText("No accelerometer detected");
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

    public void convertColor(float x,float y,float z){
        if(x > 10 ) {
            x = 10;
        } else if ( x < -10){
            x = -10;
        } else if (y > 10) {
            y = 10;
        } else if( y < -10) {
            y = -10;
        } else if( z > 10) {
            z = 10;
        } else if( z < -10){
            z = -10;
        }

        x = x + 10;
        y = y + 10;
        z = z + 10;
        x = x/20 * 255;
        y = y/20 * 255;
        z = z/20 * 255;
        t.setText("x :"+x+" y :"+y+" z :"+z);
        int myColor = Color.rgb(Math.round(x),Math.round(y),Math.round(z));
        System.out.println(myColor);
        v.setBackgroundColor(myColor);
    }
}