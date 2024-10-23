package nirmay.healthcare.niramayhealthcare.ui.feedback;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nirmay.healthcare.niramayhealthcare.API26Wrapper;
import nirmay.healthcare.niramayhealthcare.BuildConfig;
import nirmay.healthcare.niramayhealthcare.Interface.Send_Email_Interface;
import nirmay.healthcare.niramayhealthcare.Interface.User_Signup_Interface;
import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.MainActivity;
import nirmay.healthcare.niramayhealthcare.Pojo.Email;
import nirmay.healthcare.niramayhealthcare.Pojo.Get_User_Data;
import nirmay.healthcare.niramayhealthcare.Pojo.User_SignUp;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.SignUp;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.getSystemServiceName;


public class FeedbackFragment extends Fragment {

    Button sendFeedback;
    EditText subjectEdittext, messageEdittext;
    ProgressDialog pDialog;
    String user_name, user_email, user_phone, user_subject, user_message;
    public final static int NOTIFICATION_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Sending Feedback...Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        sendFeedback = view.findViewById(R.id.send_feedback_btn);
        subjectEdittext = view.findViewById(R.id.subject_edittext);
        messageEdittext = view.findViewById(R.id.message_edittext);
        pDialog = new ProgressDialog(getContext());


//        sendNotify();
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_subject = subjectEdittext.getText().toString();
                user_message = messageEdittext.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (user_subject.equals("") || user_message.equals("")) {
                    Toast.makeText(getContext(), "All Fields Are Mendatory.", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(LoginActivity.user_username,user_subject, user_message);
                    displayLoader();
                }
            }
        });
        return view;
    }

    private void sendEmail(String username, String subject, String message) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Send_Email_Interface.REGIURL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Call<Email> call;
        Send_Email_Interface api = retrofit.create(Send_Email_Interface.class);
        if (user_subject.equals("") && user_message.equals("")) {
            call = api.senduserappriciationmail(username, "", "");
        } else {
            call = api.senduserfeedback(username, subject, message);
        }

        call.enqueue(new Callback<Email>() {
            @Override
            public void onResponse(@NonNull Call<Email> call, @NonNull Response<Email> response) {
                // System.out.println("Response :" + response.toString());
                if (response.body().getStatus().equals("sent")) {

                    user_subject = "";
                    user_message = "";
                    sendEmail(username, "", "");
                    //Toast.makeText(getContext(), "Feedback Sent Successfully.", Toast.LENGTH_SHORT).show();
                    // pDialog.dismiss();
                } else if (response.body().getStatus().equals("appriciationsent")) {
                    showNotification();
                    Toast.makeText(getContext(), "Feedback Sent Successfully.", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else if (response.body().getStatus().equals("error")) {
                    Toast.makeText(getContext(), "Feedback Not Sent.Try Again Later...", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Email> call, @NonNull Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Network Connection.", Toast.LENGTH_SHORT).show();
                System.out.println("Failure");
                System.out.println(t.getMessage());
            }
        });

    }

    public static Notification getNotification(final Context context) {

//        URL urlstring = new URL("https://niramayhealthcare.000webhostapp.com/images/niramay_logo.png");
//        Bitmap bitmap = null;
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        try {
//            bitmap = BitmapFactory.decodeStream(urlstring.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Notification.Builder notificationBuilder =
                Build.VERSION.SDK_INT >= 26 ? API26Wrapper.getNotificationBuilder(context) :
                        new Notification.Builder(context).setAutoCancel(true);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentText("About Feedback Appriciation to " + LoginActivity.user_name);
        notificationBuilder.setContentTitle("Niramay HealthCare Team");
//        notificationBuilder.setLargeIcon(bitmap);
//        notificationBuilder.setStyle(new Notification.BigPictureStyle().bigPicture(bitmap).bigLargeIcon((Bitmap) null));


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);

        notificationBuilder.setPriority(Notification.PRIORITY_MAX).setShowWhen(true)
                .setContentIntent(PendingIntent
                        .getActivity(context, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSmallIcon(R.drawable.notification_icon).setOngoing(true);
        return notificationBuilder.build();
    }

    private void showNotification() {
        ((NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE))
                .notify(NOTIFICATION_ID, getNotification(getContext()));
    }
}

















//
//
//    public void sendNotification() {
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                this.getContext()
//        )
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("Niramay HealthCare")
//                .setContentText("Feedback Received Successfully.Click to Check MailBox")
//                .setAutoCancel(true);
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setType("message/rfc822");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Intent.createChooser(intent, "Choose Your Email Application");
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
//                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
//        notificationManager.notify(0, builder.build());
//    }

//    public void sendNotify() {
//
//        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
////        Intent intent = new Intent(Intent.ACTION_VIEW);
////        intent.setType("message/rfc822");
//////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
////        Intent.createChooser(intent, "Choose Your Email Application");
//
//        Intent intent  = new Intent(this.getContext(),LoginActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), getString(R.string.app_name));
//        builder.setContentTitle("Request");
//        builder.setContentText("Are You Sure");
//        builder.setSmallIcon(R.drawable.notification_icon);
//        builder.setSound(uri);
//        builder.setAutoCancel(true);
//        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        builder.addAction(R.drawable.notification_icon, "yes", pendingIntent);
//        manager.notify(1, builder.build());
//    }
//}
//    public void feedbackreply(){
//
//        String replymessageText = "Dear "+name.getText().toString()+",\n\nYour Feedback is Received Successfully.\n" +
//                "Thank you so much for sharing your experience with us.We really appriciate it." +
//                "Let us know about your future experiences.\n\n" +
//                "If you have any queries or concerns feel free to contact us at...\n" +
//                "Our Email : healthcare.niramay@gmail.com.\n" +
//                "Our Instagram Handle : https://instagram.com/itz_viv_1433?igshid=vqf9krhjivh6 \n\n" +
//                "Best Regards,\nNIRAMAY HEALTHCARE";
//
//        final String username = "healthcare.niramay@gmail.com";
//        final String password = "HealthCare.Niramay01042021";
//        String msgtosend = replymessageText;
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth","true");
//        prop.put("mail.smtp.starttls.enable","true");
//        prop.put("mail.smtp.host","smtp.gmail.com");
//        prop.put("mail.smtp.port","587");
//
//        Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username,password);
//            }
//        });
//        try{
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getText().toString()));
//            message.setSubject("About Feedback Appriciation to "+name.getText().toString());
//            message.setText(msgtosend);
//            Transport.send(message);
//        }catch(MessagingException e){}
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//    }
//}


//                    Intent email = new Intent(Intent.ACTION_SEND);
//                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"itsviv1433@gmail.com"});
//                    email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
//                    email.putExtra(Intent.EXTRA_TEXT, messageText);
//                    email.setType("message/rfc822");
//                    startActivity(Intent.createChooser(email, "Choose an Email Application :"));
//final String username = "healthcare.niramay@gmail.com";
//    final String password = "HealthCare.Niramay01042021";
//    String msgtosend = messageText;
//    Properties prop = new Properties();
//                    prop.put("mail.smtp.auth","true");
//                    prop.put("mail.smtp.starttls.enable","true");
//                    prop.put("mail.smtp.host","smtp.gmail.com");
//                    prop.put("mail.smtp.port","587");
//
//    Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
//        @Override
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(username,password);
//        }
//    });
//                    try{
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(username));
//        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("niramayhealthcarefeedback@gmail.com"));
//        message.setSubject(subject.getText().toString());
//        message.setText(msgtosend);
//        Transport.send(message);
//        Toast.makeText(getContext(),"Feedback Sent Successfully.", Toast.LENGTH_SHORT).show();
//        mailsent = true;
//        //feedbackreply
//
//    }catch(MessagingException e){
//        networkoff = true;
//        Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
//    }
//                    if(mailsent){
//        Toast.makeText(getContext(),"Feedback Sent Successfully.", Toast.LENGTH_SHORT).show();
//        pDialog.dismiss();
//
//        String replymessageText = "Dear "+name.getText().toString()+",\n\nYour Feedback is Received Successfully.\n" +
//                "Thank you so much for sharing your experience with us.We really appriciate it." +
//                "Let us know about your future experiences.\n\n" +
//                "If you have any queries or concerns feel free to contact us at...\n" +
//                "Our Email : healthcare.niramay@gmail.com.\n" +
//                "Our Instagram Handle : https://instagram.com/itz_viv_1433?igshid=vqf9krhjivh6 \n\n" +
//                "Best Regards,\nNIRAMAY HEALTHCARE";
//
//        final String replyusername = "healthcare.niramay@gmail.com";
//        final String replypassword = "HealthCare.Niramay01042021";
//        String replymsgtosend = replymessageText;
//        Properties replyprop = new Properties();
//        replyprop.put("mail.smtp.auth","true");
//        replyprop.put("mail.smtp.starttls.enable","true");
//        replyprop.put("mail.smtp.host","smtp.gmail.com");
//        replyprop.put("mail.smtp.port","587");
//
//        Session replysession = Session.getInstance(prop, new javax.mail.Authenticator(){
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(replyusername,replypassword);
//            }
//        });
//        try{
//            Message replymessage = new MimeMessage(replysession);
//            replymessage.setFrom(new InternetAddress(username));
//            replymessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getText().toString()));
//            replymessage.setSubject("About Feedback Appriciation to "+name.getText().toString());
//            replymessage.setText(replymsgtosend);
//            Transport.send(replymessage);
//        }catch(MessagingException e){}
//    }else if(networkoff){
//        pDialog.dismiss();
//        //Toast.makeText(getContext(), "Something Went Wrong.Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
//    }
//}
//}
//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
