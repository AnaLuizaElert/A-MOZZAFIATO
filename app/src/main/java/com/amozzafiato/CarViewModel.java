package com.amozzafiato;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CarViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Searching>> carsLiveData = new MutableLiveData<>();
    private ArrayList<Searching> carsList = new ArrayList<>();

    public LiveData<ArrayList<Searching>> getCars() {
        return carsLiveData;
    }

    public void loadCars(String category) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TbCar").get()
                .addOnSuccessListener(documentSnapshot -> {
                    carsList.clear();
                    for (DocumentSnapshot document : documentSnapshot) {
                        if (document.get("category").equals(category) || category.equals("TODOS")) {
                            String price = "R$ " + document.getDouble("price");
                            carsList.add(new Searching(document.getString("mainPhoto"), document.getString("name"), price));
                        }
                    }
                    carsLiveData.setValue(carsList);
                });
    }
}

