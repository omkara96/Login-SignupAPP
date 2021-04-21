package com.omkar.edubazar.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.omkar.edubazar.User.UserRetailerDashboardFragment;

import java.util.HashMap;

public class SessionManager {

    //Variables

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //Session Names
    public static final String SESSION_USERSESSSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";


    //User Session Variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "passWord";
    public static final String KEY_EMAIL = "eMail";
    public static final String KEY_PHONENUMBER = "phoneNo";
    public static final String KEY_DATE = "date";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_COLLEGE = "college";
    public static final String KEY_COURCE = "Cource";
    public static final String KEY_CITY = "City";

    //Remember me variables
    private static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONPHONENUMBER = "phoneNo";
    public static final String KEY_SESSIONPASSWORD = "passWord";

    public SessionManager(Context _context, String sessionName) {
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }


    /*
    USers
    login
    Session
     */
    public void createLoginSession(String fullName, String userName, String email, String phoneNo, String password, String date, String gender, String college, String city, String course) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_USERNAME, userName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_COLLEGE, college);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_COURCE, course);
        editor.commit();
    }

    public HashMap<String, String> getUserDetailsFromSession() {
        HashMap<String, String> useData = new HashMap<String, String>();

        useData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        useData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        useData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        useData.put(KEY_PHONENUMBER, userSession.getString(KEY_PHONENUMBER, null));
        useData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        useData.put(KEY_DATE, userSession.getString(KEY_DATE, null));
        useData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));
        useData.put(KEY_COLLEGE, userSession.getString(KEY_COLLEGE, null));
        useData.put(KEY_CITY, userSession.getString(KEY_CITY, null));
        useData.put(KEY_COURCE, userSession.getString(KEY_COURCE, null));

        return useData;

    }

    public boolean checkLogin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    /*
    Remember Me
    Session Functions
     */
    public void createRemembereMeSession(String phoneNo, String password) {

        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONPHONENUMBER, phoneNo);
        editor.putString(KEY_SESSIONPASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getRemembereMeDetailsFromSession() {
        HashMap<String, String> useData = new HashMap<String, String>();


        useData.put(KEY_SESSIONPHONENUMBER, userSession.getString(KEY_SESSIONPHONENUMBER, null));
        useData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));
        return useData;

    }
    public boolean checkRemembereMe() {
        if (userSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }

}
