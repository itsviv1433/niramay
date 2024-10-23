package nirmay.healthcare.niramayhealthcare.ui.home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nirmay.healthcare.niramayhealthcare.HomeSliderData;
import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.SliderAdapter;
import nirmay.healthcare.niramayhealthcare.ui.diagnosis.DiagnosisFragment;
import nirmay.healthcare.niramayhealthcare.ui.facilities.FacilitiesFragment;
import nirmay.healthcare.niramayhealthcare.ui.feedback.FeedbackFragment;
import nirmay.healthcare.niramayhealthcare.ui.profile.ProfileFragment;
import nirmay.healthcare.niramayhealthcare.ui.search.SearchFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    CardView appAbstractCardView;
    TextView diagnosisTextview, profileCardView, searchCardView, facilityCardView, feedbackCardView, noSearchResult;
    SliderView sliderView;
    RecyclerView recyclerView;
    VaccineAdapter va;
    EditText dateEdittxt, pincodeEdittxt;
    Calendar myCalendar;
    DatePicker datePicker;
    Button searchCenterBtn, refreshBtn, bookBtn;
    ProgressBar loadingProgressDialog;
    ArrayList<HomeSliderData> sliderDataArrayList;
    GridLayout tableHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        appAbstractCardView = root.findViewById(R.id.app_abstract);
        diagnosisTextview = root.findViewById(R.id.diagnosis_abstract);
        profileCardView = root.findViewById(R.id.profile_abstract);
        searchCardView = root.findViewById(R.id.search_abstract);
        facilityCardView = root.findViewById(R.id.facility_abstract);
        feedbackCardView = root.findViewById(R.id.feedback_abstract);
        sliderView = root.findViewById(R.id.imageSlider);
        dateEdittxt = root.findViewById(R.id.date_edittext);
        pincodeEdittxt = root.findViewById(R.id.pincode_edittext);
        myCalendar = Calendar.getInstance();
        searchCenterBtn = root.findViewById(R.id.search_button);
        noSearchResult = root.findViewById(R.id.no_search_result);
        recyclerView = root.findViewById(R.id.vaccine_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        datePicker = new DatePicker(this.getContext());
        refreshBtn = root.findViewById(R.id.refresh_button);
        bookBtn = root.findViewById(R.id.book_button);
        tableHeader = root.findViewById(R.id.table_header_layout);

        noSearchResult.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        tableHeader.setVisibility(View.GONE);
        refreshBtn.setEnabled(false);
        setSliderImages();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dateEdittxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        searchCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                tableHeader.setVisibility(View.GONE);
                noSearchResult.setVisibility(View.GONE);
                if ((!dateEdittxt.getText().toString().equals("")) && (!pincodeEdittxt.getText().toString().equals(""))) {
                    if (pincodeEdittxt.getText().toString().length() < 6) {
                        Toast.makeText(getContext(), "Enter Valid Pincode", Toast.LENGTH_SHORT).show();
                    } else {
//                        displayLoader();
                        noSearchResult.setVisibility(View.GONE);
                        JSONObject membersobject = getVaccineDetails(pincodeEdittxt.getText().toString(), dateEdittxt.getText().toString());
                        if (membersobject != null) {
                            if (membersobject.toString().equals("{\"centers\":[]}")) {
                                recyclerView.setVisibility(View.GONE);
                                tableHeader.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                            }else {
                                try {
                                    refreshBtn.setEnabled(true);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    tableHeader.setVisibility(View.VISIBLE);
                                    JSONArray getArray = membersobject.getJSONArray("centers");
                                    va = new VaccineAdapter(getContext(), getArray);
                                    recyclerView.setAdapter(va);
                                } catch (JSONException e) {
                                    System.out.println("Exception :" + e);
                                    e.printStackTrace();
//                            loadingProgressDialog.setVisibility(View.INVISIBLE);
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Please Check Your Network Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Enter Pincode and Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noSearchResult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                tableHeader.setVisibility(View.GONE);
                if ((!dateEdittxt.getText().toString().equals("")) && (!pincodeEdittxt.getText().toString().equals(""))) {
                    if (pincodeEdittxt.getText().toString().length() < 6) {
                        Toast.makeText(getContext(), "Enter Valid Pincode", Toast.LENGTH_SHORT).show();
                    } else {
//                        displayLoader();
                        noSearchResult.setVisibility(View.GONE);
                        JSONObject membersobject = getVaccineDetails(pincodeEdittxt.getText().toString(), dateEdittxt.getText().toString());
                        if (membersobject != null) {
                            if (membersobject.toString().equals("{\"centers\":[]}")) {
                                recyclerView.setVisibility(View.GONE);
                                tableHeader.setVisibility(View.GONE);
                                noSearchResult.setVisibility(View.VISIBLE);
                            }else {
                                try {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    tableHeader.setVisibility(View.VISIBLE);
                                    JSONArray getArray = membersobject.getJSONArray("centers");
                                    va = new VaccineAdapter(getContext(), getArray);
                                    recyclerView.setAdapter(va);
                                } catch (JSONException e) {
                                    System.out.println("Exception :" + e);
                                    e.printStackTrace();
//                            loadingProgressDialog.setVisibility(View.INVISIBLE);
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Please Check Your Network Connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Enter Pincode and Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://selfregistration.cowin.gov.in/";
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(Uri.parse(url));
                startActivity(browser);
            }
        });

//        sliderView.addView(new ImageView(getContext()).setImageResource(R.drawable.niramay_logo));

        setCardListener(appAbstractCardView, diagnosisTextview, profileCardView, searchCardView, facilityCardView, feedbackCardView);
        return root;
    }

    public void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEdittxt.setText(sdf.format(myCalendar.getTime()));
    }

    public void displayLoader() {
        loadingProgressDialog = new ProgressBar(getContext());
//        loadingProgressDialog.setTitle("Fetching Centers Data");
//        loadingProgressDialog.setMessage("Please Wait....");
//        loadingProgressDialog.setCancelable(false);
        loadingProgressDialog.setVisibility(View.VISIBLE);
    }

    public JSONObject getVaccineDetails(String pincode, String date) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pincode + "&date=" + date)
                .method("GET", null)
                .addHeader("User-Agent", "Chrome/90.0.4430.93")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "hi_IN")
                .addHeader("Content-Type", "application/json")
                .build();

        JSONObject jsonObject;
        ResponseBody responseBody = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            responseBody = client.newCall(request).execute().body();
            String jsonResponseBodyString = responseBody.string();
            jsonObject = new JSONObject(jsonResponseBodyString);
//            loadingProgressDialog.setVisibility(View.INVISIBLE);
            return jsonObject;

        } catch (IOException | JSONException e) {
//            loadingProgressDialog.setVisibility(View.INVISIBLE);
            return null;
        }
    }

    public void setSliderImages() {

//        String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
        String url1 = "https://niramayhealthcare.000webhostapp.com/images/niramay_logo.png";
        String url2 = "https://niramayhealthcare.000webhostapp.com/images/left_image.jpg";
        String url3 = "https://niramayhealthcare.000webhostapp.com/images/right_image.jpg";

        sliderDataArrayList = new ArrayList<>();

        sliderDataArrayList.add(new HomeSliderData(url1));
        sliderDataArrayList.add(new HomeSliderData(url2));
        sliderDataArrayList.add(new HomeSliderData(url3));

        SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(2);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();
    }


    public void setCardListener(CardView appAbstract, TextView diagnosisAbstract, TextView profileAbstract, TextView searchAbstract, TextView facilityAbstract, TextView feedbackAbstract) {
        diagnosisAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = new DiagnosisFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.home_frag, f);
                ft.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Diagnosis");
                MainActivity.navview.getMenu().getItem(3).setChecked(true);
            }
        });
        profileAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = new ProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.home_frag, f);
                ft.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
                MainActivity.navview.getMenu().getItem(1).setChecked(true);
            }
        });
        searchAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = new SearchFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.home_frag, f);
                ft.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search");
                MainActivity.navview.getMenu().getItem(2).setChecked(true);
            }
        });
        facilityAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = new FacilitiesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.home_frag, f);
                ft.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nearby Facilities");
                MainActivity.navview.getMenu().getItem(4).setChecked(true);
            }
        });
        feedbackAbstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment f = new FeedbackFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.home_frag, f);
                ft.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Feedback");
                MainActivity.navview.getMenu().getItem(5).setChecked(true);
            }
        });

    }
}