package nirmay.healthcare.niramayhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Forgot_Pass_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.ForgotpassPojo;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import okhttp3.OkHttpClient;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPass extends AppCompatActivity {

    Button send_otp;
    Button verify_otp;
    Button change_pass;
    EditText usernameEdittxt, otp_edittext1, otp_edittext2, otp_edittext3, otp_edittext4, otp_edittext5, otp_edittext6;
    EditText change_pass1;
    EditText change_pass2;
    String otp, sentotp, tempusername;
    int otpganerate;
    LinearLayout otplayout, changepasslayout;
    ProgressDialog pDialog;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ForgotPass.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        otplayout = (LinearLayout) findViewById(R.id.otp_layout);
        changepasslayout = (LinearLayout) findViewById(R.id.change_pass_layout);
        send_otp = (Button) findViewById(R.id.send_otp_btn);
        verify_otp = (Button) findViewById(R.id.verify_otp_btn);
        change_pass = (Button) findViewById(R.id.change_pass_btn);
        usernameEdittxt = (EditText) findViewById(R.id.forgot_username_edittxt);
        otp_edittext1 = (EditText) findViewById(R.id.otp_edittext1);
        otp_edittext2 = (EditText) findViewById(R.id.otp_edittext2);
        otp_edittext3 = (EditText) findViewById(R.id.otp_edittext3);
        otp_edittext4 = (EditText) findViewById(R.id.otp_edittext4);
        otp_edittext5 = (EditText) findViewById(R.id.otp_edittext5);
        otp_edittext6 = (EditText) findViewById(R.id.otp_edittext6);
        change_pass1 = (EditText) findViewById(R.id.change_pass_edittext1);
        change_pass2 = (EditText) findViewById(R.id.change_pass_edittext2);
        otp = "";

        otplayout.setVisibility(View.INVISIBLE);
        verify_otp.setEnabled(false);
        changepasslayout.setVisibility(View.INVISIBLE);

        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEdittxt.getText().toString().equals("")) {
                    Toast.makeText(ForgotPass.this, "Please Enter Username.", Toast.LENGTH_SHORT).show();
                } else {
                    displayDialog();
                    forgotPass(usernameEdittxt.getText().toString(), "");
                }
            }
        });
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = otp_edittext1.getText().toString()+otp_edittext2.getText().toString()
                        +otp_edittext3.getText().toString()+otp_edittext4.getText().toString()
                        +otp_edittext5.getText().toString()+otp_edittext6.getText().toString();


                pDialog.setMessage("Verifying...");
                if (sentotp != null) {
                pDialog.show();
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            sentotp, otp
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        changepasslayout.setVisibility(View.VISIBLE);
                                        pDialog.dismiss();
                                        otp = "";
                                        sentotp = "";
                                        verify_otp.setEnabled(false);
                                        otp_edittext6.setEnabled(false);
                                        Toast.makeText(ForgotPass.this, "Otp Verified Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPass.this, "Enter Correct Otp", Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    }

                                }
                            });
                } else {
                    Toast.makeText(ForgotPass.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (change_pass1.getText().toString().equals("") || change_pass2.getText().toString().equals("")) {
                    Toast.makeText(ForgotPass.this, "Password Cannot be Empty.", Toast.LENGTH_SHORT).show();
                } else if (change_pass1.getText().toString().length() <= 6 || change_pass2.getText().toString().length() <= 6) {
                    Toast.makeText(ForgotPass.this, "Password is too Short.", Toast.LENGTH_SHORT).show();
                } else if ((change_pass1.getText().toString()).equals(change_pass2.getText().toString())) {
                    //System.out.println(usernameEdittxt.getText().toString()+change_pass1.getText().toString());
                    forgotPass(usernameEdittxt.getText().toString(), change_pass1.getText().toString());
                    pDialog.setMessage("Changing Password...Please Wait...");
                    pDialog.show();
                    //Toast.makeText(ForgotPass.this, "Password Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPass.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void otpSender(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+ number,
                60,
                TimeUnit.SECONDS,
                ForgotPass.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        pDialog.dismiss();
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        System.out.println(e.getMessage());
                        Toast.makeText(ForgotPass.this, "Too Many Attempts.Please Try Again After an Hour."+e.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                    @Override
                    public void onCodeSent(@NonNull String generatedotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        sentotp = generatedotp;
                        otplayout.setVisibility(View.VISIBLE);
                        pDialog.dismiss();
                        send_otp.setEnabled(false);
                        Toast.makeText(ForgotPass.this, "Otp Sent Successfully on the Registered Mobile Number.Please Check Your MessageBox.", Toast.LENGTH_SHORT).show();
                        System.out.println("Code sent = "+generatedotp);
                    }
                }
        );
    }

    public void otpListener() {
        otp_edittext1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 1) {
                    otp_edittext2.requestFocus();
                }
            }
        });
        otp_edittext2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 1) {
                    otp_edittext3.requestFocus();
                } else {
                    otp_edittext1.requestFocus();
                }
            }
        });
        otp_edittext3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 1) {
                    otp_edittext4.requestFocus();
                } else {
                    otp_edittext2.requestFocus();
                }
            }
        });
        otp_edittext4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 1) {
                    otp_edittext5.requestFocus();
                } else {
                    otp_edittext3.requestFocus();
                }
            }
        });
        otp_edittext5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 1) {
                    otp_edittext6.requestFocus();
                } else {
                    otp_edittext4.requestFocus();
                }
            }
        });
        otp_edittext6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.length() == 0) {
                    verify_otp.setEnabled(false);
                    otp_edittext5.requestFocus();
                } else if (text.length() == 1) {
                    verify_otp.setEnabled(true);
                }
            }
        });
    }

    private void displayDialog() {
        pDialog = new ProgressDialog(ForgotPass.this);
        pDialog.setMessage("Sending OTP...Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void forgotPass(String username, String password) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Forgot_Pass_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        Forgot_Pass_Interface api = retrofit.create(Forgot_Pass_Interface.class);
        Call<ForgotpassPojo> call = api.changePassword(username, password);

        call.enqueue(new Callback<ForgotpassPojo>() {
            @Override
            public void onResponse(@NonNull Call<ForgotpassPojo> call, @NonNull Response<ForgotpassPojo> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("ValidUser")) {
                        otpListener();
                        otpSender(response.body().getPhone());
                    } else if (response.body().getStatus().equals("InvalidUser")) {
                        Toast.makeText(ForgotPass.this, "Username Does Not Exists.", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    } else if (response.body().getStatus().equals("PassChange")) {
                        Toast.makeText(ForgotPass.this, "Password Change Successfully.", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                        Intent i = new Intent(ForgotPass.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotpassPojo> call, @NonNull Throwable t) {
                pDialog.dismiss();
                Toast.makeText(ForgotPass.this, "Please,Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}