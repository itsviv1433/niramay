package nirmay.healthcare.niramayhealthcare;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;




public class APIFunction {

    public static JSONObject getAllSports(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=425001&date=25-05-2021")
                .method("GET", null)
                .addHeader("User-Agent", "Chrome/90.0.4430.93")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "hi_IN")
                .addHeader("Content-Type", "application/json")
                .build();

        JSONObject jsonObject;
        ResponseBody responseBody = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            responseBody = client.newCall(request).execute().body();
            String jsonResponseBodyString = responseBody.string();
            jsonObject = new JSONObject(jsonResponseBodyString);
            return jsonObject;

        } catch (IOException | JSONException e) {
            return null;
        }
    }
}
