package nirmay.healthcare.niramayhealthcare.ui.userSignup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nirmay.healthcare.niramayhealthcare.LoginActivity;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.SignUp;


public class userSignup extends Fragment {
    public static Button create_user_acc;
    public static Button login;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_signup, container, false);

        create_user_acc = root.findViewById(R.id.create_user_acc_btn);
        login = root.findViewById(R.id.usersignup_activity_login_btn);
        create_user_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return root;

    }
}