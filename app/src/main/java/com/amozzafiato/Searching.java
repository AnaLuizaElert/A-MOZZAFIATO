package com.amozzafiato;

import java.util.ArrayList;

public class Searching {

    public static ArrayList<Searching> listCars = new ArrayList<>();

    private int image;
    private String name;
    private String price;

    public Searching(int image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public static void createCars(){
        listCars.add(new Searching(R.drawable.carro,"Nissan R34 Skyline GT-R", "R$ 1.000.000"));
        listCars.add(new Searching(R.drawable.carro, "Golf GTI W12", "R$ 12.000.000"));
        listCars.add(new Searching(R.drawable.carro, "Dodge Challenger SE - 1972", "R$ 100.000"));
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
