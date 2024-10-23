package nirmay.healthcare.niramayhealthcare.ui.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nirmay.healthcare.niramayhealthcare.Interface.Disease_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Facility_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Update_Profile_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.Get_User_Data_Interface;
import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.Pojo.Profile_Pic;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.SignUp;
import nirmay.healthcare.niramayhealthcare.ui.facilities.FacilityDetailsLayoutFragment;
import nirmay.healthcare.niramayhealthcare.ui.facilities.Search_Rows_Adapter_Facilities;
import nirmay.healthcare.niramayhealthcare.ui.search.DiseaseDetailsLayoutFragment;
import nirmay.healthcare.niramayhealthcare.ui.search.Search_Rows_Adapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    TextView edit_profile;
    Button save;
    EditText name_edittext, email_edittext, phone_edittext, weight_edittext, height_edittext, age_edittext;
    TextView bmi_textview, username_textview;
    LinearLayout profile_layout;
    ScrollView profile_linear_layout;
    ImageView profilepic, updateProfileButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    public static Bitmap bitmap;
    public String photo, user_username;
    public String updatedProfile = "";
    public static String photoUri, selected_disease_name;
    ProgressDialog pDialog;
    Spinner genderspinner, cityspinner, diseasespinner;
    private ShimmerFrameLayout shimmerFrameLayout;
    ArrayList<List> facilitycitylist = new ArrayList<>();
    ArrayList<List> diseaselist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edit_profile = view.findViewById(R.id.edit_profile_btn);
        profile_layout = (LinearLayout) view.findViewById(R.id.profile_details_layout);
        profile_linear_layout = view.findViewById(R.id.profilefrag);
        save = (Button) view.findViewById(R.id.edit_profile_save_btn);
        name_edittext = (EditText) view.findViewById(R.id.profile_name_edittxt);
        email_edittext = (EditText) view.findViewById(R.id.profile_email_edittxt);
        phone_edittext = (EditText) view.findViewById(R.id.profile_phone_edittxt);
        weight_edittext = (EditText) view.findViewById(R.id.profile_weight_edittxt);
        height_edittext = (EditText) view.findViewById(R.id.profile_height_edittxt);
        bmi_textview = (TextView) view.findViewById(R.id.profile_bmi_textview);
        profilepic = (ImageView) view.findViewById(R.id.profile_circleImageView);
        updateProfileButton = (ImageView) view.findViewById(R.id.update_profile_button);
        username_textview = (TextView) view.findViewById(R.id.profile_username_textview);
        age_edittext = view.findViewById(R.id.profile_age_edittxt);
        genderspinner = view.findViewById(R.id.profile_gender_spinner);
        cityspinner = view.findViewById(R.id.profile_city_spinnner);
        diseasespinner = view.findViewById(R.id.profile_disease_spinner);
        shimmerFrameLayout = view.findViewById(R.id.profile_shimmer_layout);

        bitmap = null;

