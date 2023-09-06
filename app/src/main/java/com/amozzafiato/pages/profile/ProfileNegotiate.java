package com.amozzafiato.pages.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;

public class ProfileNegotiate extends AppCompatActivity {

    private EditText name, model, year, km, fuel, color;
    private Button linkPage2;
    private ImageView linkProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_negotiate);

        name = findViewById(R.id.profile_negotiate_input_name);
        model = findViewById(R.id.profile_negotiate_input_model);
        year = findViewById(R.id.profile_negotiate_input_year);
        km = findViewById(R.id.profile_negotiate_input_km);
        fuel = findViewById(R.id.profile_negotiate_input_fuel);
        color = findViewById(R.id.profile_negotiate_input_color);

        linkPage2 = findViewById(R.id.profile_negotiate_button_next);
        linkProfile = findViewById(R.id.profile_negotiate_come_back_profile);

        linkPage2.setOnClickListener(v -> {
            if (validation()) {
                Intent intent = new Intent(ProfileNegotiate.this, ProfileNegotiate2.class);
                String message =
                        "\n**DADOS DO CARRO**" +
                                "\nNome: " + name.getText().toString() +
                                "\nModelo: " + model.getText().toString() +
                                "\nAno: " + year.getText().toString() +
                                "\nKm rodados: " + km.getText().toString() +
                                "\nCombustível: " + fuel.getText().toString() +
                                "\nCor: " + color.getText().toString();
                intent.putExtra("message", message);
                startActivity(intent);
                finish();
            }
        });

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private Boolean validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Campo obrigatório");
            return false;
        }

        if (model.getText().toString().isEmpty()) {
            model.setError("Campo obrigatório");
            return false;
        }

        if (year.getText().toString().isEmpty()) {
            year.setError("Campo obrigatório");
            return false;
        }

        if (km.getText().toString().isEmpty()) {
            km.setError("Campo obrigatório");
            return false;
        }

        if (fuel.getText().toString().isEmpty()) {
            fuel.setError("Campo obrigatório");
            return false;
        }

        if (color.getText().toString().isEmpty()) {
            color.setError("Campo obrigatório");
            return false;
        }

        return true;
    }
}