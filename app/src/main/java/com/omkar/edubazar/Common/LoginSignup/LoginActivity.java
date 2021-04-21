package com.omkar.edubazar.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.omkar.edubazar.Databases.SessionManager;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;
import com.omkar.edubazar.User.UserDashboard;
import com.omkar.edubazar.User.UserRetailerDashboard;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //Variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, passWord;
    ProgressDialog progressDialog;
    CheckBox rememberMe;
    TextInputEditText phoneNumber_editText, password_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_start_login);

        //hooks
        countryCodePicker = findViewById(R.id.login_countryCodePicker);
        phoneNumber = findViewById(R.id.login_PhoneNumber);
        passWord = findViewById(R.id.login_Password);
        rememberMe = findViewById(R.id.login_rememberCheckbox);
        phoneNumber_editText = findViewById(R.id.login_phone_number_edit_text);
        password_editText = findViewById(R.id.login_password_edit_text);

        //progressBar
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login...");
        progressDialog.setMessage("Logging in to your account");

        //Check Weather phone and password is already saved in shared prederences or not
        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRemembereMe()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRemembereMeDetailsFromSession();
            phoneNumber_editText.setText(rememberMeDetails.get(sessionManager.KEY_SESSIONPHONENUMBER));
            password_editText.setText(rememberMeDetails.get(sessionManager.KEY_SESSIONPASSWORD));
        }

    }

    public void callLoginfromlogin(View view) {

        CheckInternet checkInternet = new CheckInternet();
        //Check internet
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        //Validate username and Password

        if(!validatePhoneNumber() | !validatePassword() )
        {
            return;
        }

        progressDialog.show();

        //get Data
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = passWord.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        if(rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
            sessionManager.createRemembereMeSession(_phoneNumber,_password);
        }

        //Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);
                    String SystemPassword = dataSnapshot.child(_completePhoneNumber).child("passWord").getValue(String.class);

                    if (SystemPassword.equals(_password)) {
                        progressDialog.dismiss();
                        passWord.setError(null);
                        passWord.setErrorEnabled(false);

                        String _fullName = dataSnapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = dataSnapshot.child(_completePhoneNumber).child("eMail").getValue(String.class);
                        String _phone = dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dateofBirth = dataSnapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                        String d_password = dataSnapshot.child(_completePhoneNumber).child("passWord").getValue(String.class);
                        String _userName = dataSnapshot.child(_completePhoneNumber).child("userName").getValue(String.class);
                        String _gender = dataSnapshot.child(_completePhoneNumber).child("gender").getValue(String.class);
                        String _college = dataSnapshot.child(_completePhoneNumber).child("college").getValue(String.class);
                        String _city = dataSnapshot.child(_completePhoneNumber).child("city").getValue(String.class);
                        String _course = dataSnapshot.child(_completePhoneNumber).child("course").getValue(String.class);


                        //Create a session
                        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_USERSESSSION);
                        sessionManager.createLoginSession(_fullName,_userName,_email,_phone,d_password,_dateofBirth,_gender,_college,_city,_course);

                        startActivity(new Intent(getApplicationContext(), UserRetailerDashboard.class));

                        Toast.makeText(LoginActivity.this,_fullName+" \n"+_email+"\n"+_phone+" \n"+_dateofBirth,Toast.LENGTH_SHORT).show();


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "No such user Exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Call Forget Password
    public void callForgetPasswordScreen(View view){
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
        finish();
    }



    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "[0-9]{10}";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void login_backBtnClick(View view) {
       if(!isTaskRoot()){
           super.onBackPressed();
       }else {
           startActivity(new Intent(LoginActivity.this, UserDashboard.class));
           finish();
       }
    }

    public void clickCreateAccountfromLogin(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}