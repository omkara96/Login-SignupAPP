package com.omkar.edubazar.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omkar.edubazar.HelperClasses.CheckInternet;
import com.omkar.edubazar.R;

public class SetNewPassword extends AppCompatActivity {

    //Variables
    TextInputLayout newPassword, confirmNewPassword;
    ProgressBar progressBar;

    String _newPassword = null, _confirmPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        //Hooks
        newPassword = findViewById(R.id.setNewPassword_newPassword);
        confirmNewPassword = findViewById(R.id.setNewPassword_confirmNewPassword);
        progressBar = findViewById(R.id.progressBar2);
    }

    public void setNewPasswordBtn(View view){

        //Check internet Connection
        CheckInternet checkInternet = new CheckInternet();

        if(!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }

        //Validate password fields
        if(!validateNewPassword() | !validateConfirmPassword()){
            return;
        }

        //set Progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        //get Data from fields
        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        //update data in firebase;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("passWord").setValue(_newPassword);

        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class));
        finish();
    }

    /*
    validation functions for password
     */
    private boolean validateNewPassword() {
        String val = newPassword.getEditText().getText().toString().trim();
        _newPassword = val;
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
            newPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            newPassword.setError("Password should contain 4 characters!");
            return false;
        } else {
            newPassword.setError(null);
            newPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = confirmNewPassword.getEditText().getText().toString().trim();

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
            confirmNewPassword.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            confirmNewPassword.setError("Password should contain 4 characters!");
            return false;
        }else if(!val.equals(_newPassword)){
            confirmNewPassword.setError("Password did not Match. Re Enter.");
            return false;
        }
        else {
            confirmNewPassword.setError(null);
            confirmNewPassword.setErrorEnabled(false);
            return true;
        }
    }



    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
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

}