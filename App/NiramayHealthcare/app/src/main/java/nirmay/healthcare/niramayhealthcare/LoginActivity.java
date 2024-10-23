package nirmay.healthcare.niramayhealthcare;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button login;
    Button signup;
    Button click_here;
    EditText username_edittxt;
    EditText password_edittxt;
    private long pressedTime;
    public static String nav_header_username,user_username,user_password,profile_pic,user_name,user_weight,user_height;
    public static  int user_gender;
  //  public static String updatedProfile = "";
    ProgressDialog loadingProgressDialog;
    private JSONArray jsonArrayRequest ;
    UserSessionData userSessionData;


    @Override
    public void onBackPressed() {
        if(pressedTime + 2000 > System.currentTimeMillis()){
//            moveTaskToBack(true);
            super.onBackPressed();
            this.finish();
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.signup_button);
        click_here = (Button)findViewById(R.id.forgot_pass_btn);
        username_edittxt = findViewById(R.id.username);
        password_edittxt = findViewById(R.id.password);
        loadingProgressDialog = new ProgressDialog(LoginActivity.this);
        loadingProgressDialog.setMessage("Logging in...Please Wait...");
        loadingProgressDialog.setCancelable(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_edittxt.getText().toString();
                String password  = password_edittxt.getText().toString();
//                validatelogin("itsviv","itsviv1433");

                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Username And Password Cannot be Empty.", Toast.LENGTH_SHORT).show();
                }
                else if((password.length() <= 6)){
                    Toast.makeText(LoginActivity.this, "Password is too Short.", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingProgressDialog.show();
                    validatelogin(username,password);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignUp.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });
        click_here.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,ForgotPass.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });

    }
    public void validatelogin(String username,String password)
    {
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
        Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call = api.getUserData(username,password);

        call.enqueue(new Callback<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data>() {
            @Override
            public void onResponse(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Response<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> response) {

                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("Successful"))
                    {
                        LoginActivity.nav_header_username = response.body().getUsername();
                        LoginActivity.user_username = response.body().getUsername();
                        LoginActivity.user_password = response.body().getPassword();
                        LoginActivity.profile_pic = response.body().getProfile_pic();
                        LoginActivity.user_name = response.body().getName();
                        LoginActivity.user_height = response.body().getHeight();
                        LoginActivity.user_weight = response.body().getWeight();
                        LoginActivity.user_gender = response.body().getGender();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        //save session
                        userSessionData = new UserSessionData(response.body().getUsername(),response.body().getPassword());
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        sessionManagement.saveSession(userSessionData);

                        loadingProgressDialog.hide();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else if(response.body().getStatus().equals("Failed"))
                    {
                        loadingProgressDialog.hide();
                        Toast.makeText(LoginActivity.this, "Please Enter Valid Credentials.", Toast.LENGTH_LONG).show();
                    }
                }

            }
            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                loadingProgressDialog.hide();
                Toast.makeText(LoginActivity.this, "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
            }
        });

    }

    private void activate_online_device() {
        String g_p_s1 = "No network connection available.";
        AlertDialog.Builder alert112 = new AlertDialog.Builder(this);
        alert112.setTitle("Network Error");
        // alert112.setIcon(R.drawable.ic_network_check_black_24dp);

        alert112.setMessage(g_p_s1);
        alert112.setPositiveButton("Activate Internet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface alert, int which) {
                // TODO Auto-generated method stub
                //Do something
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
                Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(settingsIntent, 9003);
                alert.dismiss();
            }

        });
    }

    private boolean isDeviceOnline() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}