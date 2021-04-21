package com.omkar.edubazar.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omkar.edubazar.HelperClasses.SliderAdapter;
import com.omkar.edubazar.R;
import com.omkar.edubazar.User.UserDashboard;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    Button lets_get_started_btn;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    TextView temp;
    Animation animation;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        lets_get_started_btn = findViewById(R.id.getStartedBtn);

        //call adapter

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View View)
    {
        Toast.makeText(this,"Skip Clicked",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,UserDashboard.class);
        startActivity(intent);
        finish();
    }

    public void next(View view)
    {
        viewPager.setCurrentItem(currentPosition+1);
    }

    private void addDots(int position) {
        dots = new TextView[4];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText("•");
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            currentPosition = position;

            if (position == 0) {
                lets_get_started_btn.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                lets_get_started_btn.setVisibility(View.INVISIBLE);
            } else if(position == 2) {
                lets_get_started_btn.setVisibility(View.INVISIBLE);
            } else{
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                lets_get_started_btn.setAnimation(animation);
                lets_get_started_btn.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}