package nirmay.healthcare.niramayhealthcare.ui.search;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Disease_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiseaseDetailsLayoutFragment extends Fragment {

    TextView diseaseName, disease_symptoms, disease_modesoftransmit, disease_information, disease_precaution, loadingText;
    ProgressDialog loadingProgressDialog;
    ScrollView diseaseScrollview;
    ArrayList<Disease> disease_details = new ArrayList<>();
    String disease_name;

    public DiseaseDetailsLayoutFragment(String disease_name) {
        this.disease_name = disease_name;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_disease_details_layout, container, false);
        diseaseName = (TextView) root.findViewById(R.id.search_disease_name);
        disease_symptoms = (TextView) root.findViewById(R.id.search_disease_symptoms);
        disease_modesoftransmit = (TextView) root.findViewById(R.id.search_disease_modesoftransmission);
        disease_information = (TextView) root.findViewById(R.id.search_disease_details);
        disease_precaution = (TextView) root.findViewById(R.id.search_disease_precaution);
        loadingText = root.findViewById(R.id.loading_text);
        diseaseScrollview = root.findViewById(R.id.disease_scrollview);

        diseaseScrollview.setVisibility(View.INVISIBLE);
//        disease_name = SearchFragment.search_bar.getText().toString();
        getdiseasedetails(disease_name);
        displayLoader();
        return root;
    }

    public void displayLoader() {
        loadingProgressDialog = new ProgressDialog(getContext());
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Please Wait....");
        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.show();
    }

    public void getdiseasedetails(String disease_name) {

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
                // System.out.println("Response :" + response.toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        disease_details = new ArrayList<>(response.body());
                        loadingProgressDialog.hide();
                        loadingText.setVisibility(View.INVISIBLE);
                        diseaseScrollview.setVisibility(View.VISIBLE);

                        diseaseName.setText(disease_details.get(0).getDisease_name());
                        disease_symptoms.setText(disease_details.get(0).getDisease_symptoms());
                        disease_modesoftransmit.setText(disease_details.get(0).getModes_of_transmission());
                        disease_information.setText(disease_details.get(0).getDisease_detail());
                        disease_precaution.setText(disease_details.get(0).getDisease_precaution());
                    }
                } else {
                    Toast.makeText(getContext(), "No Search Result Found.", Toast.LENGTH_SHORT).show();
                }
                loadingProgressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Disease>> call, Throwable t) {
                loadingProgressDialog.hide();
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

