package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Medicine;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Get_Medicine {

    String REGIURL = "";

    @FormUrlEncoded
    @POST("get_Meds_Details.php")
    Call<List<Medicine>> getMedDetails(
            @Field("disease_name") String disease_name
    );

}
