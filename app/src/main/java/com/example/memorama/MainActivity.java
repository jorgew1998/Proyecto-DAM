package com.example.memorama;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {


    Button play, salir, scores;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.botonMainJugar);
        salir = findViewById(R.id.botonMainSalir);
        scores = findViewById(R.id.btnScores);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("iniciando juego...");
                iniciarJuego();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pasando a pantalla de puntajes");
                goToScoresView();
            }
        });
    }

    private void iniciarJuego(){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }

    private void goToScoresView() {
        Intent i = new Intent(this, ScoresView.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
//            Intent intent = new Intent(ScoresView.this, Login.class);
//            startActivity(intent);
            finish();
        }
    }
}