package com.example.mclabproject;

import java.io.Serializable;

public class SearchItem implements Serializable {
    public String dishName;
    public String dishDescription;
    public String dishResturant;
    public String resturantLocation;
    public String dishCat;
    public String imageSrc;
    public Integer dishPrice;


    public SearchItem() {}

    public SearchItem(String dishName, String dishDescription, String dishResturant, String resturantLocation, String dishCat, String imageSrc, Integer dishPrice) {
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.dishResturant = dishResturant;
        this.resturantLocation = resturantLocation;
        this.dishCat = dishCat;
        this.imageSrc = imageSrc;
        this.dishPrice = dishPrice;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishResturant() {
        return dishResturant;
    }

    public void setDishResturant(String dishResturant) {
        this.dishResturant = dishResturant;
    }

    public String getDishCat() {
        return dishCat;
    }

    public void setDishCat(String dishCat) {
        this.dishCat = dishCat;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getResturantLocation() {
        return resturantLocation;
    }

    public void setResturantLocation(String resturantLocation) {
        this.resturantLocation = resturantLocation;
    }
}
