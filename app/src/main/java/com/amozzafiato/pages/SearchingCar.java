package com.amozzafiato.pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amozzafiato.AdapterSearching;
import com.amozzafiato.CarViewModel;
import com.amozzafiato.R;

import java.util.ArrayList;

public class SearchingCar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSearching adapter;
    private CarViewModel carViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_car);

        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            if (category != null) {
                TextView categoryTextView = findViewById(R.id.searching_car_line_title);
                if (category.equals("TODOS")) {
                    categoryTextView.setText("Nossa Coleção ");
                } else {
                    categoryTextView.setText("Coleção " + category);
                }

                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                adapter = new AdapterSearching(new ArrayList<>());
                recyclerView.setAdapter(adapter);

                carViewModel = new ViewModelProvider(this).get(CarViewModel.class);

                carViewModel.getCars().observe(this, cars -> {
                    adapter.updateItems(cars);
                });

                carViewModel.loadCars(category);

            }
        }


    }

}