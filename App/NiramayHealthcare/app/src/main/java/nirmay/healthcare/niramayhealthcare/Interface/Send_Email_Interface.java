package nirmay.healthcare.niramayhealthcare.Interface;

import nirmay.healthcare.niramayhealthcare.Pojo.Email;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Send_Email_Interface {

    String REGIURL = "";

    @FormUrlEncoded
    @POST("feedbackemaildata.php")
    Call<Email> senduserfeedback(
            @Field("username") String username,
            @Field("subject") String subject,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("feedbackemaildata.php")
    Call<Email> senduserappriciationmail(
            @Field("username") String username,
            @Field("subject") String subject,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("emailData.php")
    Call<Email> sendEmailData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("send_signup_email.php")
    Call<Email> sendSignupMail();
}
