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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;

    TextInputLayout fullName, userName, eMail, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_start_sign_up);

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);


        //Text Input Layout Hooks
        fullName = findViewById(R.id.signup_fullname);
        userName = findViewById(R.id.signup_username);
        eMail = findViewById(R.id.signup_email);
        passWord = findViewById(R.id.signup_password);

    }

    public void callNextSigupScreen(View view) {

        CheckInternet checkInternet = new CheckInternet();
        //Check internet
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        if(!validateFullname() | !validateUsername() | !validateeMail() | !validatePassword() )
        {
            return;
        }

        //Extract Data from text Input feilds------->
        String _full_Name = fullName.getEditText().getText().toString().trim();
        String _uname = userName.getEditText().getText().toString().trim();
        String _Email = eMail.getEditText().getText().toString().trim();
        String _Password = passWord.getEditText().getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        //Pass All fields to next activity.................
        intent.putExtra("fullName",_full_Name);
        intent.putExtra("uName",_uname);
        intent.putExtra("EMail",_Email);
        intent.putExtra("PassWord",_Password);

        //Add Shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
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

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    /*
    Validation Functions
     */

    private boolean validateFullname() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {
        String val = userName.getEditText().getText().toString().trim();
        String checkspaces = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";;

        if (val.isEmpty()) {
            userName.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            userName.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            userName.setError("No White spaces are allowed!");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateeMail() {
        String val = eMail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            eMail.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            eMail.setError("Invalid Email!");
            return false;
        } else {
            eMail.setError(null);
            eMail.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {
        String val = passWord.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            passWord.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            passWord.setError("Password should contain 4 characters!");
            return false;
        } else {
            passWord.setError(null);
            passWord.setErrorEnabled(false);
            return true;
        }
    }



    public void SignUpBck1(View view)
    {
        super.onBackPressed();
    }
}