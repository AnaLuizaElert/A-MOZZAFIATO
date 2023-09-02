package com.amozzafiato.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private TextInputEditText name, country, state, image, email, password, confpassword;
    private TextView linkToLogin;
    private Button buttonChooseImage, register;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static Uri selectedImageUri;
    private static String idImage;

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

        /*Colocar underline no texto*/
        String linkTextLogin = getResources().getString(R.string.link_forgot_password);
        SpannableString spannableStringPassword = new SpannableString(linkTextLogin);
        spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextLogin.length(), 0);
        linkToLogin.setText(spannableStringPassword);

        buttonChooseImage.setOnClickListener(v -> {
            openGallery();
        });

        register.setOnClickListener(v -> {
            boolean isValid = true;

            if (name.getText().toString().isEmpty()) {
                name.setError("Campo obrigatório");
                isValid = false;
            }

            if (state.getText().toString().isEmpty()) {
                state.setError("Campo obrigatório");
                isValid = false;
            }

            if (country.getText().toString().isEmpty()) {
                country.setError("Campo obrigatório");
                isValid = false;
            }

            if (email.getText().toString().isEmpty()) {
                email.setError("Campo obrigatório");
                isValid = false;
            }

            if (password.getText().toString().isEmpty()) {
                password.setError("Campo obrigatório");
                isValid = false;
            }

            if (confpassword.getText().toString().isEmpty()) {
                confpassword.setError("Campo obrigatório");
                isValid = false;
            }

            if(!confpassword.getText().toString().equals(password.getText().toString())){
                password.setError("Senhas diferentes!");
                confpassword.setError("Senhas diferentes!");
                isValid = false;
            }

            if (isValid) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                String userId = user.getUid(); // Obtém o ID único do usuário

                                saveUserDataToFirestore(
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

    private void saveUserDataToFirestore(
            String userId, String name, String country, String state, String email, String password
            ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                idImage = uri.toString();

                                // Agora que você tem a URL da imagem, atualize o userMap e salve os dados no Firestore
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("name", name);
                                userMap.put("country", country);
                                userMap.put("state", state);
                                userMap.put("image", idImage);
                                userMap.put("email", email);
                                userMap.put("password", password);

                                // Adicione os dados ao Firestore
                                db.collection("TbUser").document(userId) // Use o ID do usuário como ID do documento
                                        .set(userMap)
                                        .addOnSuccessListener(aVoid -> {
                                            // Dados salvos com sucesso
                                            Intent intent = new Intent(Register.this, NavigationPage.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Ocorreu um erro ao salvar os dados no Firestore
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocorreu um erro ao fazer o upload da imagem
                    }
                });


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