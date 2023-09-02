package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amozzafiato.R;
import com.amozzafiato.pages.fragments.Profile;

import de.cketti.mailto.EmailIntentBuilder;


public class ProfileNegotiate2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Button buttonChooseImage, buttonSend, image;
    private EditText price, obs;
    private RadioGroup exchange, original;
    private static String exchangeText;
    private static String originalText;
    private static String message;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_negotiate2);

        price = findViewById(R.id.profile_negotiate2_input_price);
        exchange = findViewById(R.id.profile_negotiate2_container_radio_exchange);
        original = findViewById(R.id.profile_negotiate2_container_radio_original);
        obs = findViewById(R.id.profile_negotiate2_input_obs);

        buttonChooseImage = findViewById(R.id.profile_negotiate2_input_images);
        buttonSend = findViewById(R.id.profile_negotiate2_button_send);
        ImageView comeBack = findViewById(R.id.profile_negotiate2_come_back_negotiate);

        // Defina um ouvinte de seleção para o RadioGroup
        exchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.profile_negotiate2_radio_automatic) {
                    exchangeText = "automático";
                } else if (checkedId == R.id.profile_negotiate2_radio_manual) {
                    originalText = "manual";
                }
            }
        });

        original.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.profile_negotiate2_radio_yes) {
                    exchangeText = "original";
                } else if (checkedId == R.id.profile_negotiate2_radio_not) {
                    originalText = "modificado";
                }
            }
        });

        buttonSend.setOnClickListener(v -> {
            Intent thisIntent = getIntent();
            if (thisIntent != null) {
                System.out.println(message);
                message = thisIntent.getStringExtra("message");
                if (message != null) {
                    message += "\nPreço: " + price.getText().toString() +
                            "\nCâmbio: " + exchangeText +
                            "\nOriginalidade: " + originalText +
                            "\nObservação: " + obs.getText().toString();
                    
                }

            }

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
        });

        comeBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileNegotiate2.this, ProfileNegotiate.class);
            startActivity(intent);
            finish();
        });

        buttonChooseImage.setOnClickListener(v -> {
            openGallery();
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Escolher Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            imageUri = data.getData();
        }
    }
}