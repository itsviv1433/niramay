package nirmay.healthcare.niramayhealthcare.Interface;

import nirmay.healthcare.niramayhealthcare.Pojo.Profile_Pic;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Update_Profile_Interface {
    String REGIURL = "";


    @FormUrlEncoded
    @POST("setUserData.php")
    Call<Profile_Pic> update_UserData(
            @Field("user_username") String user_username,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("city") int city,
            @Field("photo") String photo,
            @Field("weight") String weight,
            @Field("height") String height,
            @Field("age") String age,
            @Field("gender") int gender,
            @Field("disease") int disease

    );


}
