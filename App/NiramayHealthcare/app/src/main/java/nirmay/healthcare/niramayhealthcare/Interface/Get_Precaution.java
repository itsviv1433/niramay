package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Get_Precaution {
    String REGIURL = "";


    @FormUrlEncoded
    @POST("getDiseaseDetails.php")
    Call<List<Disease>> getPrecaution(
            @Field("disease_name") String disease_name
    );
}
