package com.amozzafiato;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCarViewModel extends ViewModel {

    private static MutableLiveData<ArrayList<Favorite>> carsLiveData = new MutableLiveData<>();
    private static ArrayList<Favorite> carsList = new ArrayList<>();

    public LiveData<ArrayList<Favorite>> getCars() {
        return carsLiveData;
    }

    public void loadCars(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("TbFavorites")
//                .get()
//                .addOnSuccessListener(querySnapshot -> {
//                    if (!querySnapshot.isEmpty()) {
//                        carsList.clear();
//                        for (DocumentSnapshot document : querySnapshot) {
//                            if (Objects.equals(document.getString("idUser"), userId)) {
//                                Double idCar = document.getDouble("idCar");
//
//                                db.collection("TbCar")
//                                        .document(String.valueOf(idCar))
//                                        .get()
//                                        .addOnSuccessListener(documentCar -> {
//                                            carsList.add(
//                                                    new Favorite(documentCar.getString("name"), documentCar.getString("mainPhoto")));
//                                        });

//                                db.collection("TbCar")
//                                        .get()
//                                        .addOnSuccessListener(documentSnapshot -> {
//                                            carsList.clear();
//                                            for (DocumentSnapshot documentCar : documentSnapshot) {
//                                                carsList.add(new Favorite(documentCar.getString("name"), documentCar.getString("mainPhoto")));
//                                            }
//                                            carsLiveData.setValue(carsList);
//                                        });

//                            }
//                        }
//                        carsLiveData.setValue(carsList);
//                    }
//                });


//        carsList.clear();
//        db.collection("TbCar")
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    for (DocumentSnapshot documentCar : documentSnapshot) {
//                        db.collection("TbFavorites")
//                                .get()
//                                .addOnSuccessListener(queryFavorite -> {
//                                    if (!queryFavorite.isEmpty()) {
//                                        for (DocumentSnapshot documentFavorite : queryFavorite) {
//                                            if (Objects.equals(documentFavorite.get("idUser"), userId)
//                                                    && documentFavorite.get("idCar").equals(documentCar.getId())) {
//                                                Log.d("ProfileFavorites", "Dados atualizados: " + documentFavorite);
//                                                carsList.add(new Favorite(documentCar.getString("name"), documentCar.getString("mainPhoto")));
//                                                Log.d("ProfileFavorites", "Dados atualizados: " + documentCar.getString("name"));
//                                            }
//                                        }
//                                    }
//                                });
//                    }
//                });
//        carsLiveData.setValue(carsList);


//        carsList.clear();
//        db.collection("TbCar")
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    List<Task<?>> tasks = new ArrayList<>();
//                    for (DocumentSnapshot documentCar : documentSnapshot) {
//                        Log.d("ProfileFavorites", "DocumentCar: " + documentCar);
//
//                        Task<?> task = db.collection("TbFavorites")
//                                .whereEqualTo("idUser", userId)
//                                .whereEqualTo("idCar", documentCar.getId())
//                                .get()
//                                .addOnSuccessListener(queryFavorite -> {
//                                    if (!queryFavorite.isEmpty()) {
//                                        for (DocumentSnapshot documentFavorite : queryFavorite) {
//                                            carsList.add(
//                                                    new Favorite(documentCar.getString("name"), documentCar.getString("mainPhoto")));
//                                            Log.d("ProfileFavorites", "Dados atualizados: " + documentCar.getString("name"));
//                                        }
//                                    }
//                                });
//                        tasks.add(task);
//                    }
//
//                    // Aguarde a conclusão de todas as tarefas antes de atualizar o LiveData
//                    Tasks.whenAllComplete(tasks)
//                            .addOnSuccessListener(results -> {
//                                carsLiveData.setValue(carsList);
//                                Log.d("ProfileFavorites", "carsList: " + carsList);
//                            });
//                });


        db.collection("TbFavorites")
                .whereEqualTo("idUser", userId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    carsList.clear();

                    List<Task<?>> tasks = new ArrayList<>();

                    for (DocumentSnapshot document : querySnapshot) {
                        Double idCar = document.getDouble("idCar");
                        if (idCar != null) {
                            Task<DocumentSnapshot> carTask = db.collection("TbCar")
                                    .document(String.valueOf(idCar))
                                    .get()
                                    .addOnSuccessListener(documentCar -> {
                                        if (documentCar.exists()) {
                                            carsList.add(
                                                    new Favorite(
                                                            documentCar.getString("name"),
                                                            documentCar.getString("mainPhoto")
                                                    )
                                            );
                                        }
                                    });
                            tasks.add(carTask);
                        }
                    }

                    Tasks.whenAllComplete(tasks)
                            .addOnSuccessListener(results -> {
                                carsLiveData.setValue(carsList);
                            });
                })
                .addOnFailureListener(e -> {
                    // Lida com falhas na consulta
                    e.printStackTrace();
                });
    }


}