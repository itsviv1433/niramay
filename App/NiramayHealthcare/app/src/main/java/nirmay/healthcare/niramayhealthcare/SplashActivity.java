package nirmay.healthcare.niramayhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 2500;
    private boolean login;
    AlertDialog.Builder builder;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        builder = new AlertDialog.Builder(this);

        if (isDeviceOnline()) {
            SessionManagement sessionManagement = new SessionManagement(SplashActivity.this);
            login = sessionManagement.isLoggedIn();
            if(login){
                getUserData(SessionManagement.session_username, SessionManagement.session_password);
            }else{
                loadSession();
            }
        } else {
            activate_online_device();

        }

    }

    public void loadSession() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (login) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    public void getUserData(String username, String password) {
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
                    if (response.body().getStatus().equals("Successful")) {
                        LoginActivity.nav_header_username = response.body().getUsername();
                        LoginActivity.user_username = response.body().getUsername();
                        LoginActivity.user_password = response.body().getPassword();
                        LoginActivity.profile_pic = response.body().getProfile_pic();
                        LoginActivity.user_name = response.body().getName();
                        LoginActivity.user_height = response.body().getHeight();
                        LoginActivity.user_weight = response.body().getWeight();
                        LoginActivity.user_gender = response.body().getGender();
                        loadSession();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                Toast.makeText(SplashActivity.this, "Something Went Wrong.", Toast.LENGTH_LONG).show();
//                activate_online_device();
            }
        });
    }

    private void activate_online_device() {

        builder.setMessage("No network connection available")
                .setCancelable(false)
                .setTitle("Network Error")
                .setPositiveButton("Activate Internet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                        startActivity(intent);
                        finish();
//                        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
//                        startActivityForResult(settingsIntent, 9003);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        AlertDialog alert =  builder.create();
        alert.show();
    }

    private boolean isDeviceOnline() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
