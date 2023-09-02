package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import de.cketti.mailto.EmailIntentBuilder;

import com.amozzafiato.R;

public class ProfileContact extends AppCompatActivity {

    private Button sendEmail;
    private EditText message;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_contact);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_contact_come_back_profile);

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });

        sendEmail = findViewById(R.id.profile_contact_button);
        message = findViewById(R.id.profile_contact_message);

        sendEmail.setOnClickListener(v -> {
            String emailContent = message.getText().toString();

            // Crie uma Intent para enviar o email
            Intent emailIntent = EmailIntentBuilder.from(ProfileContact.this)
                    .to("ana_elert@estudante.sesisenai.org.br")
                    .subject("Entrar em contato")
                    .body(emailContent)
                    .build();

            // Inicie a tela de composição de email
            startActivity(emailIntent);
            });
    }

}