package nirmay.healthcare.niramayhealthcare.ui.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Disease_Interface;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    public static EditText search_bar;
    Button search;
    ProgressDialog loadingProgressDialog;
    //    public String disease_name;
    public String disease_clicked;
    public ArrayList<Disease> disease_list = new ArrayList<>();
    public ArrayList<Disease> disease_found = new ArrayList<>();
    RecyclerView diseasename_recycler_view;
    Search_Rows_Adapter sra;
    private ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout searchBarLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        search_bar = (EditText) root.findViewById(R.id.search_search_bar);
        search = (Button) root.findViewById(R.id.search_search_btn);
//        search_disease_details_layout = diseaseDetailsView.findViewById(R.id.search_disease_layout);
//        loadingtext = (TextView)root.findViewById(R.id.loading_text);

        diseasename_recycler_view = (RecyclerView) root.findViewById(R.id.diseasenamerecyclerview);
        diseasename_recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext()));

        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        searchBarLayout = root.findViewById(R.id.search_bar_layout);

        getdisease("");
//        displayLoader();
        shimmerFrameLayout.startShimmer();
        searchBarLayout.setVisibility(View.INVISIBLE);

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finddisease(search_bar.getText().toString());
                displayLoader();
            }
        });

        return root;
    }

    public void displayLoader() {
        loadingProgressDialog = new ProgressDialog(getContext());
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Please Wait....");
        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.show();
    }

    public void getdisease(String disease_name) {

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
        Call<List<Disease>> call = api.getDiseasename_List();
        call.enqueue(new Callback<List<Disease>>() {
            @Override
            public void onResponse(@NonNull Call<List<Disease>> call, @NonNull Response<List<Disease>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        disease_list = new ArrayList<>(response.body());
                        for (int i = 0; i < disease_list.size(); i++) {
                            if (disease_list.get(i).getDisease_name().equals("Select"))
                                disease_list.remove(i);
                        }
                        sra = new Search_Rows_Adapter(getContext(), disease_list, new Search_Rows_Adapter.OnNoteListener() {
                            @Override
                            public void onNoteClick(int position) {
                                disease_clicked = disease_list.get(position).getDisease_name();
                                Fragment f = new DiseaseDetailsLayoutFragment(disease_clicked);
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.disease_layout, f);
                                ft.commit();
                            }
                        });
                        diseasename_recycler_view.setAdapter(sra);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        searchBarLayout.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "No Search Result Found.", Toast.LENGTH_SHORT).show();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    }

                } else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Disease>> call, @NonNull Throwable t) {
                //System.out.println("Failed: " + t);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                searchBarLayout.setVisibility(View.VISIBLE);
//                loadingtext.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void finddisease(String disease_name) {

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
                    disease_found = new ArrayList<>(response.body());
                    if (!(disease_found.get(0).getDisease_symptoms().equals("notfound"))) {
                        Fragment f = new DiseaseDetailsLayoutFragment(disease_name);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.disease_layout, f);
                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "No Search Result Found.", Toast.LENGTH_SHORT).show();
                    }
                }
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                loadingProgressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Disease>> call, @NonNull Throwable t) {
                //System.out.println("Failed: " + t);
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                loadingProgressDialog.dismiss();
//                loadingtext.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
//    @Override
//    public void onNoteClick(int position) {
////        Fragment f = new DiseaseDetailsLayoutFragment();
////        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
////        ft.replace(R.id.disease_layout, f);
////        ft.commit();
////
//        Toast.makeText(getContext(), "disease_list.get(position);", Toast.LENGTH_SHORT).show();
//    }
