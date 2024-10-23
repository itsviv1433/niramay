package nirmay.healthcare.niramayhealthcare.Interface;

import nirmay.healthcare.niramayhealthcare.Pojo.ForgotpassPojo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Forgot_Pass_Interface {
    String REGIURL = "";

    @FormUrlEncoded
    @POST("forgotpass.php")
    Call<ForgotpassPojo> changePassword(
            @Field("username") String username,
            @Field("password") String password
    );
}
