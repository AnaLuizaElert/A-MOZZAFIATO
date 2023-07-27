package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.amozzafiato.R;

public class ProfileData extends AppCompatActivity {

    private EditText inputName, inputPhone, inputEmail, inputPassword;
    private CardView cardName, cardPhone, cardEmail, cardPassword;
    private Button editButton;
    private boolean isEditing = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);

        inputName = findViewById(R.id.profile_data_input_name);
        inputPhone = findViewById(R.id.profile_data_input_phone);
        inputEmail = findViewById(R.id.profile_data_input_email);
        inputPassword = findViewById(R.id.profile_data_input_password);

        cardName = findViewById(R.id.profile_data_card_name);
        cardPhone = findViewById(R.id.profile_data_card_phone);
        cardEmail = findViewById(R.id.profile_data_card_email);
        cardPassword = findViewById(R.id.profile_data_card_password);

        editButton = findViewById(R.id.profile_data_button_edit);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView linkProfile = findViewById(R.id.profile_data_come_back_profile);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView editInfo= findViewById(R.id.profile_data_edit);

        linkProfile.setOnClickListener(v -> {
                onBackPressed();
        });

        // Set OnClickListener for the edit image
        editInfo.setOnClickListener(v ->  {
                if (!isEditing) {
                    // Enable inputs for editing
                    inputName.setEnabled(true);
                    inputPhone.setEnabled(true);
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);

                    // Show the edit button
                    editButton.setVisibility(View.VISIBLE);
                } else {
                    // Disable inputs after editing is done
                    inputName.setEnabled(false);
                    inputName.setTextColor(getResources().getColor(android.R.color.black));
                    inputName.setBackgroundColor(Color.parseColor("#B6B4B3"));
                    cardName.setCardBackgroundColor(Color.parseColor("#B6B4B3"));

                    inputPhone.setEnabled(false);
                    inputPhone.setTextColor(getResources().getColor(android.R.color.black));
                    cardPhone.setCardBackgroundColor(getColor(R.color.grayInput));

                    inputEmail.setEnabled(false);
                    inputEmail.setTextColor(getResources().getColor(android.R.color.black));
                    cardEmail.setCardBackgroundColor(getColor(R.color.grayInput));

                    inputPassword.setEnabled(false);
                    inputPassword.setTextColor(getResources().getColor(android.R.color.black));
                    cardPassword.setCardBackgroundColor(getColor(R.color.grayInput));

                    // Hide the edit button
                    editButton.setVisibility(View.GONE);
                }

                // Toggle the editing state
                isEditing = !isEditing;
            });
    }
}