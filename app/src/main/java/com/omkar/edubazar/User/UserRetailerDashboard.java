package com.omkar.edubazar.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.omkar.edubazar.Databases.SessionManager;
import com.omkar.edubazar.R;

import java.util.HashMap;

public class UserRetailerDashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_retailer_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu_chip);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserRetailerDashboardFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.bottom_nav_dashboard:
                        fragment = new UserRetailerDashboardFragment();
                        break;

                    case R.id.bottom_nav_manage:
                        fragment = new UserRetailerSettingsFragment();
                        break;

                    case R.id.bottom_nav_profile:
                        fragment = new UserRetailerProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });

    }

    //logout Function
    public void userLogout(View view){
        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSSION);
        sessionManager.logoutUserFromSession();
        startActivity(new Intent(this, UserDashboard.class));
        finish();

    }
}