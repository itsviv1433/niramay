package nirmay.healthcare.niramayhealthcare.Interface;

import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.User_SignUp;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface User_Signup_Interface {

    String REGIURL = "";


    @FormUrlEncoded
   // @POST("userSignUp.php")
    @POST("signup.php")
    Call<User_SignUp> addUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );
}
