package com.omkar.edubazar.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.omkar.edubazar.Common.LoginSignup.UserStartUpScreen;
import com.omkar.edubazar.Databases.SessionManager;
import com.omkar.edubazar.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.omkar.edubazar.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.omkar.edubazar.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.omkar.edubazar.HelperClasses.HomeAdapter.FeaturedHealperClass;
import com.omkar.edubazar.HelperClasses.HomeAdapter.MostViewedAdpater;
import com.omkar.edubazar.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.omkar.edubazar.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static final float END_SCALE = 0.7f;

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    ;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    ImageView menuIcon;
    LinearLayout contentView;

    //Drawaer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);
        menuIcon = findViewById(R.id.menu_Icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        //Recycler Function calls
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    //Navigation Drawer Functions Start

    private void navigationDrawer() {

        //Navigation Drawaer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {


        drawerLayout.setScrimColor(getResources().getColor(R.color.colorAccent));
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_all_categories_new:
                startActivity(new Intent(UserDashboard.this, AllCategories_New.class));
                break;
        }
        return true;
    }


    //Navigation Drawer Functions END

    public void callUserStartupScreen(View view) {
        SessionManager sessionManager = new SessionManager(this, SessionManager.SESSION_USERSESSSION);
        if (sessionManager.checkLogin()) {
            Toast.makeText(this,"Already logged In!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UserRetailerDashboard.class));
        } else {
            startActivity(new Intent(getApplicationContext(), UserStartUpScreen.class));
        }

    }


    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHealperClass> featuredLocation = new ArrayList<>();

        featuredLocation.add(new FeaturedHealperClass(R.drawable.calculator, "Calculator", "Calculator is djfdjfgkdgjhdgdkjg gjkdgdkgjtdkg"));
        featuredLocation.add(new FeaturedHealperClass(R.drawable.ic_splash_screen_bg, "Product A", "Calculator is djfdjfgkdgjhdgdkjg gjkdgdkgjtdkg"));
        featuredLocation.add(new FeaturedHealperClass(R.drawable.ic_baseline_add_24, "Product B", "Calculator is djfdjfgkdgjhdgdkjg gjkdgdkgjtdkg"));

        adapter = new FeaturedAdapter(featuredLocation);
        featuredRecycler.setAdapter(adapter);

        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.general_icon, "Flats on Rent", gradient1));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.ic_baseline_add_24, "Looking for Stay", gradient2));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.ic_next_btn, "Looking for Tiffin Facility", gradient3));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.menu_ipen_icon, "Shopping", gradient4));
        categoriesHelperClasses.add(new CategoriesHelperClass(R.drawable.skip_icon, "Rent Your Bike", gradient1));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.calculator, "McDonald's"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.ic_baseline_add_24, "Edenrobe"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.ic_next_btn, "J."));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.skip_icon, "item xyz"));

        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }


}