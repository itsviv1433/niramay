package nirmay.healthcare.niramayhealthcare.ui.facilities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Disease_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Facility_Interface;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.search.SearchFragment;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacilityDetailsLayoutFragment extends Fragment {
    TextView loadingText;
    ProgressDialog loadingProgressDialog;
    RecyclerView facilities_recycler_view;
    FacilitiesAdapter fa;
    FrameLayout facilitiesDetailsLaout;
    ArrayList<Facility> facilitylist = new ArrayList<>();
    String city_name;

    public FacilityDetailsLayoutFragment(String city_name) {
        this.city_name = city_name;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_facilities_details_layout, container, false);
        loadingText = root.findViewById(R.id.loading_text);
        facilities_recycler_view = root.findViewById(R.id.facility_details_recyclerview);
        facilities_recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext()));
        facilitiesDetailsLaout = root.findViewById(R.id.facilities_recycler_layout);

//        city_name = FacilitiesFragment.searchbbar.getText().toString();
        getfacility(city_name);
        //  facilitiesDetailsLaout.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.VISIBLE);
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

    private void getfacility(String fac_city) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Facility_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Facility_Interface.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
               addConverterFactory(GsonConverterFactory.create())
                .build();

        .addConverterFactory(GsonConverterFactory.create(new Gson())).build();*/

        Facility_Interface api = retrofit.create(Facility_Interface.class);
        Call<List<Facility>> call = api.getFacility_List(fac_city);
        call.enqueue(new Callback<List<Facility>>() {
            @Override
            public void onResponse(@NonNull Call<List<Facility>> call, @NonNull Response<List<Facility>> response) {
//                System.out.println("Response :" + response.toString());
                if (response.isSuccessful()) {
                    facilitylist = new ArrayList<>(response.body());
                    if (facilitylist.get(0).getFac_city().equals("notfound")) {

                        Fragment f = new FacilityListFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.facilities_layout, f);
                        ft.commit();
                        loadingText.setVisibility(View.INVISIBLE);
                        loadingProgressDialog.hide();
                        Toast.makeText(getContext(), "No Search Results Found.", Toast.LENGTH_SHORT).show();
                    } else {
//                        facilitylist = new ArrayList<>(response.body());
//                        System.out.println(facilitylist);
                        facilitiesDetailsLaout.setVisibility(View.VISIBLE);
                        loadingText.setVisibility(View.INVISIBLE);
                        loadingProgressDialog.hide();
                        fa = new FacilitiesAdapter(getContext(), facilitylist);
                        facilities_recycler_view.setAdapter(fa);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Facility>> call, @NonNull Throwable t) {
                System.out.println("Failed: " + t);
                loadingText.setVisibility(View.INVISIBLE);
                loadingProgressDialog.hide();
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
