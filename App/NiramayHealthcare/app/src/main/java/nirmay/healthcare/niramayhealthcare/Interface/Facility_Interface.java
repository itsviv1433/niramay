package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Facility_Interface {

    String REGIURL = "";


    @FormUrlEncoded
    @POST("getFacilityDetails.php")
    Call<List<Facility>> getFacility_List(
            @Field("fac_city") String fac_city
    );

    @POST("get_facilities_city_list.php")
    Call<List<Facility>> getFacilityCity_List(
    );

    @POST("get_facilitycitylist_spinner.php")
    Call<List> getFacilityCityList_Spinner(
    );

}
