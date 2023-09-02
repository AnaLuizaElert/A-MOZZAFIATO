package com.amozzafiato.pages.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amozzafiato.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileData extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword, inputCountry, inputState;
    private ImageView imageProfile;
    private CardView cardName, cardEmail, cardPassword, cardCountry, cardState, cardImage;
    private Button editButton;
    private boolean isEditing = false;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

        imageProfile = findViewById(R.id.profile_data_image);
        inputName = findViewById(R.id.profile_data_input_name);
        inputEmail = findViewById(R.id.profile_data_input_email);
        inputCountry = findViewById(R.id.profile_data_input_country);
        inputState = findViewById(R.id.profile_data_input_state);
        inputPassword = findViewById(R.id.profile_data_input_password);

        cardName = findViewById(R.id.profile_data_card_name);
        cardEmail = findViewById(R.id.profile_data_card_email);
        cardPassword = findViewById(R.id.profile_data_card_password);
        cardCountry = findViewById(R.id.profile_data_card_country);
        cardState = findViewById(R.id.profile_data_card_state);

        editButton = findViewById(R.id.profile_data_button_edit);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_data_come_back_profile);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView editInfo= findViewById(R.id.profile_data_edit);

        linkProfile.setOnClickListener(v -> {
                onBackPressed();
        });

        /*Colocar dados do usuário*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("TbUser").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        inputName.setText(documentSnapshot.getString("name"));
                        inputEmail.setText(documentSnapshot.getString("email"));
                        inputCountry.setText(documentSnapshot.getString("country"));
                        inputPassword.setText(documentSnapshot.getString("password"));
                        inputState.setText(documentSnapshot.getString("state"));
                        Glide.with(this)
                                .load(documentSnapshot.getString("image"))
                                .into(imageProfile);
                    }
                });

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/");



        // Set OnClickListener for the edit image
        editInfo.setOnClickListener(v ->  {
                if (!isEditing) {
                    // Enable inputs for editing
                    inputName.setEnabled(true);
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                    inputCountry.setEnabled(true);
                    inputState.setEnabled(true);

                    // Show the edit button
                    editButton.setVisibility(View.VISIBLE);
                } else {
                    // Disable inputs after editing is done
                    inputName.setEnabled(false);
                    inputName.setTextColor(getResources().getColor(android.R.color.black));
                    inputName.setBackgroundColor(Color.parseColor("#B6B4B3"));
                    cardName.setCardBackgroundColor(Color.parseColor("#B6B4B3"));

                    inputEmail.setEnabled(false);
                    inputEmail.setTextColor(getResources().getColor(android.R.color.black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardEmail.setCardBackgroundColor(getColor(R.color.grayInput));
                    }

                    inputPassword.setEnabled(false);
                    inputPassword.setTextColor(getResources().getColor(android.R.color.black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardPassword.setCardBackgroundColor(getColor(R.color.grayInput));
                    }

                    inputCountry.setEnabled(false);
                    inputCountry.setTextColor(getResources().getColor(android.R.color.black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardCountry.setCardBackgroundColor(getColor(R.color.grayInput));
                    }

                    inputState.setEnabled(false);
                    inputState.setTextColor(getResources().getColor(android.R.color.black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardState.setCardBackgroundColor(getColor(R.color.grayInput));
                    }

                    // Hide the edit button
                    editButton.setVisibility(View.GONE);
                }

                // Toggle the editing state
                isEditing = !isEditing;
            });
    }
}