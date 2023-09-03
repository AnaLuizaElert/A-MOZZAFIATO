package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amozzafiato.R;
import com.amozzafiato.pages.fragments.Profile;

import de.cketti.mailto.EmailIntentBuilder;


public class ProfileNegotiate2 extends AppCompatActivity {

    private Button buttonSend;
    private EditText price, obs;
    private RadioGroup exchange, original;
    private static String message, originalText, exchangeText;


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
        ImageView comeBack = findViewById(R.id.profile_negotiate2_come_back_negotiate);

        exchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.profile_negotiate2_radio_automatic) {
                    exchangeText = "automático";
                } else if (checkedId == R.id.profile_negotiate2_radio_manual) {
                    exchangeText = "manual";
                }
            }
        });

        original.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.profile_negotiate2_radio_yes) {
                    originalText = "original";
                } else if (checkedId == R.id.profile_negotiate2_radio_not) {
                    originalText = "modificado";
                }
            }
        });

        buttonSend.setOnClickListener(v -> {
            Intent thisIntent = getIntent();
            if (thisIntent != null) {
                message = thisIntent.getStringExtra("message");
                if (message != null) {
                    boolean isValid = true;

                    if (price.getText().toString().isEmpty()) {
                        price.setError("Campo obrigatório");
                        isValid = false;
                    }

                    if (originalText == null || exchangeText == null) {
                        Toast.makeText(getApplicationContext(), "Todos campos são obrigatórios", Toast.LENGTH_SHORT).show();
                        isValid = false;
                    }


                    if (isValid) {
                        message += "\nPreço: " + price.getText().toString() +
                                "\nCâmbio: " + exchangeText +
                                "\nOriginalidade: " + originalText +
                                "\nObservação: " + obs.getText().toString();

                        Intent emailIntent =  EmailIntentBuilder.from(ProfileNegotiate2.this)
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
            Intent intent = new Intent(ProfileNegotiate2.this, ProfileNegotiate.class);
            startActivity(intent);
            finish();
        });
    }

}