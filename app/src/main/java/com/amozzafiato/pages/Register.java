package com.amozzafiato.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import com.amozzafiato.NavigationPage;
import com.amozzafiato.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private TextInputEditText name, country, state, image, email, password, confpassword;

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
        image = findViewById(R.id.register_image_edit);
        email = findViewById(R.id.register_email_edit);
        password = findViewById(R.id.register_password_edit);
        confpassword = findViewById(R.id.register_confpassword_edit);


        /*Underline no texto*/
        TextView linkToLogin = findViewById(R.id.register_link_login);
        String linkTextLogin = getResources().getString(R.string.link_forgot_password);
        SpannableString spannableStringPassword = new SpannableString(linkTextLogin);
        spannableStringPassword.setSpan(new UnderlineSpan(), 0, linkTextLogin.length(), 0);
        linkToLogin.setText(spannableStringPassword);

        Button register = findViewById(R.id.register_button);

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

            if (image.getText().toString().isEmpty()) {
                image.setError("Campo obrigatório");
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
                                        image.getText().toString(),
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
            String userId, String name, String country, String state, String image, String email, String password
            ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crie um mapa com os dados do usuário
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("country", country);
        userMap.put("state", state);
        userMap.put("image", image);
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
                .addOnFailureListener(e -> {
                    // Trate o erro, se necessário
                });
    }
}