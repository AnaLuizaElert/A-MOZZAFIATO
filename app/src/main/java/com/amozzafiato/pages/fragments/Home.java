package com.amozzafiato.pages.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;
import com.amozzafiato.pages.SearchingCar;

public class Home extends Fragment {

    private Space emptySpace;
    private ScrollView scrollView;
    private int originalSpaceHeight;
    private Button seeCollectionButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializa as Views
        emptySpace = view.findViewById(R.id.emptySpace);
        scrollView = view.findViewById(R.id.scrollView);
        seeCollectionButton = view.findViewById(R.id.fragment_home_button_see_collection);

        // Obtém a altura original do espaço vazio (Space) e a altura do botão
        originalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.space_height);
        int buttonHeight = seeCollectionButton.getHeight();
        // Rolando para o topo da página
        scrollView.post(() -> scrollView.scrollTo(0, 0));


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button colection = view.findViewById(R.id.fragment_home_button_see_collection);

        colection.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "TODOS");
            startActivity(intent);
        });

        // Escuta as alterações de rolagem do ScrollView
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Obtém o scrollY atual do ScrollView
                int scrollY = scrollView.getScrollY();

                // Ajuste o fator de ajuste para controlar a rapidez da diminuição do espaço
                float sizeAdjustmentFactor = 0.8f; // Experimente diferentes valores

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

        return view;
    }
}