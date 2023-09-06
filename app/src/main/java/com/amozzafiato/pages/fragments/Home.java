package com.amozzafiato.pages.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;
import com.amozzafiato.pages.PageCar;
import com.amozzafiato.pages.SearchingCar;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends Fragment {

    private Space emptySpace;
    private ScrollView scrollView;
    private int originalSpaceHeight, count;
    private Button seeCollectionButton;
    private LinearLayout firstCar, secondCar, thirdCar;
    private TextView nameCar, price;
    private String nameFirstCar, nameSecondCar, nameThirdCar;
    private ImageView mainPhoto;

    @SuppressLint({"CutPasteId", "MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializa as Views
        emptySpace = view.findViewById(R.id.emptySpace);
        scrollView = view.findViewById(R.id.scrollView);
        seeCollectionButton = view.findViewById(R.id.fragment_home_button_see_collection);

        //Inicializa os containers dos carros
        firstCar = view.findViewById(R.id.home_first_car);
        secondCar = view.findViewById(R.id.home_second_car);
        thirdCar = view.findViewById(R.id.home_third_car);

        // Obtém a altura original do espaço vazio (Space) e a altura do botão
        originalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.space_height);
        int buttonHeight = seeCollectionButton.getHeight();
        // Rolando para o topo da página
        scrollView.post(() -> scrollView.scrollTo(0, 0));

        // Escuta as alterações de rolagem do ScrollView
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Obtém o scrollY atual do ScrollView
                int scrollY = scrollView.getScrollY();

                // Ajuste o fator de ajuste para controlar a rapidez da diminuição do espaço
                float sizeAdjustmentFactor = 0.8f;

                // Calcula a altura desejada para o espaço vazio de acordo com o fator de ajuste
                int desiredSpaceHeight = (int) (originalSpaceHeight - scrollY * sizeAdjustmentFactor);

                // Limita a altura mínima
                int minSpaceHeight = 10;
                int clampedSpaceHeight = Math.max(minSpaceHeight, desiredSpaceHeight);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) emptySpace.getLayoutParams();
                layoutParams.height = clampedSpaceHeight;
                emptySpace.setLayoutParams(layoutParams);
            }
        });

        generateDataDB(view);

        seeCollectionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "TODOS");
            startActivity(intent);
        });

        firstCar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PageCar.class);
            intent.putExtra("carName", nameFirstCar);
            startActivity(intent);
        });

        secondCar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PageCar.class);
            intent.putExtra("carName", nameSecondCar);
            startActivity(intent);
        });

        thirdCar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PageCar.class);
            intent.putExtra("carName", nameThirdCar);
            startActivity(intent);
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void generateDataDB(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("TbCar")
                .limit(3)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        count++;
                        if (count == 1) {
                            nameFirstCar = documentSnapshot.getString("name");

                            nameCar = view.findViewById(R.id.home_first_name_car);
                            price = view.findViewById(R.id.home_first_price_car);
                            mainPhoto = view.findViewById(R.id.home_first_image_car);

                        } else if (count == 2) {
                            nameSecondCar = documentSnapshot.getString("name");

                            nameCar = view.findViewById(R.id.home_second_name_car);
                            price = view.findViewById(R.id.home_second_price_car);
                            mainPhoto = view.findViewById(R.id.home_second_image_car);

                        } else if (count == 3) {
                            nameThirdCar = documentSnapshot.getString("name");

                            nameCar = view.findViewById(R.id.home_third_name_car);
                            price = view.findViewById(R.id.home_third_price_car);
                            mainPhoto = view.findViewById(R.id.home_third_image_car);

                        }

                        nameCar.setText(documentSnapshot.getString("name"));
                        price.setText("R$ " + documentSnapshot.getDouble("price"));
                        Glide.with(this)
                                .load(documentSnapshot.getString("mainPhoto"))
                                .into(mainPhoto);

                    }
                });

    }
}