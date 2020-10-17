package com.example.foodjournal;

import android.graphics.Bitmap;

public class Experience {
    private String title, description, date;
    private Bitmap img;


    public Experience(String title, String description, String date, Bitmap img) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.img = img;


    }

    public String getDate() {
        return date;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setDate(String date) {
        this.date = date;
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
}
