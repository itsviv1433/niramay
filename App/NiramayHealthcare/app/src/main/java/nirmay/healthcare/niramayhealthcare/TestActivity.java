package nirmay.healthcare.niramayhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import nirmay.healthcare.niramayhealthcare.Interface.StepListener;
import nirmay.healthcare.niramayhealthcare.ui.stepTracker.StepDetector;

public class TestActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    private TextView stepsTextview,distanceTextview,caloriesTextview;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = " Steps";
    private static final String TEXT_DISTANCE = " Meters in Total";
    private static final String TEXT_CALORIES = " Calories Burned";
    private static String TEXT_STEP_NOTIFICATION = " Step Tracker is ON";
    public static int numSteps;
    private String calories,distance;
    private Button resetbtn;
    private Switch trackerSwitch;
    public final static int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        stepsTextview = findViewById(R.id.tv_steps);
        trackerSwitch = findViewById(R.id.tracker_switch);
        resetbtn = findViewById(R.id.steps_reset_button);
        distanceTextview = findViewById(R.id.distance_textview);
        caloriesTextview = findViewById(R.id.calories_textview);


        resetbtn.setEnabled(false);

        trackerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    resetbtn.setEnabled(true);
                    TEXT_STEP_NOTIFICATION = "Step Tracker is ON";
                    showNotification();
                    sensorManager.registerListener(TestActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                } else {
                    resetbtn.setEnabled(false);
                    TEXT_STEP_NOTIFICATION = "Step Tracker is OFF";
//                    showNotification();
                    API26Wrapper.manager.cancel(NOTIFICATION_ID);
                    sensorManager.unregisterListener(TestActivity.this);
                }
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numSteps = 0;
                distance = "0";
                calories = "0";
                stepsTextview.setText(numSteps + TEXT_NUM_STEPS);
                distanceTextview.setText(distance + TEXT_DISTANCE);
                caloriesTextview.setText(calories + TEXT_CALORIES);
                showNotification();
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                        public void run() {
//                            API26Wrapper.manager.cancel(NOTIFICATION_ID);
//                        }
//                    }, 3000);
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        TestActivity.numSteps++;
        stepsTextview.setText(TestActivity.numSteps + TEXT_NUM_STEPS);
        showNotification();
//        calorieBurnedCalculator(LoginActivity.user_weight,LoginActivity.user_height,numSteps,LoginActivity.user_gender);
        calorieBurnedCalculator("52","172",numSteps,1);
    }

    public void calorieBurnedCalculator(String weight, String height, int stepsCount, int gender) {


//        static double weight = 67.0; // kg
//
//        static double height = 178.0; // cm
//
//        static double stepsCount = 4793;

        double walkingFactor = 0.57;

        double CaloriesBurnedPerMile;

        double strip = 0;

        double stepCountMile; // step/mile

        double conversationFactor;

        double CaloriesBurned;

        NumberFormat formatter = new DecimalFormat("#0.00");

        double distancewalked;

        //Calculate Calories And Steps

        CaloriesBurnedPerMile = walkingFactor * (Double.parseDouble(weight) * 2.2);

        if(gender == 1){
            strip = Double.parseDouble(height) * 0.415;
        }else if(gender == 2){
            strip = Double.parseDouble(height) * 0.413;
        }else {
            strip = Double.parseDouble(height) * 0.415;
        }
        stepCountMile = 160934.4 / strip;

        conversationFactor = CaloriesBurnedPerMile / stepCountMile;

        CaloriesBurned = stepsCount * conversationFactor;

        calories = formatter.format(CaloriesBurned);

        distancewalked = (stepsCount * strip) / 100;

        distance = formatter.format(distancewalked);

        caloriesTextview.setText(calories + TEXT_CALORIES);
        distanceTextview.setText(distance + TEXT_DISTANCE);
    }

    public static Notification getNotification(final Context context) {

        Notification.Builder notificationBuilder =
                Build.VERSION.SDK_INT >= 26 ? API26Wrapper.getNotificationBuilder(context) :
                        new Notification.Builder(context).setAutoCancel(true);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentText("Steps "+ TestActivity.numSteps);
        notificationBuilder.setContentTitle(TEXT_STEP_NOTIFICATION);


//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_APP_EMAIL);

        notificationBuilder.setPriority(Notification.PRIORITY_MIN)
                .setShowWhen(true)
                .setSmallIcon(R.drawable.step_tracker_icon)
                .setOngoing(true)
                .setOnlyAlertOnce(true);
        return notificationBuilder.build();
    }

    private void showNotification() {
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID, getNotification(this));
    }

}