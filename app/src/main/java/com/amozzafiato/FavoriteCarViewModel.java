package com.amozzafiato;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavoriteCarViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Favorite>> carsLiveData = new MutableLiveData<>();
    private ArrayList<Favorite> carsList = new ArrayList<>();

    public LiveData<ArrayList<Favorite>> getCars() {
        return carsLiveData;
    }

    public void loadCars(String carId, String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TbFavorites")
                .whereEqualTo("idUser", userId)
                .whereEqualTo("idCar", carId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        
                        db.collection("TbCar")
                                .document(carId)
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        String carName = documentSnapshot.getString("name");
                                        String carMainPhoto = documentSnapshot.getString("mainPhoto");
                                        carsList.add(new Favorite(carName, carMainPhoto));
                                        carsLiveData.setValue(carsList);
                                    }
                                });
                    }
                });
    }

}
