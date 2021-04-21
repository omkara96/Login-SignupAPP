package com.omkar.edubazar.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.omkar.edubazar.R;

public class UserStartUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_start_up_screen);
    }

    public void callLoginScreen(View view)
    {
        Intent intent = new Intent(UserStartUpScreen.this, LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View , String>(findViewById(R.id.btn_Login),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserStartUpScreen.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else{
            startActivity(intent);
        }
    }

    public void callSignUpActivity(View view)
    {

        Intent intent = new Intent(UserStartUpScreen.this, SignUpActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View , String>(findViewById(R.id.btn_SignUp),"transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UserStartUpScreen.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else{
            startActivity(intent);
        }
    }
}