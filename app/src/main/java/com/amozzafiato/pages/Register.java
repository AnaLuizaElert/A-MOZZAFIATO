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

    private TextInputEditText name, phone, email, password, confpassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);

        /*Iniciando variáveis*/
        name = findViewById(R.id.register_name_edit);
        phone = findViewById(R.id.register_phone_edit);
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

            if (phone.getText().toString().isEmpty()) {
                phone.setError("Campo obrigatório");
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
                                saveUserDataToFirestore(name.getText().toString(), phone.getText().toString(), email.getText().toString(), password.getText().toString());

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
            String name, String phone,String email, String password
            ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crie um mapa com os dados do usuário
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("phone", phone);
        userMap.put("email", email);
        userMap.put("password", password);

        // Adicione os dados ao Firestore
        db.collection("users").document(email)
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(Register.this, NavigationPage.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {

                });
    }
}