package nirmay.healthcare.niramayhealthcare.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import nirmay.healthcare.niramayhealthcare.API26Wrapper;
import nirmay.healthcare.niramayhealthcare.Health_Status;
import nirmay.healthcare.niramayhealthcare.Interface.StepListener;
import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.SplashActivity;
import nirmay.healthcare.niramayhealthcare.ui.stepTracker.StepDetector;

public class CowinService extends Service implements SensorEventListener, StepListener {

    public final static int NOTIFICATION_ID = 1;
    private static String TEXT_STEP_NOTIFICATION = " Step Tracker is ON";
    private static final String TEXT_NUM_STEPS = " Steps";
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static int SPLASH_TIME_OUT = 3000;
    int i;
    public CowinService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        step(100);

        return Service.START_STICKY;
    }
    public static Notification getNotification(final Context context) {

        Notification.Builder notificationBuilder =
                Build.VERSION.SDK_INT >= 26 ? API26Wrapper.getNotificationBuilder(context) :
                        new Notification.Builder(context).setAutoCancel(true);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentText("Steps "+ Health_Status.numSteps);
        notificationBuilder.setContentTitle(TEXT_STEP_NOTIFICATION);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        Health_Status.numSteps++;
        Health_Status.stepsTextview.setText(Health_Status.numSteps + TEXT_NUM_STEPS);
//        onStartCommand(new Intent(),0,0);
        Health_Status.calorieBurnedCalculator(LoginActivity.user_weight,LoginActivity.user_height,Health_Status.numSteps,LoginActivity.user_gender);

    }
}
