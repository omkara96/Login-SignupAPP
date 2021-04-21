package com.omkar.edubazar.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omkar.edubazar.Databases.UserHelperClass;
import com.omkar.edubazar.R;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {


    PinView pinFromUser;
    String CodeBySystem;
    TextView otpDescriptionText;



    String fullName = null,phoneNo = null,eMail = null,userName = null,passWord = null,date = null,gender = null,college = null,course = null,city = null,whatToDo=null;

    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        //hooks
        pinFromUser = findViewById(R.id.pin_View);
        otpDescriptionText = findViewById(R.id.otp_Desc_text);

       // String _phoneNo = getIntent().getStringExtra("phoneNo");

        //Get All the data from Intents
        fullName = getIntent().getStringExtra("fullName4") ;
        userName = getIntent().getStringExtra("uName4");
        eMail = getIntent().getStringExtra("EMail4");
        passWord = getIntent().getStringExtra("PassWord4");
        gender = getIntent().getStringExtra("gender4");
        date = getIntent().getStringExtra("date4");
        college = getIntent().getStringExtra("college4");
        course = getIntent().getStringExtra("course4");
        city = getIntent().getStringExtra("city4");
        phoneNo = getIntent().getStringExtra("phoneNo4");
        whatToDo = getIntent().getStringExtra("whatToDo");

        otpDescriptionText.setText("Enter One Time Password Sent On "+phoneNo);
        rootNode = FirebaseDatabase.getInstance();
        String PH = phoneNo;
        sendVerificationCodeToUser(PH);

    }


  /*  public void getDatafromPreviousActivity()
    {
        /*********** Get All data from Previous Activity ******************


        Log.i("Tag",fullName);
        Log.i("Tag",userName);
        Log.i("Tag",eMail);
        Log.i("Tag",passWord);
        Log.i("Tag",gender);
        Log.i("Tag",date);
        Log.i("Tag",college);
        Log.i("Tag",course);
        Log.i("Tag",city);
        Log.i("Tag",phoneNo);
    }

    */


    private void sendVerificationCodeToUser(String phoneNo_User) {

        Toast.makeText(this,phoneNo_User,Toast.LENGTH_LONG).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo_User,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);


    }

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            CodeBySystem =s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code !=null){
                pinFromUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String code) {

        try{
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeBySystem,code);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show();
        }finally {
            Toast.makeText(this,"Server Busy",Toast.LENGTH_SHORT).show();
        }



    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if(whatToDo.equals("updateData")){
                                updateOldUserData();
                            }else if(whatToDo.equals("SignUpUser")){
                                storeNewUserData();
                                Intent intent = new Intent(VerifyOTP.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(VerifyOTP.this,"Succesfull",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                              Toast.makeText(VerifyOTP.this,"Verification not completed! Try again.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateOldUserData() {
        Intent intent = new Intent(getApplicationContext(),SetNewPassword.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullName,phoneNo,eMail,userName,passWord,date,gender,college,course,city);
        reference.child(phoneNo).setValue(addNewUser);

    }

    public void callNextFromOTP(View view)
    {
        String code = pinFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }

    public void cancelfromVrifyotpButton(View view){
        startActivity(new Intent(getApplicationContext(),UserStartUpScreen.class));
        finish();
    }
}