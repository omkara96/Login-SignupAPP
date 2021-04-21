package com.omkar.edubazar.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omkar.edubazar.Databases.SessionManager;
import com.omkar.edubazar.R;


public class UserRetailerDashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_retailer_dashboard, container, false);
    }

    /*logout Function
    public void userLogout(View view){
        Context _context = getActivity().getApplicationContext();
        SessionManager sessionManager = new SessionManager(_context, SessionManager.SESSION_USERSESSSION);
        sessionManager.logoutUserFromSession();
        startActivity(new Intent(_context, UserDashboard.class));
        getActivity().finish();
    }*/
}