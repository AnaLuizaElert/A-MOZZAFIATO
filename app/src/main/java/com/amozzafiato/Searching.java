package com.amozzafiato;

import java.util.ArrayList;

public class Searching {

    public static ArrayList<Searching> listCars = new ArrayList<>();

    private String image;
    private String name;
    private String price;

    public Searching(String image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
