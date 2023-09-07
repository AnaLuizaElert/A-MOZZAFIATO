package com.amozzafiato.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static Uri selectedImageUri;
    private static String idImage;
    private TextInputEditText name, country, state, email, password, confpassword;
    private TextView linkToLogin;
    private Button buttonChooseImage, register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);

        /*Iniciando variáveis*/
        name = findViewById(R.id.register_name_edit);
        country = findViewById(R.id.register_country_edit);
        state = findViewById(R.id.register_state_edit);
        email = findViewById(R.id.register_email_edit);
        password = findViewById(R.id.register_password_edit);
        confpassword = findViewById(R.id.register_confpassword_edit);
        register = findViewById(R.id.register_button);
        buttonChooseImage = findViewById(R.id.register_image_edit);
        linkToLogin = findViewById(R.id.register_link_login);

        putUnderlineOnLink();

        buttonChooseImage.setOnClickListener(v -> {
            openGallery();
        });

        register.setOnClickListener(v -> {
            if (validation()) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                String userId = user.getUid();

                                generateDataDb(
                                        userId, // Passe o ID do usuário para usar como ID do documento
                                        name.getText().toString(),
                                        country.getText().toString(),
                                        state.getText().toString(),
                                        email.getText().toString(),
                                        password.getText().toString()
                                );
                            } else {
                                Exception e = task.getException();
                                if (e instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) e).getErrorCode();
                                }
                            }
                        });
            }
        });
    }

    private Boolean validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Campo obrigatório");
            return false;
        }

        if (state.getText().toString().isEmpty()) {
            state.setError("Campo obrigatório");
            return false;
        }

        if (country.getText().toString().isEmpty()) {
            country.setError("Campo obrigatório");
            return false;
        }

        if (email.getText().toString().isEmpty()) {
            email.setError("Campo obrigatório");
            return false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Campo obrigatório");
            return false;
        }

        if (confpassword.getText().toString().isEmpty()) {
            confpassword.setError("Campo obrigatório");
            return false;
        }

        if (!confpassword.getText().toString().equals(password.getText().toString())) {
            password.setError("Senhas diferentes!");
            confpassword.setError("Senhas diferentes!");
            return false;
        }

        return true;
    }

    private void putUnderlineOnLink() {
        String linkTextLogin = getResources().getString(R.string.link_login);
        SpannableString spannableStringPassword = new SpannableString(linkTextLogin);
        spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextLogin.length(), 0);
        linkToLogin.setText(spannableStringPassword);
    }

    private void generateDataDb(
            String userId, String name, String country, String state, String email, String password
    ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            idImage = uri.toString();

                            // Agora que você tem a URL da imagem, atualize o userMap e salve os dados no Firestore
                            Map<String, Object> userMap1 = new HashMap<>();
                            userMap1.put("name", name);
                            userMap1.put("country", country);
                            userMap1.put("state", state);
                            userMap1.put("image", idImage);
                            userMap1.put("email", email);
                            userMap1.put("password", password);

                            // Adicione os dados ao Firestore
                            db.collection("TbUser").document(userId) // Use o ID do usuário como ID do documento
                                    .set(userMap1)
                                    .addOnSuccessListener(aVoid -> {
                                        // Dados salvos com sucesso
                                        Intent intent = new Intent(Register.this, NavigationPage.class);
                                        startActivity(intent);
                                        finish();
                                    });
                        }));

    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        buttonChooseImage = findViewById(R.id.register_image_edit);
        buttonChooseImage.setText("Imagem escolhida");
        startActivityForResult(Intent.createChooser(galleryIntent, "Escolher Imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            selectedImageUri = data.getData();
        }


    }

}