package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private Button myButton, orientButton;
    private TextView AX, AY, AZ,GX, GY,GZ,CX,CY,CZ, PV, RV, YV;
    private List<Object> listacc = new ArrayList<>();
    private List<Object> listgyro = new ArrayList<>();
    private List<Object> listcomp = new ArrayList<>();
    private  static final int NUMSAMPLES = 1000;
    private SensorManager sensorManager;
    private int flag = 0;
    private float accx,accy,accz, gyrox, gyroy,gyroz, compx,compy,compz;
    private Float acc_Pitch = 0.0f, acc_Roll = 0.0f, acc_Yaw = 0.0f;
    private Float gyro_Pitch = 0.0f, gyro_Roll = 0.0f, gyro_Yaw = 0.0f;
    private long gyro_TimeStamp = 0, interval =0;
    private double  fPitch=0, fRoll=0, fYaw=0;


    private Sensor sensoracc, sensorgyro, sensorcompass;
    private Handler myHandler = new Handler();
    private ImageView mobileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button) findViewById(R.id.button);
        orientButton = (Button) findViewById(R.id.button3);
        AX = (TextView) findViewById(R.id.accX);
        AY = (TextView) findViewById(R.id.accY);
        AZ = (TextView) findViewById(R.id.accZ);
        GX = (TextView) findViewById(R.id.gyroX);
        GY = (TextView) findViewById(R.id.gyroY);
        GZ = (TextView) findViewById(R.id.gyroZ);
        CX = (TextView) findViewById(R.id.compX);
        CY = (TextView) findViewById(R.id.compY);
        CZ = (TextView) findViewById(R.id.compZ);
        PV = (TextView) findViewById(R.id.pitchValue);
        RV = (TextView) findViewById(R.id.rollValue);
        YV = (TextView) findViewById(R.id.yawValue);
        mobileImg = (ImageView)findViewById(R.id.mobileImage);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensoracc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorgyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorcompass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        myButton.setOnClickListener(this);
        orientButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                sensorManager.registerListener(this,sensoracc, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this,sensorgyro, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this,sensorcompass, SensorManager.SENSOR_DELAY_NORMAL);
                //calibrate_Orientation();
                break;

            case R.id.button3:
                flag = 1;
                calibrate_Orientation();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        myButton.setOnClickListener(this);
        orientButton.setOnClickListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //check when you have 1000 samples

     /*   if(accelerometerData.size() >= NUMSAMPLES && listcomp.size() >= NUMSAMPLES && listgyro.size() >= NUMSAMPLES){
            //run algorithm
            number.setText(listacc.size());
        }
        else {*/

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accx = sensorEvent.values[0];
                accy = sensorEvent.values[1];
                accz = sensorEvent.values[2];
                AX.setText(new Float(accx).toString());
                AY.setText(new Float(accy).toString());
                AZ.setText(new Float(accz).toString());
                listacc.add(new Accelerometer(accx, accy, accz));
              //  myUpdateClass myobj = new myUpdateClass(accx, accy, accz);
              //  myHandler.post(myobj);
            if (flag == 1) {
                acc_Pitch = getaccPitch(accx, accy, accz);
                acc_Roll = getaccRoll(accx, accy, accz);
                acc_Yaw = getaccYaw(compx, compy, compz, acc_Pitch, acc_Roll);
            }
            }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyrox = sensorEvent.values[0];
            gyroy = sensorEvent.values[1];
            gyroz = sensorEvent.values[2];
            GX.setText(new Float(gyrox).toString());
            GY.setText(new Float(gyroy).toString());
            GZ.setText(new Float(gyroz).toString());
            listgyro.add(new Gyroscope(gyrox, gyroy, gyroz));
            //myUpdateClass myobj = new myUpdateClass(gyrox,gyroy,gyroz);
            //myHandler.post(myobj);
            if (flag == 1) {
                interval = sensorEvent.timestamp - gyro_TimeStamp;
                if (gyro_TimeStamp <= 1) {
                    gyro_TimeStamp = sensorEvent.timestamp;
                    //break;
                }
            }

                gyro_TimeStamp = sensorEvent.timestamp;
                float dT = interval * 0.000000001f; // nanosec to sec

                gyro_Pitch = (float) ((gyro_Pitch) + ((-gyrox) * dT * 57.3f)) % 360;
                gyro_Roll = (float) ((gyro_Roll) + ((gyroy) * dT * 57.3f)) % 360;
                gyro_Yaw = (float) ((gyro_Yaw) + (-gyroz * dT * 57.3f)) % 360;
            }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                compx = sensorEvent.values[0];
                compy = sensorEvent.values[1];
                compz = sensorEvent.values[2];
                CX.setText(new Float(compx).toString());
                CY.setText(new Float(compy).toString());
                CZ.setText(new Float(compz).toString());
                listcomp.add(new Compass(compx, compy, compz));
                // myUpdateClass myobj = new myUpdateClass(compx, compy, compz);
                //myHandler.post(myobj);
            }

            if (flag == 1) {
                complimentaryFilter();
                mobileImg.setRotationX((float) fPitch);
                mobileImg.setRotationY((float) fRoll);
                mobileImg.setRotation((float) fYaw);
            }
            else{
                fPitch=0;
                fRoll=0;
                fYaw=0;
            }


    }

    /*private class myUpdateClass implements Runnable{
        private  float ax,ay,az;
        public myUpdateClass(float x_,float y_, float z_){
            ax = x_;
            ay = y_;
            az = z_;
        }
        @Override
        public void run() {
            AX.setText(new Float(ax).toString());
            AY.setText(new Float(ay).toString());
            AZ.setText(new Float(az).toString());
        }
    }*/


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private float getaccPitch(Float accx, Float accy, Float accz){
       Float magnitude = (float) Math.abs(Math.sqrt(Math.pow(accx, 2) + Math.pow(accy, 2) + Math.pow(accz, 2)));
        Float pitch = (float) Math.toDegrees(Math.asin((-accy) / magnitude));
        return pitch;

        /*
        Float pitch = (float) (180 * Math.atan2(accx, Math.sqrt(accy*accy + accz*accz))/Math.PI);;
        return pitch;

        */
    }

    private float getaccRoll(Float accx, Float accy, Float accz){

       Float magnitude = (float) Math.abs(Math.sqrt(Math.pow(accx, 2) + Math.pow(accy, 2) + Math.pow(accz, 2)));
        Float roll = -(float) Math.toDegrees(Math.asin(accx / magnitude));
        return roll;
        /*
        Float roll = (float) (180 * Math.atan2(accy, Math.sqrt(accx*accx + accz*accz))/Math.PI);
        return roll;
*/
    }

    private float getaccYaw(Float compx, Float compy, Float compz, Float acc_Pitch, Float acc_Roll){
        Float Y = (float) (compx*Math.cos(acc_Pitch) + compy*Math.sin(acc_Roll)*Math.sin(acc_Pitch) + compz*Math.cos(acc_Roll)*Math.sin(acc_Pitch));;
        Float X = (float) ((float) (compx * Math.sin(acc_Pitch) * Math.sin(acc_Roll)) + (compy * Math.cos(acc_Pitch)) + (compz * Math.sin(acc_Pitch) * Math.cos(acc_Roll)) );
        Float Yaw = (float) Math.toDegrees(Math.atan2(Y,X));

        if (Yaw == null){
            Yaw = 0.0f;
        }
        return Yaw;
    }

    private void complimentaryFilter(){
        fPitch = (gyro_Pitch * 0.98) + (acc_Pitch * 0.02);
        fRoll = (gyro_Roll * 0.98) + (acc_Roll * 0.02);
        fYaw = (gyro_Yaw * 0.98) + (acc_Yaw * 0.02);

        int fP = (int) Math.round(fPitch);
        int fR = (int) Math.round(fRoll);
        int fY = (int) Math.round(fYaw);

        PV.setText(Integer.toString(fP));
        RV.setText(Integer.toString(fR));
        YV.setText(Integer.toString(fY));
    }

    private void calibrate_Orientation(){
        gyro_Pitch = acc_Pitch ;
        gyro_Roll = acc_Roll;

        accx = 0.0f; accy = 0.0f; accz = 0.0f;
        gyrox = 0.0f; gyroy = 0.0f; gyroz = 0.0f;
        compx = 0.0f; compy = 0.0f; compz = 0.0f;

        fPitch=0; fRoll=0; fYaw=0;
        acc_Pitch = 0.0f; acc_Roll = 0.0f; acc_Yaw = 0.0f;
        gyro_Pitch = 0.0f; gyro_Roll = 0.0f; gyro_Yaw = 0.0f;

        gyro_TimeStamp = 0; interval =0;

        mobileImg.setRotationX(0.0f);
        mobileImg.setRotationY(0.0f);
        mobileImg.setRotation(0.0f);
    }

}

