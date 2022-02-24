package com.example.memorama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperar extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetButton;

    private String email = "";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar);

        emailEditText = findViewById(R.id.e_mail);
        resetButton = findViewById(R.id.recuperar);
        mDialog = new ProgressDialog(Recuperar.this);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEditText.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espera un momento mientras enviamos el correo...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                } else {
                    Toast.makeText(Recuperar.this, "Ingrese su dirección de correo electrónico para continuar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Recuperar.this, "Se ha enviado un correo para reestablecer tu contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Recuperar.this, "No se pudo enviar el correo elctrónico.", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();

                Intent intent = new Intent(Recuperar.this, Login.class);
                startActivity(intent);
            }
        });
    }
}