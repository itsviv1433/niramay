package nirmay.healthcare.niramayhealthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public TextView diagnosisCardView;
    public static NavigationView navview;
    private long pressedTime;
    AlertDialog.Builder builder;
    public static TextView navHeaderNameTextview,navHeaderUsernameTextview;
    public static ImageView profileImageView;
    SessionManagement sessionManagement;


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC5"));
        actionBar.setBackgroundDrawable(colorDrawable);

        sessionManagement = new SessionManagement(MainActivity.this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navview = findViewById(R.id.nav_view);

        View headerView = navview.getHeaderView(0);
        navHeaderNameTextview = headerView.findViewById(R.id.nav_header_name);
        profileImageView = headerView.findViewById(R.id.imageView);
        Glide.with(this).load(LoginActivity.profile_pic).into(profileImageView);
        navHeaderUsernameTextview = headerView.findViewById(R.id.nav_header_username);
        navHeaderNameTextview.setText("Hello,"+LoginActivity.user_name);
        navHeaderUsernameTextview.setText(LoginActivity.user_username);

        builder = new AlertDialog.Builder(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile,R.id.nav_search,R.id.nav_diagnosis,R.id.nav_facilities,R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navview, navController);

        navview.setBackgroundColor(Color.parseColor("#9568BD"));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem Logout = findViewById(R.id.logout);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.logout:

                builder.setMessage("Do you really want to logout...?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sessionManagement.removeSession();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alert =  builder.create();
                alert.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}