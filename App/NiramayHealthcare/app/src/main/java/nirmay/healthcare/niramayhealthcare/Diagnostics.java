package nirmay.healthcare.niramayhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Disease_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.diagnosis.DiagnosisFragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Diagnostics extends AppCompatActivity {

    ArrayList<Disease> test_details_array = new ArrayList<>();
    ProgressDialog loadingProgressDialog;
    TextView testname,testprice,testdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);

        testname = findViewById(R.id.test_name);
        testprice = findViewById(R.id.test_price);
        testdetails = findViewById(R.id.test_details);

        if(!DiagnosisFragment.selectedDisease.equals("")){
            gettestdetails(DiagnosisFragment.selectedDisease);
            displayLoader();
        }else{
            Intent i = new Intent(Diagnostics.this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "You dont have any disease", Toast.LENGTH_SHORT).show();
        }

    }
    public void displayLoader() {
        loadingProgressDialog = new ProgressDialog(this);
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Please Wait....");
        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.show();
    }
    public void gettestdetails (String disease_name){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Disease_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        Disease_Interface api = retrofit.create(Disease_Interface.class);
        Call<List<Disease>> call = api.getDisease_Details(disease_name);
        call.enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(@NonNull Call<List<Disease>> call, @NonNull Response<List<Disease>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        test_details_array = new ArrayList<>(response.body());
                        if(!test_details_array.get(0).getTest_name().equals("0") && !test_details_array.get(0).getTest_price().equals("0") && !test_details_array.get(0).getTest_details().equals("0")) {
                            testname.setText(test_details_array.get(0).getTest_name());
                            testprice.setText(test_details_array.get(0).getTest_price());
                            testdetails.setText(test_details_array.get(0).getTest_details());
                        }
                        loadingProgressDialog.hide();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {
                loadingProgressDialog.hide();
                Toast.makeText(Diagnostics.this, "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}