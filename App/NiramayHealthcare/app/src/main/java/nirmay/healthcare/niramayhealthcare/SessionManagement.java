package nirmay.healthcare.niramayhealthcare;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_USERNAME = "session_user";
    String SESSION_PASSWORD = "session_pass";
    public static String session_username,session_password;

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserSessionData userSessionData){
        String username = userSessionData.getUsername();
        String password = userSessionData.getPassword();
        editor.putString(SESSION_USERNAME,username).commit();
        editor.putString(SESSION_PASSWORD,password).commit();

    }
    public boolean isLoggedIn(){
        SessionManagement.session_username = sharedPreferences.getString(SESSION_USERNAME,"null");
        SessionManagement.session_password = sharedPreferences.getString(SESSION_PASSWORD,"null");
        if(session_username.equals("null") || session_password.equals("null")){
            return false;
        }else {
            return true;
        }
    }
    public void removeSession(){
        editor.putString(SESSION_USERNAME,"null").commit();
        editor.putString(SESSION_PASSWORD,"null").commit();
    }
}
