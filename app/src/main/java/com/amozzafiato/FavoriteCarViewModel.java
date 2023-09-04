package com.amozzafiato;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavoriteCarViewModel extends ViewModel {

    private static MutableLiveData<ArrayList<Favorite>> carsLiveData = new MutableLiveData<>();
    private static ArrayList<Favorite> carsList = new ArrayList<>();

    public LiveData<ArrayList<Favorite>> getCars() {
        return carsLiveData;
    }

    public void loadCars(String userId) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("TbFavorites")
//                .get()
//                .addOnSuccessListener(querySnapshot -> {
//                    if (!querySnapshot.isEmpty()) {
//                        for (DocumentSnapshot document : querySnapshot) {
//                            if (Objects.equals(document.getString("idUser"), userId)) {

//        db.collection("TbCar")
//                .get()
//                .addOnSuccessListener(querySnapshotCar -> {
//                    if (!querySnapshotCar.isEmpty()) {
//                        for (DocumentSnapshot documentCar : querySnapshotCar) {
////                                                    if (Objects.requireNonNull(
////                                                            documentCar.getDouble("idCar")).toString().equals(carId)
////                                                    ) {
//                            String carName = documentCar.getString("name");
//                            String carMainPhoto = documentCar.getString("mainPhoto");
//                            carsList.add(new Favorite(carName, carMainPhoto));
//                            carsLiveData.setValue(carsList);
////                                                    }
//                        }
//                    }
//                });

//                            }
//    }

        //                    }
//                });
//    }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TbCar")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    carsList.clear();
                    for (DocumentSnapshot document : documentSnapshot) {
                        carsList.add(new Favorite(document.getString("name"), document.getString("mainPhoto")));
                    }
                    carsLiveData.setValue(carsList);
                });
    }
}


