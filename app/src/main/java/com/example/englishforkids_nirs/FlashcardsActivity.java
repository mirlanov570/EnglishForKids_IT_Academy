package com.example.englishforkids_nirs;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FlashcardsActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        ImageView alphabetButton = findViewById(R.id.btnAlphabet);
        ImageView colorsButton = findViewById(R.id.btnColors);
        ImageView numbersButton = findViewById(R.id.btnNumbers);
        ImageView animalsButton = findViewById(R.id.btnAnimals);
        ImageView familyButton = findViewById(R.id.btnFamily);
        ImageView clothingButton = findViewById(R.id.btnClothing);

        alphabetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, AlphabetActivity.class);
                startActivity(intent);
            }
        });

        colorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, ColorsActivity.class);
                startActivity(intent);
            }
        });

        numbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });

        animalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, AnimalsActivity.class);
                startActivity(intent);
            }
        });

        familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, FamilyActivity.class);
                startActivity(intent);
            }
        });



        clothingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardsActivity.this, ClothingActivity.class);
                startActivity(intent);
            }
        });
    }
}
