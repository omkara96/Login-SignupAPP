package com.omkar.edubazar.HelperClasses.HomeAdapter;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {

    int image;
    String title;
    GradientDrawable gradient;

    public CategoriesHelperClass(int image, String title, GradientDrawable gradient) {
        this.image = image;
        this.title = title;
        this.gradient = gradient;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GradientDrawable getGradient() {
        return gradient;
    }

    public void setGradient(GradientDrawable gradient) {
        this.gradient = gradient;
    }
}
