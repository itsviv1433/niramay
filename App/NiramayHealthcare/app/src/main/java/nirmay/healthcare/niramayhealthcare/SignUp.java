package nirmay.healthcare.niramayhealthcare;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Get_Vaccine_Details;
import nirmay.healthcare.niramayhealthcare.Interface.Send_Email_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.User_Signup_Interface;

import nirmay.healthcare.niramayhealthcare.Pojo.Email;
import nirmay.healthcare.niramayhealthcare.Pojo.User_SignUp;
import nirmay.healthcare.niramayhealthcare.Pojo.Vaccine;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    TextView signup_title;
    String name;
    Button create_user_acc;
    Button login;
    EditText first_name, last_name, username_edditxt, email_editxt, phone_editxt, password_editxt, confirmpassword_editxt;
    EditText contrycode_edittxt;
    CheckBox agreeCheckbox;
    ProgressDialog loadingProgressDialog;
    public final static int NOTIFICATION_ID = 1;
    JsonArray vaccine_details_array = new JsonArray();


    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignUp.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_title = (TextView) findViewById(R.id.signup_title);
        create_user_acc = (Button) findViewById(R.id.create_user_acc_btn);
        login = (Button) findViewById(R.id.usersignup_activity_login_btn);
        first_name = (EditText) findViewById(R.id.firstname);
        last_name = (EditText) findViewById(R.id.lastname);
        username_edditxt = (EditText) findViewById(R.id.username);
        email_editxt = (EditText) findViewById(R.id.email);
        contrycode_edittxt = (EditText) findViewById(R.id.countryCode);
        phone_editxt = (EditText) findViewById(R.id.mobileNumber);
        password_editxt = (EditText) findViewById(R.id.password);
        confirmpassword_editxt = (EditText) findViewById(R.id.rePassword);
        agreeCheckbox = (CheckBox) findViewById(R.id.agree_condition_checkbox);
        loadingProgressDialog = new ProgressDialog(SignUp.this);
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Creating Account...Please Wait...");


        create_user_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = first_name.getText().toString() + " " + last_name.getText().toString();
                String username = username_edditxt.getText().toString();
                String email = email_editxt.getText().toString().trim();
                String phone = phone_editxt.getText().toString();
                String password = password_editxt.getText().toString();
                String confirmpassword = confirmpassword_editxt.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if ((name.equals("") || username.equals("") || email.equals("") || phone.equals("") || password.equals("") || confirmpassword.equals(""))) {
                    Toast.makeText(SignUp.this, "All Fields Are Mendatory.", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(SignUp.this, "Enter Valid Email.", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 10) {
                    Toast.makeText(SignUp.this, "Please,Enter Valid Contact.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmpassword)) {
                    Toast.makeText(SignUp.this, "Please,Enter Same Password.", Toast.LENGTH_SHORT).show();
                } else if ((password.length() <= 6) || (confirmpassword.length() <= 6)) {
                    Toast.makeText(SignUp.this, "Password is too Short.", Toast.LENGTH_SHORT).show();
                } else if (!(agreeCheckbox.isChecked())) {
                    Toast.makeText(SignUp.this, "Please Accept the Terms and Conditions.", Toast.LENGTH_SHORT).show();
                } else {
                    loadingProgressDialog.show();
                    addUser(name, username, email, phone, password);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            }
        });

    }

    private void addUser(String name, String username, String email, String phone, String password) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(User_Signup_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        User_Signup_Interface api = retrofit.create(User_Signup_Interface.class);
        //Call<List<User_SignUp>> call = api.addUser(name,username,email,phone,password);
        Call<User_SignUp> call = api.addUser(name, username, email, phone, password);

        call.enqueue(new Callback<User_SignUp>() {
            @Override
            public void onResponse(@NonNull Call<User_SignUp> call, @NonNull Response<User_SignUp> response) {
                //System.out.println("Response :" + response.toString());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("inserted")) {
                        loadingProgressDialog.hide();
                        Toast.makeText(SignUp.this, "Account Created Successfully.Kindly Login.", Toast.LENGTH_LONG).show();
                        sendEmail();
                        showNotification();
                        Intent i = new Intent(SignUp.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else if (response.body().getResponse().equals("exists")) {
                        loadingProgressDialog.hide();
                        Toast.makeText(SignUp.this, "Username is already taken.Please take another one.", Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResponse().equals("error")) {
                        loadingProgressDialog.hide();
                        Toast.makeText(SignUp.this, "error.", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<User_SignUp> call, @NonNull Throwable t) {
                Toast.makeText(SignUp.this, "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
                loadingProgressDialog.hide();
            }
        });

    }

    private void sendEmail() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Send_Email_Interface.REGIURL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Call<Email> call;
        Send_Email_Interface api = retrofit.create(Send_Email_Interface.class);
        call = api.sendSignupMail();
        call.enqueue(new Callback<Email>() {
            @Override
            public void onResponse(@NonNull Call<Email> call, @NonNull Response<Email> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Email> call, @NonNull Throwable t) {
                System.out.println("Failure");
                System.out.println(t.getMessage());
            }
        });

    }

    public static Notification getNotification(final Context context, String name) {
        Notification.Builder notificationBuilder =
                Build.VERSION.SDK_INT >= 26 ? API26Wrapper.getNotificationBuilder(context) :
                        new Notification.Builder(context).setAutoCancel(true);
        notificationBuilder.setAutoCancel(true);

        notificationBuilder.setContentText("Next Step of " + name + " to Us...");
        notificationBuilder.setContentTitle("Niramay HealthCare Team");

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);

            notificationBuilder.setPriority(Notification.PRIORITY_HIGH).setShowWhen(true)
                    .setContentIntent(PendingIntent
                            .getActivity(context, 0, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT))
                    .setSmallIcon(R.drawable.notification_icon).setOngoing(true);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(context, "There is no Email Application in your device", Toast.LENGTH_SHORT).show();
        }
        return notificationBuilder.build();
    }

    private void showNotification() {
        ((NotificationManager) getSystemService(this.NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID, getNotification(this, name));
    }

}