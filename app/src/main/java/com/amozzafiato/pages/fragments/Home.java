package com.amozzafiato.pages.fragments;

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

public class Home extends Fragment {

    private Space emptySpace;
    private ScrollView scrollView;
    private LinearLayout.LayoutParams layoutParams;
    private int originalSpaceHeight;
    private int previousScrollY;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializa as Views
        emptySpace = view.findViewById(R.id.emptySpace);
        scrollView = view.findViewById(R.id.scrollView);
        layoutParams = (LinearLayout.LayoutParams) emptySpace.getLayoutParams();

        // Obtém a altura original do espaço vazio (Space)
        originalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.space_height);

        // Inicializa a posição de rolagem anterior
        previousScrollY = 0;

        // Escuta as alterações de rolagem do ScrollView
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // Obtém o scrollY atual do ScrollView
                int scrollY = scrollView.getScrollY();

                // Verifica se o usuário está rolando para cima
                boolean scrollingUp = scrollY < previousScrollY;

                // Se o usuário estiver rolando para cima e o espaço não estiver no topo da tela, ajuste a altura do espaço vazio (Space)
                if (scrollingUp && emptySpace.getTop() > 0) {
                    // Calcula a altura desejada para o espaço vazio
                    int desiredSpaceHeight = layoutParams.height - (previousScrollY - scrollY);
                    // Certifica-se de que a altura do espaço não seja menor que 0 e não seja maior que a altura original
                    int clampedSpaceHeight = Math.max(0, Math.min(desiredSpaceHeight, originalSpaceHeight));
                    // Atualiza a altura do espaço vazio (Space)
                    layoutParams.height = clampedSpaceHeight;
                    emptySpace.setLayoutParams(layoutParams);
                } else {
                    // Se o usuário estiver rolando para baixo e o espaço não estiver com a altura original, aumente a altura do espaço vazio (Space)
                    if (emptySpace.getLayoutParams().height < originalSpaceHeight) {
                        // Calcula a altura desejada para o espaço vazio
                        int desiredSpaceHeight = layoutParams.height + (scrollY - previousScrollY);
                        // Certifica-se de que a altura do espaço não seja menor que 0 e não seja maior que a altura original
                        int clampedSpaceHeight = Math.max(0, Math.min(desiredSpaceHeight, originalSpaceHeight));
                        // Atualiza a altura do espaço vazio (Space)
                        layoutParams.height = clampedSpaceHeight;
                        emptySpace.setLayoutParams(layoutParams);
                    }
                }

                // Atualiza a posição de rolagem anterior
                previousScrollY = scrollY;
            }
        });

        return view;
    }
}