package nirmay.healthcare.niramayhealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Facility_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Get_Precaution;
import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.ui.diagnosis.DiagnosisFragment;
import nirmay.healthcare.niramayhealthcare.ui.facilities.FacilitiesAdapter;
import nirmay.healthcare.niramayhealthcare.ui.facilities.FacilityListFragment;
import nirmay.healthcare.niramayhealthcare.ui.profile.ProfileFragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Hospitals extends AppCompatActivity {

    ArrayList<Facility> hospitalList = new ArrayList<>();
    Hospital_Details_Adapter hda;
    RecyclerView hospital_details_recyclerview;
    TextView loadingText;
    ProgressDialog loadingProgressDialog;
    AlertDialog.Builder builder;
    String [] cityList = {"Select","Jalgaon","Pune","Nashik","Mumbai","Nagpur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        loadingText = findViewById(R.id.loading_text);
        builder = new AlertDialog.Builder(this);
        hospital_details_recyclerview = findViewById(R.id.diagnosis_hospital_details_recyclerview);
        hospital_details_recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        getCityName(LoginActivity.user_username,LoginActivity.user_password);

        getHospitalDetails(DiagnosisFragment.selectedCity);
        displayLoader();
//        if(!DiagnosisFragment.selectedCity.equals("")){
//            getHospitalDetails(DiagnosisFragment.selectedCity);
//            displayLoader();
//        }else {
//            builder.setMessage("Please add city first in your profile").setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
////                            Fragment f = new ProfileFragment();
////                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                            ft.replace(R.id.hospital_activity, f);
////                            ft.commit();
//                            Intent intent = new Intent(Hospitals.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//            AlertDialog alert =  builder.create();
//            alert.show();
//
//        }
    }

    public void displayLoader() {
        loadingProgressDialog = new ProgressDialog(this);
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Please Wait....");
        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.show();
    }

    public void getCityName(String username,String password)
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
                    getHospitalDetails(cityList[response.body().getCity()]);
                }

            }
            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                Toast.makeText(Hospitals.this, "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
                loadingProgressDialog.dismiss();
            }
        });
    }

    private void getHospitalDetails(String hosp_city) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Facility_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();

        Facility_Interface api = retrofit.create(Facility_Interface.class);
        Call<List<Facility>> call = api.getFacility_List(hosp_city);
        call.enqueue(new Callback<List<Facility>>() {
            @Override
            public void onResponse(@NonNull Call<List<Facility>> call, @NonNull Response<List<Facility>> response) {
                if (response.isSuccessful()) {
                    hospitalList = new ArrayList<>(response.body());
                    if (!hospitalList.get(0).getFac_city().equals("notfound")) {

                        hda = new Hospital_Details_Adapter(Hospitals.this, hospitalList);
                        hospital_details_recyclerview.setAdapter(hda);
                        loadingText.setVisibility(View.INVISIBLE);
                        loadingProgressDialog.hide();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Facility>> call, @NonNull Throwable t) {
                System.out.println("Failed: " + t);
                loadingText.setVisibility(View.INVISIBLE);
                loadingProgressDialog.hide();
                Toast.makeText(Hospitals.this, "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}