//        displayLoader();
        getfacilityCityList();
        profile_linear_layout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();

        updateProfileButton.setEnabled(false);
        name_edittext.setEnabled(false);
        email_edittext.setEnabled(false);
        phone_edittext.setEnabled(false);
        weight_edittext.setEnabled(false);
        height_edittext.setEnabled(false);
        age_edittext.setEnabled(false);
        genderspinner.setEnabled(false);
        cityspinner.setEnabled(false);
        diseasespinner.setEnabled(false);
        save.setEnabled(false);

        username_textview.setText(LoginActivity.user_username);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        String prevPic = LoginActivity.profile_pic;
        Glide.with(this).load(prevPic).into(profilepic);
        Glide.with(this).load(prevPic).into(MainActivity.profileImageView);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
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
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileButton.setEnabled(true);
                name_edittext.setEnabled(true);
                email_edittext.setEnabled(true);
                phone_edittext.setEnabled(true);
                weight_edittext.setEnabled(true);
                height_edittext.setEnabled(true);
                age_edittext.setEnabled(true);
                genderspinner.setEnabled(true);
                cityspinner.setEnabled(true);
                diseasespinner.setEnabled(true);
                save.setEnabled(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pDialog.show();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (name_edittext.getText().toString().equals("") || email_edittext.getText().toString().equals("") || phone_edittext.getText().toString().equals("") || cityspinner.getSelectedItem().equals("Select") || weight_edittext.getText().toString().equals("") || height_edittext.getText().toString().equals("") || age_edittext.getText().toString().equals("") || genderspinner.getSelectedItem().equals("Select")) {
                    Toast.makeText(getContext(), "All Fields Are Mendatory.", Toast.LENGTH_SHORT).show();
                } else if (!email_edittext.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getContext(), "Enter Valid Email.", Toast.LENGTH_SHORT).show();
                } else if (phone_edittext.getText().toString().length() < 10) {
                    Toast.makeText(getContext(), "Please,Enter Valid Contact.", Toast.LENGTH_SHORT).show();
                } else {
                    updateProfile(LoginActivity.user_username, name_edittext.getText().toString(), email_edittext.getText().toString(), phone_edittext.getText().toString(), cityspinner.getSelectedItemPosition(), weight_edittext.getText().toString(), height_edittext.getText().toString(), age_edittext.getText().toString(), genderspinner.getSelectedItemPosition(), diseasespinner.getSelectedItemPosition());
                    updateProfileButton.setEnabled(false);
                    name_edittext.setEnabled(false);
                    email_edittext.setEnabled(false);
                    phone_edittext.setEnabled(false);
                    weight_edittext.setEnabled(false);
                    height_edittext.setEnabled(false);
                    age_edittext.setEnabled(false);
                    genderspinner.setEnabled(false);
                    cityspinner.setEnabled(false);
                    diseasespinner.setEnabled(false);
                    bmi_textview.setText("Your BMI is : " + getBmi(weight_edittext.getText().toString(), height_edittext.getText().toString()));
                }
//                if((!weight_edittext.getText().toString().equals("")) && (!height_edittext.getText().toString().equals(""))){
//
//                }
                save.setEnabled(true);
                //Toast.makeText(getContext(), "Profile Edited Successfully", Toast.LENGTH_SHORT).show();
            }
        });
