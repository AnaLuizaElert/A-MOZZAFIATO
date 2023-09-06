package com.amozzafiato.pages.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.R;
import com.amozzafiato.pages.fragments.Profile;

import de.cketti.mailto.EmailIntentBuilder;


public class ProfileNegotiate2 extends AppCompatActivity {

    private static String message, originalText, exchangeText;
    private Button buttonSend;
    private EditText price, obs;
    private RadioGroup exchange, original;
    private ImageView comeBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_negotiate2);

        price = findViewById(R.id.profile_negotiate2_input_price);
        exchange = findViewById(R.id.profile_negotiate2_container_radio_exchange);
        original = findViewById(R.id.profile_negotiate2_container_radio_original);
        obs = findViewById(R.id.profile_negotiate2_input_obs);

        buttonSend = findViewById(R.id.profile_negotiate2_button_send);
        comeBack = findViewById(R.id.profile_negotiate2_come_back_negotiate);

        exchange.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.profile_negotiate2_radio_automatic) {
                exchangeText = "automático";
            } else if (checkedId == R.id.profile_negotiate2_radio_manual) {
                exchangeText = "manual";
            }
        });

        original.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.profile_negotiate2_radio_yes) {
                originalText = "original";
            } else if (checkedId == R.id.profile_negotiate2_radio_not) {
                originalText = "modificado";
            }
        });

        buttonSend.setOnClickListener(v -> {
            Intent thisIntent = getIntent();
            if (thisIntent != null) {
                message = thisIntent.getStringExtra("message");
                if (message != null) {
                    if (validation()) {
                        message += "\nPreço: " + price.getText().toString() +
                                "\nCâmbio: " + exchangeText +
                                "\nOriginalidade: " + originalText +
                                "\nObservação: " + obs.getText().toString() +
                                "\n****Você pode anexar imagens do carro no email****";

                        Intent emailIntent = EmailIntentBuilder.from(ProfileNegotiate2.this)
                                .to("ana_elert@estudante.sesisenai.org.br")
                                .subject("Negociar automóvel")
                                .body(message)
                                .build();

                        // Inicie a tela de composição de email
                        startActivity(emailIntent);

                        Intent intent = new Intent(ProfileNegotiate2.this, Profile.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }

        });

        comeBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private Boolean validation() {
        if (price.getText().toString().isEmpty()) {
            price.setError("Campo obrigatório");
            return false;
        }

        if (originalText == null || exchangeText == null) {
            Toast.makeText(getApplicationContext(), "Todos campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}