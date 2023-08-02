package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.amozzafiato.R;
import com.amozzafiato.pages.fragments.Profile;


public class ProfileNegotiate2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_negotiate2);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonChooseImage = findViewById(R.id.profile_negotiate2_input_images);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonSend = findViewById(R.id.profile_negotiate2_button_send);
        ImageView comeBack = findViewById(R.id.profile_negotiate2_come_back_negotiate);

        buttonSend.setOnClickListener(v -> {
            System.out.println("Negotiete 2222");
            Intent intent = new Intent(ProfileNegotiate2.this, Profile.class);
            startActivity(intent);
        });

        comeBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileNegotiate2.this, ProfileNegotiate.class);
            startActivity(intent);
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