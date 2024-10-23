package nirmay.healthcare.niramayhealthcare.Interface;

import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Get_User_Data_Interface {

    String REGIURL = "";

    @FormUrlEncoded
    @POST("getUserData.php")
    Call<nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data> getUserData(
            @Field("username") String username,
            @Field("password") String password
    );

}
