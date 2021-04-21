package com.omkar.edubazar.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;

public class SignUp3rdClass extends AppCompatActivity {

    //Variables
    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleTxt;

    //Data Variables
    TextInputLayout clgName, clgCourse, city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks Anim
        backBtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleTxt = findViewById(R.id.signup_title_text3);

        //Hooks
        clgName = findViewById(R.id.signup_college_name);
        clgCourse = findViewById(R.id.signup_course);
        city = findViewById(R.id.signup_city);


    }



    public void call4thSignUpScreen(View view) {

        CheckInternet checkInternet = new CheckInternet();
        //Check internet
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        if(!validateClgName() | !validateCity() | !validateCourse() )
        {
            return;
        }

        //Get Data of Previous Activity i.e. From 2nd Form

        String _full_Name = getIntent().getStringExtra("fullName2") ;
        String _uname = getIntent().getStringExtra("uName2");
        String _Email = getIntent().getStringExtra("EMail2");
        String _Password = getIntent().getStringExtra("PassWord2");
        String _gender = getIntent().getStringExtra("gender2");
        String _date = getIntent().getStringExtra("date2");

        //Get Values of Current Activity

        String cName = clgName.getEditText().getText().toString().trim();
        String cCourse = clgCourse.getEditText().getText().toString().trim();
        String cCity = city.getEditText().getText().toString().trim();

        Intent intent = new Intent(SignUp3rdClass.this, SignUp4thClass.class);

        //Pass All fields to next activity.................
        intent.putExtra("fullName3",_full_Name);
        intent.putExtra("uName3",_uname);
        intent.putExtra("EMail3",_Email);
        intent.putExtra("PassWord3",_Password);
        intent.putExtra("gender3",_gender);
        intent.putExtra("date3",_date);
        intent.putExtra("college3",cName);
        intent.putExtra("course3",cCourse);
        intent.putExtra("city3",cCity);

        //Add Transition

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleTxt, "transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void callLoginFromSignUp3rd(View view)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    /*
    Validating Functions
     */

    private boolean validateClgName(){
        String val = clgName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            clgName.setError("Field can not be empty");
            return false;
        } else {
            clgName.setError(null);
            clgName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCourse(){
        String val = clgCourse.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            clgCourse.setError("Field can not be empty");
            return false;
        } else {
            clgCourse.setError(null);
            clgCourse.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCity(){
        String val = city.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            city.setError("Field can not be empty");
            return false;
        } else {
            city.setError(null);
            city.setErrorEnabled(false);
            return true;
        }
    }
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp3rdClass.this);
        builder.setMessage("Please Connect to internet to proceed further!")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),UserStartUpScreen.class));
                        finish();
                    }
                }).show();
    }

    public void SignUpBck3(View view)
    {
        super.onBackPressed();
    }

    public void signinSignup3(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}