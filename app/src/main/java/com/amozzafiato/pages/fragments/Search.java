package com.amozzafiato.pages.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.amozzafiato.R;
import com.amozzafiato.pages.SearchingCar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Configurar o clique do botão no CardView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView european = view.findViewById(R.id.search_european_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView american = view.findViewById(R.id.search_american_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView convertible = view.findViewById(R.id.search_convertible_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView coupe = view.findViewById(R.id.search_coupe_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView hot = view.findViewById(R.id.search_hot_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView pickup = view.findViewById(R.id.search_pickup_category);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView national = view.findViewById(R.id.search_national_category);


        european.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            // Passando o parâmetro para a próxima página
            intent.putExtra("category", "Europeu");
            startActivity(intent);
        });

        american.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Americano");
            startActivity(intent);
        });

        convertible.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Conversível");
            startActivity(intent);
        });

        coupe.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Coupé");
            startActivity(intent);
        });

        hot.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Hot Rod");
            startActivity(intent);
        });

        pickup.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Picape");
            startActivity(intent);
        });

        national.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchingCar.class);
            intent.putExtra("category", "Nacional");
            startActivity(intent);
        });

        return view;
    }
}