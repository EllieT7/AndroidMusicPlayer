package com.example.app_crud;

import android.graphics.Bitmap;

public class ModelClassImage {
    private String imageName;
    private Bitmap image;

    public ModelClassImage(String imageName, Bitmap image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
