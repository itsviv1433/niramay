package nirmay.healthcare.niramayhealthcare.ui.facilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Facility_Interface;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.search.DiseaseDetailsLayoutFragment;
import nirmay.healthcare.niramayhealthcare.ui.search.Search_Rows_Adapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class FacilitiesFragment extends Fragment {

    ArrayList<Facility> facilitycitylist = new ArrayList<>();
    Search_Rows_Adapter_Facilities sraf;
    public static EditText searchbbar;
    Button searchbtn;
    ProgressDialog loadingProgressDialog;
    public String fac_city;
    public String fac_city_clicked;
    RecyclerView recyclerView;
    TextView loadingText;
    private ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout searchBarLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facilities, container, false);
        //Recycler view intialize

//        facilitycitylist.add("Jalgaon");
//        facilitycitylist.add("Pune");
//        facilitycitylist.add("Nashik");
//        facilitycitylist.add("Mumbai");
//        facilitycitylist.add("Nagpur");
//        facilitycitylist.add("Satara");
//        facilitycitylist.add("Buldhana");
//        facilitycitylist.add("Beed");
//        facilitycitylist.add("Kolhapur");


        //Data Stream
        searchbbar = (EditText) view.findViewById(R.id.enter_city_edittext);
        searchbtn = (Button) view.findViewById(R.id.search_btn);
        loadingText = (TextView) view.findViewById(R.id.loading_text);
        recyclerView = (RecyclerView)view.findViewById(R.id.facilities_recyclerview);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        searchBarLayout = view.findViewById(R.id.search_bar_layout);

        shimmerFrameLayout.startShimmer();
        searchBarLayout.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        getfacilityCityList();
//        loadingText.setVisibility(View.INVISIBLE);


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        } );

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fac_city = searchbbar.getText().toString();
                Fragment f = new FacilityDetailsLayoutFragment(fac_city);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.facilities_layout, f);
                ft.commit();
            }
        });

        return view;
    }
    public void displayLoader(){
        loadingProgressDialog = new ProgressDialog(getContext());
        loadingProgressDialog.setTitle("Loading...");
        loadingProgressDialog.setMessage("Please Wait....");
        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.show();
    }

    private void getfacilityCityList() {

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
        Call<List<Facility>> call = api.getFacilityCity_List();
        call.enqueue(new Callback<List<Facility>>() {
            @Override
            public void onResponse(@NonNull Call<List<Facility>> call, @NonNull Response<List<Facility>> response) {
                if (response.isSuccessful()) {
                    facilitycitylist = new ArrayList<>(response.body());
                    facilitycitylist.remove(0);
                    sraf = new Search_Rows_Adapter_Facilities(getContext(), facilitycitylist, new Search_Rows_Adapter_Facilities.OnNoteListener() {
                        @Override
                        public void onNoteClick(int position) {
                            fac_city_clicked = facilitycitylist.get(position).getCities();
                            Fragment f = new FacilityDetailsLayoutFragment(fac_city_clicked);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.facilities_layout, f);
                            ft.commit();
                        }
                    });
                    recyclerView.setAdapter(sraf);
                    shimmerFrameLayout.stopShimmer();
                    searchBarLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Facility>> call, @NonNull Throwable t) {
                System.out.println("Failed: " + t);
                shimmerFrameLayout.stopShimmer();
                searchBarLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
    /*public void save(View v) throws IOException {
        String text = searchbbar.getText().toString();
//        File root = new File(Environment.getExternalStorageDirectory(),"Facility");
//        File Facility = new File(root,FILE_NAME);
//        if(!Facility.exists()){
//            Facility.mkdirs();
//            if(Facility.mkdirs())
//            System.out.println(Environment.getExternalStorageDirectory().toString());
//        }
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(text.getBytes());
            test.setText(getActivity().getFilesDir().getAbsolutePath());
            System.out.println(getActivity().getFilesDir().getAbsolutePath());
            //FileWriter writer = new FileWriter(Facility,true); // Exception called....
           // System.out.println("Directory Created....");
//            writer.append(text);
//            writer.flush();
            fos.close();
            searchbbar.getText().clear();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    public void load(View v) throws IOException {
        FileInputStream fis = null;
        fis = getActivity().openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;

        while((text = br.readLine()) != null){
            sb.append(text).append("\n");
        }
        test.setText(sb.toString());
    }*/