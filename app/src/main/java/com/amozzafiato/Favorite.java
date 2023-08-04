package com.amozzafiato;

import java.util.ArrayList;

public class Favorite {
    public static ArrayList<Favorite> listContact = new ArrayList<>();
    private String nameCar;
    private int image;


    public Favorite(String nameCar, int image) {
        this.nameCar = nameCar;
        this.image = image;
    }

    public static void createFavorites(){
            listContact.add(new Favorite("Nissan R34 Skyline GT-R", R.drawable.carro));
            listContact.add(new Favorite("Golf GTI W12",  R.drawable.carro));
            listContact.add(new Favorite("Dodge Challenger SE - 1972", R.drawable.carro));
    }

    public String getNameCar() {
        return nameCar;
    }

    public int getImage() {
        return image;
    }
}
