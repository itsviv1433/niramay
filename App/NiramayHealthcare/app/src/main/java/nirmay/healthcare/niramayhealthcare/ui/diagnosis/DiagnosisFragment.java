package nirmay.healthcare.niramayhealthcare.ui.diagnosis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import nirmay.healthcare.niramayhealthcare.Diagnostics;
import nirmay.healthcare.niramayhealthcare.Health_Status;
import nirmay.healthcare.niramayhealthcare.Hospitals;
import nirmay.healthcare.niramayhealthcare.Interface.Get_Precaution;
import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.Pharmacy;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.profile.ProfileFragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiagnosisFragment extends Fragment {

    CardView diagnostics, pharmacy, hospital, healthstatus,diagnosis_precaution;
    TextView name, username, precaution;
    CircleImageView profile_image;
    ProgressDialog pDialog;
    LinearLayout diagnosisFragmentLayout;
    ArrayList<Disease> disease_precaution = new ArrayList<>();
    String [] cityList = {"Select","Jalgaon","Pune","Kolhapur","Nagpur","Chandrapur","Nashik","Mumbai","Malkapaur","Yawatmal","Aurangabad","Bhusawal","Latur","Akola"};
    String [] diseases = {"Select","Cancer","Tuberclosis","Dibetes","Allergies","Cold And Flue","Diarrhea","Headaches","Corona"};
    public static String selectedDisease = "",selectedCity = "";
    AlertDialog.Builder builder;
    private ShimmerFrameLayout shimmerFrameLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diagnosis, container, false);

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });


        diagnostics = root.findViewById(R.id.diagnostic_cardview);
        pharmacy = root.findViewById(R.id.pharmacy_cardview);
        hospital = root.findViewById(R.id.hospital_cardview);
        healthstatus = root.findViewById(R.id.dietplan_cardview);
        name = root.findViewById(R.id.patient_name);
        username = root.findViewById(R.id.patient_username);
        precaution = root.findViewById(R.id.diagnosis_precaution_textview);
        profile_image = root.findViewById(R.id.diagnosis_profile_image);
        diagnosis_precaution = root.findViewById(R.id.diagnosis_precaution_cardview);
        DiagnosisFragment.selectedDisease = "";
        DiagnosisFragment.selectedCity = "";

        shimmerFrameLayout = root.findViewById(R.id.diagnosis_shimmer_layout);
        diagnosisFragmentLayout= root.findViewById(R.id.diagnosis_frag_layout);

        builder = new AlertDialog.Builder(getContext());

        name.setText(LoginActivity.user_name);
        username.setText(LoginActivity.user_username);
        Glide.with(this).load(LoginActivity.profile_pic).into(profile_image);

        setOnclickonCard();
        getDiseaseName(LoginActivity.user_username,LoginActivity.user_password);
//        displayLoader();
        diagnosisFragmentLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        return root;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void setOnclickonCard() {

        diagnostics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DiagnosisFragment.selectedDisease.equals("") && !DiagnosisFragment.selectedDisease.equals("Select")){
                    Intent i = new Intent(getContext(), Diagnostics.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getContext(), "You Dont Have Any Disease", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DiagnosisFragment.selectedDisease.equals("") && !DiagnosisFragment.selectedDisease.equals("Select")){
                    Intent i = new Intent(getContext(), Pharmacy.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getContext(), "You Dont Have Any Disease", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!DiagnosisFragment.selectedCity.equals("") && !DiagnosisFragment.selectedCity.equals("Select")){
                    Intent i = new Intent(getContext(), Hospitals.class);
                    startActivity(i);
//                    displayLoader();
                }else {
                    builder.setMessage("Please add city first in your profile").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert =  builder.create();
                    alert.show();

                }

            }
        });
        healthstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Health_Status.class);
                startActivity(i);
            }
        });
    }
    public void getDiseaseName(String username,String password)
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
                    if(response.body().getDisease() != 0){
                        DiagnosisFragment.selectedDisease = diseases[response.body().getDisease()];
                        getPrecaution(diseases[response.body().getDisease()]);
                    }else{
                        diagnosis_precaution.setVisibility(View.INVISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        diagnosisFragmentLayout.setVisibility(View.VISIBLE);
                    }
                    if(response.body().getCity() != 0){
                        DiagnosisFragment.selectedCity = cityList[response.body().getCity()];
                    }
//                    pDialog.dismiss();

                }

            }
            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
//                pDialog.dismiss();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                diagnosisFragmentLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void getPrecaution(String disease) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Get_Precaution.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        Get_Precaution api = retrofit.create(Get_Precaution.class);
        Call<List<Disease>> call = api.getPrecaution(disease);


        call.enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(@NonNull Call<List<nirmay.healthcare.niramayhealthcare.Pojo.Disease>> call, @NonNull Response<List<Disease>> response) {

                if (response.isSuccessful()) {
                    disease_precaution = new ArrayList<>(response.body());
//                    pDialog.dismiss();
                    precaution.setText(disease_precaution.get(0).getDisease_precaution());
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    diagnosisFragmentLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<nirmay.healthcare.niramayhealthcare.Pojo.Disease>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
//                pDialog.dismiss();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                diagnosisFragmentLayout.setVisibility(View.VISIBLE);
            }
        });

    }
}