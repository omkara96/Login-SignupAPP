package com.omkar.edubazar.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;

public class SignUp4thClass extends AppCompatActivity {

    //Data Received From SignUp Activity 1
    String FullName = null, UserName = null, EMail = null, PassWord = null;
    //Data Received From SignUp Activity 2
    String gender = null, Dob = null;
    //Data Received From SignUp Activity 3
    String collegeName = null, collegeCourse = null, CurrCity = null;

    ScrollView scrollView;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;
    ProgressDialog progressDialog;
    String _completePhone;

    int flag=1;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4th_class);

        //Hooks
        scrollView = findViewById(R.id.signup4thScroolView);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        phoneNumber = findViewById(R.id.sign_UP_PhoneNumber);

        //progressBar
        progressDialog = new ProgressDialog(SignUp4thClass.this);
        progressDialog.setTitle("Checking...");
        progressDialog.setMessage("Please wait we are checking if user is previously registerd or not");

    }

   /* private void getDataFromSignUpActivity_page3() {
        //Get Data of Sign Up page 1
        Intent intent = getIntent();
        FullName = intent.getStringExtra("Full_Name");
        UserName = intent.getStringExtra("User_Name");
        EMail = intent.getStringExtra("e_Mail");
        PassWord = intent.getStringExtra("Pass_word");
        gender = intent.getStringExtra("Gender");
        Dob = intent.getStringExtra("DOB");
        collegeName = intent.getStringExtra("College_Name");
        collegeCourse = intent.getStringExtra("College_Course");
        CurrCity = intent.getStringExtra("City");
    }*/

    public void callOTPScreen(View view) {



        CheckInternet checkInternet = new CheckInternet();
        //Check internet
        if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        //validate fields
        if (!validatePhoneNumber()) {
            return;
        }   //Validation succeeded and now move to next screen to verify phone number and save data

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim(); //Get Phone Number;
        String _PhoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;
        _completePhone=_PhoneNo;


        String _full_Name = getIntent().getStringExtra("fullName3") ;
        String _uname = getIntent().getStringExtra("uName3");
        String _Email = getIntent().getStringExtra("EMail3");
        String _Password = getIntent().getStringExtra("PassWord3");
        String _gender = getIntent().getStringExtra("gender3");
        String _date = getIntent().getStringExtra("date3");
        String _college = getIntent().getStringExtra("college3");
        String _course = getIntent().getStringExtra("course3");
        String _city = getIntent().getStringExtra("city3");

        //Get Values of Current Activity

        progressDialog.show();
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_PhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                   //Create New User
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    //Pass All fields to next activity.................
                    intent.putExtra("fullName4",_full_Name);
                    intent.putExtra("uName4",_uname);
                    intent.putExtra("EMail4",_Email);
                    intent.putExtra("PassWord4",_Password);
                    intent.putExtra("gender4",_gender);
                    intent.putExtra("date4",_date);
                    intent.putExtra("college4",_college);
                    intent.putExtra("course4",_course);
                    intent.putExtra("city4",_city);
                    intent.putExtra("phoneNo4",_PhoneNo);
                    intent.putExtra("whatToDo","SignUpUser");

                    //Add Transitions
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(SignUp4thClass.this, pairs);
                        startActivity(intent, options.toBundle());
                        finish();
                    } else {
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(SignUp4thClass.this,"This Phone Number Already Exists" +
                            "\nUse Forget Password to retrive password"+"\n Or use different Phone Number", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                flag=0;
            }
        });
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

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp4thClass.this);
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


    /*****************      Back Pressed Listener **********/
    public void signUpBackBtn_4_Clicked(View view) {
        super.onBackPressed();
    }

    /*************** Call Login Screen Directly *********************/
    public void signUP4LoginBtn(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}