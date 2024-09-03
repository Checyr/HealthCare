package com.gabrielly.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gabrielly.healthcare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView txtaccelerometerX;
    private TextView txtaccelerometerY;
    private TextView txtaccelerometerZ;


    private Float accelerometerX;
    private Float accelerometerY;
    private Float accelerometerZ;


    private TextView txtstepcounter;
    private int stepcount;

    // peak magnitude
    private static final float STEP_THRESHOLD = 8.0f;

    private static final int STEP_DELAY_NS = 250000000; // 0.25 seconds

    // armazena o valor do ultimo passo
    private long lastStepTime = 0;


    // Used to load the 'healthcare' library on application startup.
    static {
        System.loadLibrary("healthcare");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btn_add = findViewById(R.id.btn_add);

        // Set an OnClickListener to call the native method
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });


        txtaccelerometerX = findViewById(R.id.accelerometerX);
        txtaccelerometerY = findViewById(R.id.accelerometerY);
        txtaccelerometerZ = findViewById(R.id.accelerometerZ);


        txtstepcounter = findViewById(R.id.txt_step_count);
        txtstepcounter.setText("0");

        setupSensorStuff();

    }

    // configuração do acelerometro
    private void setupSensorStuff() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelerometer != null){
            sensorManager.registerListener(this,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST,
                    SensorManager.SENSOR_DELAY_FASTEST
            );
        }
    }

    /**
     * A native method that is implemented by the 'healthcare' native library,
     * which is packaged with this application.
     */
    public native Void onButtonClick();

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float sidesX = sensorEvent.values[0]; // X
            float upDownY = sensorEvent.values[1]; // Y
            float latitudeZ = sensorEvent.values[2]; // Z

            accelerometerY = upDownY; //turn the smartphone upsidedown and you will see
            accelerometerX = sidesX; //turn the smartphone upsidedown and you will see
            accelerometerZ = latitudeZ; //turn the smartphone upsidedown and you will see

            String stepCountStringX = Float.toString(accelerometerX);
            String stepCountStringY = Float.toString(accelerometerY);
            String stepCountStringZ = Float.toString(accelerometerZ);

            txtaccelerometerX.setText(stepCountStringX);
            txtaccelerometerY.setText(stepCountStringY);
            txtaccelerometerZ.setText(stepCountStringZ);

            // chamar a função magnitude agora que as variaveis estao com valores
            magnitude(accelerometerX,accelerometerY,accelerometerZ);
        }

    }

    public Float getAccelerometerX() {
        return accelerometerX;
    }

    public Float getAccelerometerY() {
        return accelerometerY;
    }

    public Float getAccelerometerZ() {
        return accelerometerZ;
    }



    // magnitude e contador de passos
    private void magnitude(float x,float y,float z) {

        float magnitude = (float)Math.sqrt(x * x + y * y + z * z);

        //dectar os picos

        if (magnitude < STEP_THRESHOLD) {
            long currentTime = System.nanoTime();
            if (currentTime - lastStepTime > STEP_DELAY_NS){
                lastStepTime = currentTime;
                onStepcounter();
            }
        }
    }


    // aumentar contador de passo
    private void onStepcounter() {
        stepcount = stepcount + 1;

        String Str_step_count = Integer.toString(stepcount);

        txtstepcounter.setText(Str_step_count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}