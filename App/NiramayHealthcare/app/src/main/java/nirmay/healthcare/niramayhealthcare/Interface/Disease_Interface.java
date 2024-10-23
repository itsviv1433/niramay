package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Disease_Interface {
    String REGIURL = "";


    @FormUrlEncoded
    @POST("getDiseaseDetails.php")
    Call<List<Disease>> getDisease_Details(
            @Field("disease_name") String disease_name
    );

    @POST("get_disease_recyclerview_data.php")
    Call<List<Disease>> getDiseasename_List();

    @POST("get_disease_list_spinner.php")
    Call<List> getDiseasename_List_spinner();

}
