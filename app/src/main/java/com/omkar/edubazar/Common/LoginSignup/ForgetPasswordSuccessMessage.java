package com.omkar.edubazar.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.omkar.edubazar.R;

public class ForgetPasswordSuccessMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_success_message);
    }

    public void forget_password_successful_message_login_btn_pressed(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}