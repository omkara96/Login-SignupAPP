package com.omkar.edubazar.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;

public class ForgetPassword extends AppCompatActivity {

    //Variables
    private ImageView screenIcon;
    private TextView title, description;
    private TextInputLayout phoneNumberTextField;
    private CountryCodePicker countryCodePicker;
    private Button nextBtn;
    private Animation animation;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Hooks
        screenIcon = findViewById(R.id.forgetPasswordImage_view);
        title = findViewById(R.id.forgetPasswordText);
        description = findViewById(R.id.forgetPasswordText2);
        phoneNumberTextField = findViewById(R.id.forgetPassword_PhoneNumber);
        countryCodePicker = findViewById(R.id.forgetPassword_countryCodePicker);
        nextBtn = findViewById(R.id.forgetPasswordNextBtn);
        progressbar = findViewById(R.id.progressBar);

        //Animation Hooks
        animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //Set Animation to all the elements
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumberTextField.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);
    }

    /*
    call the otp screen and
    pass the phone number for
    verification
     */

    public void forget_password_verify_phoneNumber(View view){

        //Check Internet Connection
        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
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
            return;
        }

        //validate fields
        if (!validatePhoneNumber()) {
            return;
        }   //Validation succeeded and now move to next screen to verify phone number and save data

        //get Data
        String _phoneNumber = phoneNumberTextField.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;
        progressbar.setVisibility(View.VISIBLE);
        //Check weather user exists in our database or not
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //If phone Number Exists then call OTP to Verifiy his/her Number
                if(dataSnapshot.exists()){
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo4",_completePhoneNumber);
                    intent.putExtra("whatToDo","updateData");
                    startActivity(intent);
                    finish();

                    progressbar.setVisibility(View.GONE);
                }else {
                    progressbar.setVisibility(View.GONE);
                    phoneNumberTextField.setError("No such User exists !");
                    phoneNumberTextField.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean validatePhoneNumber() {
        String val = phoneNumberTextField.getEditText().getText().toString().trim();
        String checkspaces = "[0-9]{10}";
        if (val.isEmpty()) {
            phoneNumberTextField.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumberTextField.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumberTextField.setError(null);
            phoneNumberTextField.setErrorEnabled(false);
            return true;
        }
    }

    public void forgetPasswordBackPressed(View view){
        super.onBackPressed();
    }
}