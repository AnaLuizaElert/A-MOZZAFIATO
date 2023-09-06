package com.amozzafiato.pages.profile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amozzafiato.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileData extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private EditText inputName, inputEmail, inputPassword, inputCountry, inputState;
    private ImageView imageProfile, linkProfile, editInfo;
    private CardView cardName, cardEmail, cardPassword, cardCountry, cardState;
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

        linkProfile = findViewById(R.id.profile_data_come_back_profile);
        editInfo = findViewById(R.id.profile_data_edit);

        generateDataDb();

        linkProfile.setOnClickListener(v -> {
            onBackPressed();
        });

        editInfo.setOnClickListener(v -> {
            if (!isEditing) {
                changeEditStateTrue();
            } else {
                changeEditStateFalse();
            }
            isEditing = !isEditing;
        });

        editButton.setOnClickListener(v -> {
            updateDataUser();
        });
    }

    private void updateDataUser() {
        DocumentReference userRef = db.collection("TbUser").document(userId);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser thisUser = mAuth.getCurrentUser();

        // Obtenha os valores dos campos de texto
        String newName = inputName.getText().toString();
        String newEmail = inputEmail.getText().toString();
        String newCountry = inputCountry.getText().toString();
        String newPassword = inputPassword.getText().toString();
        String newState = inputState.getText().toString();

        // Crie um mapa com os novos dados que você deseja atualizar
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", newName);
        userData.put("email", newEmail);
        userData.put("country", newCountry);
        userData.put("password", newPassword);
        userData.put("state", newState);


        userRef.update(userData)
                .addOnSuccessListener(aVoid -> {
                    assert thisUser != null;
                    thisUser.updateEmail(newEmail)

                            .addOnSuccessListener(aVoid1 ->
                                    thisUser.updatePassword(newPassword)

                                            .addOnSuccessListener(aVoid11 -> {
                                                // Ambas as atualizações foram bem-sucedidas
                                                Toast.makeText(getApplicationContext(), "Pode demorar um pouquinho para atualizar :)", Toast.LENGTH_SHORT).show();
                                                isEditing = false;
                                                changeEditStateFalse();
                                            })

                                            .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Erro ao atualizar a senha", Toast.LENGTH_SHORT).show()))
                            .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Erro ao atualizar o email", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Erro ao editar!!", Toast.LENGTH_SHORT).show());
    }

    private void changeEditStateFalse() {
        inputName.setEnabled(false);
        inputName.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardName.setCardBackgroundColor(getColor(R.color.grayMozzafiato));
        }

        inputEmail.setEnabled(false);
        inputEmail.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardEmail.setCardBackgroundColor(getColor(R.color.grayMozzafiato));
        }

        inputPassword.setEnabled(false);
        inputPassword.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardPassword.setCardBackgroundColor(getColor(R.color.grayMozzafiato));
        }

        inputCountry.setEnabled(false);
        inputCountry.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardCountry.setCardBackgroundColor(getColor(R.color.grayMozzafiato));
        }

        inputState.setEnabled(false);
        inputState.setTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardState.setCardBackgroundColor(getColor(R.color.grayMozzafiato));
        }

        editButton.setVisibility(View.GONE);
    }

    private void changeEditStateTrue() {
        inputName.setEnabled(true);
        inputName.setTextColor(getResources().getColor(android.R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardName.setCardBackgroundColor(getColor(R.color.grayInput));
        }

        inputEmail.setEnabled(true);
        inputEmail.setTextColor(getResources().getColor(android.R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardEmail.setCardBackgroundColor(getColor(R.color.grayInput));
        }

        inputPassword.setEnabled(true);
        inputPassword.setTextColor(getResources().getColor(android.R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardPassword.setCardBackgroundColor(getColor(R.color.grayInput));
        }

        inputCountry.setEnabled(true);
        inputCountry.setTextColor(getResources().getColor(android.R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardCountry.setCardBackgroundColor(getColor(R.color.grayInput));
        }

        inputState.setEnabled(true);
        inputState.setTextColor(getResources().getColor(android.R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardState.setCardBackgroundColor(getColor(R.color.grayInput));
        }

        editButton.setVisibility(View.VISIBLE);
    }

    private void generateDataDb() {
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
    }
}