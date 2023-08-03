package com.amozzafiato.pages.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amozzafiato.R;

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

        // Escuta as alterações de rolagem do ScrollView
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Obtém o scrollY atual do ScrollView
                int scrollY = scrollView.getScrollY();

                // Calcula a altura desejada para o espaço vazio (Space)
                int desiredSpaceHeight = originalSpaceHeight - scrollY;

                // Certifique-se de que o espaço vazio não seja menor que a altura do botão + um espaço mínimo
                int minSpaceHeight = buttonHeight + getResources().getDimensionPixelSize(R.dimen.space_min_height);
                int clampedSpaceHeight = Math.max(minSpaceHeight, desiredSpaceHeight);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) emptySpace.getLayoutParams();
                layoutParams.height = clampedSpaceHeight;
                emptySpace.setLayoutParams(layoutParams);
            }
        });

        return view;
    }
}