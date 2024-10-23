package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Vaccine;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Get_Vaccine_Details {
    String REGIURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/";

    @GET("findByPin")
//    @Headers("User-Agent : Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
    @Headers("User-Agent:Chrome/90.0.4430.93")
    Call<List<Vaccine>> getVaccine_Details(
        @Query("pincode") int pincode,
        @Query("date") String date
    );

}
