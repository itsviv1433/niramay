package nirmay.healthcare.niramayhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.StepListener;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import nirmay.healthcare.niramayhealthcare.service.CowinService;
import nirmay.healthcare.niramayhealthcare.service.MyWakefulReceiver;
import nirmay.healthcare.niramayhealthcare.ui.stepTracker.StepDetector;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class Health_Status extends AppCompatActivity implements SensorEventListener, StepListener {

    Slider slider;
    TextView bmiAdvice,bmitextview;
    ProgressDialog pDialog;
    LinearLayout bmiadvicelayout;
    public static TextView stepsTextview,distanceTextview,caloriesTextview;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = " Steps";
    private static final String TEXT_DISTANCE = " Meters in Total";
    private static final String TEXT_CALORIES = " Calories Burned";
    private static String TEXT_STEP_NOTIFICATION = " Step Tracker is ON";
    public static double numSteps;
    public static String calories,distance;
    private Button resetbtn;
    private Switch trackerSwitch;
    public final static int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_status);

        slider = findViewById(R.id.slider);
        bmiAdvice = findViewById(R.id.bmi_advice);
        bmitextview = findViewById(R.id.diagnosis_bmi_textview);
        bmiadvicelayout = findViewById(R.id.advice_layout);
        slider.setEnabled(false);

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

        getHeightWeight(LoginActivity.user_username,LoginActivity.user_password);
        displayLoader();

        resetbtn.setEnabled(false);


        trackerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    startService(new Intent(getApplication(), CowinService.class));

                    resetbtn.setEnabled(true);
                    TEXT_STEP_NOTIFICATION = "Step Tracker is ON";
                    showNotification();
                    sensorManager.registerListener(Health_Status.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                } else {
                    stopService(new Intent(getApplication(),CowinService.class));
                    MyWakefulReceiver.completeWakefulIntent(new Intent());
                    resetbtn.setEnabled(false);
                    TEXT_STEP_NOTIFICATION = "Step Tracker is OFF";
//                    showNotification();
                    API26Wrapper.manager.cancel(NOTIFICATION_ID);
                    sensorManager.unregisterListener(Health_Status.this);
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
            }
        });

    }
    private void displayLoader() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public String getBmi(String weight, String height) {
        float w = Float.parseFloat(weight);
        float h = Float.parseFloat(height);
        float BMI = (w / h / h) * 10000;
        return (String.format("%.2f", BMI));
    }
    public void getHeightWeight(String username, String password) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Get_User_Data_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        Get_User_Data_Interface api = retrofit.create(Get_User_Data_Interface.class);
        Call<Get_User_Data> call = api.getUserData(username, password);

        call.enqueue(new Callback<Get_User_Data>() {
            @Override
            public void onResponse(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Response<Get_User_Data> response) {

                if (response.isSuccessful()) {
                    float bmi = Float.parseFloat(getBmi(response.body().getWeight(),response.body().getHeight()));
                    bmitextview.setText("Your BMI is "+bmi);
                    if(response.body().getWeight().equals("0") && response.body().getHeight().equals("0")){
                        slider.setVisibility(View.GONE);
                        bmitextview.setVisibility(View.GONE);
                        bmiadvicelayout.setVisibility(View.GONE);
                    }
                    else if(bmi < 18.5) {
                        slider.setValue(5f);
//                        bmiAdvice.setText(Html.fromHtml(getResources().getString(R.string.underweight1)+"<b>"+"\n\nHere are some healthy ways to gain weight when you are underweight:"+"</b>"+getResources().getString(R.string.underweight2)));
                        bmiAdvice.setText(getResources().getString(R.string.underweight));
                    }
                    else if(bmi < 25) {
                        slider.setValue(15f);
                        bmiAdvice.setText(getResources().getString(R.string.healthy));
                    }
                    else if(bmi < 30) {
                        slider.setValue(25f);
                        bmiAdvice.setText(getResources().getString(R.string.overweight));
                    }
                    else {
                        slider.setValue(35f);
                        bmiAdvice.setText(getResources().getString(R.string.obese));
                    }
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                Toast.makeText(Health_Status.this, "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
                pDialog.dismiss();
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
        Health_Status.numSteps++;
        stepsTextview.setText(Health_Status.numSteps + TEXT_NUM_STEPS);
        showNotification();
        calorieBurnedCalculator(LoginActivity.user_weight,LoginActivity.user_height,numSteps,LoginActivity.user_gender);
    }

    public static void calorieBurnedCalculator(String weight, String height, double stepsCount, int gender) {


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

}