package com.omkar.edubazar.HelperClasses.HomeAdapter;

public class FeaturedHealperClass {

    int image;
    String title, description,price;

    public FeaturedHealperClass(int image, String title, String description, String price) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public FeaturedHealperClass(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
