package com.omkar.edubazar.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.omkar.edubazar.R;

public class AllCategories_New extends AppCompatActivity {

    ImageView back_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories__new);

        //Hooks
        back_Btn = findViewById(R.id.back_pressed);


        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategories_New.super.onBackPressed();
            }
        });
    }
}