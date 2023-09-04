package com.amozzafiato;

import java.util.ArrayList;

public class Favorite {
    public static ArrayList<Favorite> listFavorite = new ArrayList<>();
    private String nameCar;
    private String image;

    public Favorite(String nameCar, String image) {
        this.nameCar = nameCar;
        this.image = image;
    }

    public String getNameCar() {
        return nameCar;
    }

    public String getImage() {
        return image;
    }
}