//        if(prevPic.equals("https://niramayhealthcare.000webhostapp.com/images/profile_icon.jpg")) {
//            profilepic.setImageResource(R.drawable.profile_icon);
//
//        }
//        else {
//            Glide.with(this).load(prevPic).into(profilepic);
////            updateProfileButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    openFileChooser();
////                }
////            });
//        }

        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    public String getBmi(String weight, String height) {
        float w = Float.parseFloat(weight);
        float h = Float.parseFloat(height);
        float BMI = (w / h / h) * 10000;
//        BMI = String.format("%.2f",BMI);
//        return (Float.toString(BMI));
        return (String.format("%.2f", BMI));
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            photoUri = String.valueOf(filePath);
            try {

                // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                profilepic.setImageBitmap(bitmap);
                user_username = LoginActivity.user_username;
                updateProfile(LoginActivity.user_username, name_edittext.getText().toString(), email_edittext.getText().toString(), phone_edittext.getText().toString(), cityspinner.getSelectedItemPosition(), weight_edittext.getText().toString(), height_edittext.getText().toString(), age_edittext.getText().toString(), genderspinner.getSelectedItemPosition(), diseasespinner.getSelectedItemPosition());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void getProfile(String username, String password) {


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
        Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call = api.getUserData(username, password);

        call.enqueue(new Callback<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data>() {
            @Override
            public void onResponse(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Response<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> response) {

                if (response.isSuccessful()) {
//                    pDialog.dismiss();
                    profile_linear_layout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    if ((!response.body().getWeight().equals("0")) && (!response.body().getHeight().equals("0")) && (!response.body().getAge().equals("0"))) {
                        weight_edittext.setText(response.body().getWeight());
                        LoginActivity.user_weight = response.body().getWeight();
                        height_edittext.setText(response.body().getHeight());
                        LoginActivity.user_height = response.body().getHeight();
                        age_edittext.setText(response.body().getAge());
                        bmi_textview.setText("Your BMI is : " + getBmi(response.body().getWeight(), response.body().getHeight()));
                    }
                    name_edittext.setText(response.body().getName());
                    email_edittext.setText(response.body().getEmail());
                    phone_edittext.setText(response.body().getPhone());
                    cityspinner.setSelection(response.body().getCity());
                    genderspinner.setSelection(response.body().getGender());
                    LoginActivity.user_gender = response.body().getGender();
                    diseasespinner.setSelection(response.body().getDisease());
                }

            }

            @Override
            public void onFailure(@NonNull Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_LONG).show();
                System.out.println("Failed: " + t);
                //pDialog.dismiss();
            }
        });

    }

    private void updateProfile(String user_username, String name, String email, String phone, int city, String weight, String height, String age, int gender, int disease) {
        displayLoader();

        photo = null;
        if (bitmap == null) {
            photo = "";
        } else {
            photo = getStringImage(bitmap);
        }
        //System.out.println(photo);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Update_Profile_Interface.REGIURL)
                .addConverterFactory(GsonConverterFactory.create(gson));

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Profile_Pic_Interface.REGIURL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Call<Profile_Pic> call;
        Update_Profile_Interface api = retrofit.create(Update_Profile_Interface.class);
        if (bitmap == null) {
            //System.out.println("Field: "+user_username +"Field: "+ name+"Field: "+email+"Field: "+phone+"Field: "+photo+"Field: "+weight+"Field: "+height);
            call = api.update_UserData(user_username, name, email, phone, city, "", weight, height, age, gender, disease);
            System.out.println("Inside if");
        } else {
            call = api.update_UserData(user_username, name, email, phone, city, photo, weight, height, age, gender, disease);
            System.out.println("Inside else");
        }
        call.enqueue(new Callback<Profile_Pic>() {
            @Override
            public void onResponse(@NonNull Call<Profile_Pic> call, @NonNull Response<Profile_Pic> response) {
                // System.out.println("Response :" + response.body().getUpdated_profile() + "Older :"+ LoginActivity.profile_pic);
                if (response.body().getStatus().equals("Updated")) {
                    updatedProfile = response.body().getUpdated_profile();
                    Glide.with(getContext()).load(updatedProfile).into(profilepic);
                    Glide.with(getContext()).load(updatedProfile).into(MainActivity.profileImageView);
                    LoginActivity.profile_pic = updatedProfile;
                    LoginActivity.user_name = name_edittext.getText().toString();
                    MainActivity.navHeaderNameTextview.setText(name_edittext.getText().toString());
                    LoginActivity.user_height = height_edittext.getText().toString();
                    LoginActivity.user_weight = weight_edittext.getText().toString();
                    pDialog.dismiss();
                    selected_disease_name = diseasespinner.getSelectedItem().toString();
                    Toast.makeText(getContext(), "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Something Went Wrong.Please Try Again Later.", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile_Pic> call, @NonNull Throwable t) {
                System.out.println("Failed: " + t);
                pDialog.dismiss();
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
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
        Call<List> call = api.getFacilityCityList_Spinner();
        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(@NonNull Call<List> call, @NonNull Response<List> response) {
                if (response.isSuccessful()) {
                    facilitycitylist = new ArrayList<>(response.body());
                     ArrayAdapter<List> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,facilitycitylist);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    cityspinner.setAdapter(arrayAdapter);
                    getdiseaselist();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List> call, @NonNull Throwable t) {
                System.out.println("Failed: " + t);
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void getdiseaselist() {

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
        Call<List> call = api.getDiseasename_List_spinner();
        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(@NonNull Call<List> call, @NonNull Response<List> response) {
                if (response.isSuccessful()) {
                    diseaselist = new ArrayList<>(response.body());
                    ArrayAdapter<List> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,diseaselist);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    diseasespinner.setAdapter(arrayAdapter);
                    getProfile(LoginActivity.user_username, LoginActivity.user_password);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
