package nirmay.healthcare.niramayhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import nirmay.healthcare.niramayhealthcare.Interface.Get_Medicine;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Medicine;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.diagnosis.DiagnosisFragment;
import nirmay.healthcare.niramayhealthcare.ui.profile.ProfileFragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pharmacy extends AppCompatActivity {

    ProgressDialog loadingProgressDialog;
    RecyclerView meds_recycler_view;
    ArrayList<Medicine> meds_details_array = new ArrayList<>();
    ArrayList<Disease> vaccine_details_array = new ArrayList<>();
    Medicine_Details_Adapter mdc;
    TextView vaccinename,vaccineprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        meds_recycler_view = findViewById(R.id.diagnosis_meds_details_recyclerview);
        meds_recycler_view.setLayoutManager(new LinearLayoutManager(Pharmacy.this));
        vaccinename = findViewById(R.id.vaccine_name_textview);
        vaccineprice = findViewById(R.id.vaccine_price_textview);

        if(!DiagnosisFragment.selectedDisease.equals("")){
            getmedsDetails(DiagnosisFragment.selectedDisease);
            getvaccinedetails(DiagnosisFragment.selectedDisease);
            displayLoader();
        }else
        {
            meds_recycler_view.setVisibility(View.GONE);

            Intent i = new Intent(Pharmacy.this, MainActivity.class);
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

    public void getmedsDetails(String disease) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Disease_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Facility_Interface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
               addConverterFactory(GsonConverterFactory.create())
                .build();

        .addConverterFactory(GsonConverterFactory.create(new Gson())).build();*/

        Get_Medicine api = retrofit.create(Get_Medicine.class);
        Call<List<Medicine>> call = api.getMedDetails(disease);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(@NonNull Call<List<Medicine>> call, @NonNull Response<List<Medicine>> response) {
                // System.out.println("Response :" + response.toString());
                if (response.body().get(0).getStatus() != null) {
                    if(!(response.body().get(0).getStatus().equals("false"))){
                        meds_details_array = new ArrayList<>(response.body());
                        loadingProgressDialog.hide();
                        mdc = new Medicine_Details_Adapter(Pharmacy.this,meds_details_array);
                        meds_recycler_view.setAdapter(mdc);
                    }else{
                        meds_recycler_view.setVisibility(View.GONE);
                    }
                }else
                {
                    meds_details_array = new ArrayList<>(response.body());
                    loadingProgressDialog.hide();
                    mdc = new Medicine_Details_Adapter(Pharmacy.this,meds_details_array);
                    meds_recycler_view.setAdapter(mdc);
                }
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                loadingProgressDialog.hide();
                Toast.makeText(Pharmacy.this, "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getvaccinedetails (String disease_name){

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
                        vaccine_details_array = new ArrayList<>(response.body());
                        if(!vaccine_details_array.get(0).getVaccine_name().equals("0") && !vaccine_details_array.get(0).getVaccine_price().equals("0")) {
                            vaccinename.setText("Vaccine Name : " + vaccine_details_array.get(0).getVaccine_name());
                            vaccineprice.setText("Vaccine Price : " + vaccine_details_array.get(0).getVaccine_price());
                        }
                        loadingProgressDialog.hide();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {
                loadingProgressDialog.hide();
                Toast.makeText(Pharmacy.this, "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}