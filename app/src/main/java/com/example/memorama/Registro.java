package com.example.memorama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText nicknameEditText, emailEditText, passwordEditText;
    Button registerButton;

    //variables a registrar
    private String nickname = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        nicknameEditText = findViewById(R.id.nickname);
        emailEditText = findViewById(R.id.mail);
        passwordEditText = findViewById(R.id.password);

        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = nicknameEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if(!nickname.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if (password.length() >= 6){
                        registerUser();
                    } else {
                        Toast.makeText(Registro.this, "La constraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Registro.this, "No se completaron uno o más campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nickname", nickname);
                    map.put("email", email);

                    mFirestore.collection("Usuarios").document(mAuth.getCurrentUser().getUid()).set(map);

                    Intent main = new Intent(Registro.this, MainActivity.class);
                    startActivity(main);
                    finish();

                } else {
                    Toast.makeText(Registro.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}