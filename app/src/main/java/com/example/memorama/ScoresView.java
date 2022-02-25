package com.example.memorama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoresView extends AppCompatActivity {

    Button back;
    TableLayout table;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final String TAG = "MYDOC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores_view);

        back = findViewById(R.id.btnBack);
        table = findViewById(R.id.scoresTable);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Regresando a Inicio...");
                goToStart();

            }
        });

        db.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(40)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());

                                TableRow tr = new TableRow(ScoresView.this);
                                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                                // textwiew username
                                TextView txtUsername = new TextView(ScoresView.this);
                                txtUsername.setText(document.getData().get("user").toString());
                                txtUsername.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT));
                                txtUsername.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                txtUsername.setPadding(25,0,0,0);
                                tr.addView(txtUsername);
                                // textview scores
                                TextView txtScores = new TextView(ScoresView.this);
                                txtScores.setText(document.getData().get("score").toString());
                                txtScores.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                                txtScores.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                txtScores.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                                tr.addView(txtScores);
                                table.addView(tr);
                            }
//                            System.out.println("size1 "+scoresMap.size());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }



                    }

                });



    }

    private void goToStart() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private HashMap<String, Integer> getAllScores() {
        HashMap<String, Integer> scoresMap = new HashMap<String, Integer>();

        db.collection("scores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());

                                scoresMap.put(document.getData().get("user").toString(),
                                        Integer.parseInt(document.getData().get("score").toString()));

                            }
                            System.out.println("size1 "+scoresMap.size());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        System.out.println("size 2 "+scoresMap.size());

                    }

                });
        System.out.println("size 3 "+scoresMap.size());
        for (String i : scoresMap.keySet()) {
            System.out.println("key: " + i + " value: " + scoresMap.get(i));
        }

        return scoresMap;
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