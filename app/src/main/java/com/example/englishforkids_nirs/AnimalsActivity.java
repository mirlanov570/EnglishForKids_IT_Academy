package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        recyclerView = findViewById(R.id.recyclerView);


        sharedPreferences = getSharedPreferences("LearnedAnimals", Context.MODE_PRIVATE);


        int[] animalImages = {
                R.drawable.tiger, R.drawable.bear, R.drawable.pig,
                R.drawable.monkey, R.drawable.elephant, R.drawable.hippopotamus,
                R.drawable.frog, R.drawable.crocodile, R.drawable.zebra


        };


        String[] animalNames = getResources().getStringArray(R.array.animal_names);

        int[] animalSounds = {
                R.raw.sound_tiger, R.raw.sound_bear, R.raw.sound_pig,
                R.raw.sound_monkey, R.raw.sound_elephant, R.raw.sound_hippopotumus,
                R.raw.sound_frog, R.raw.sound_crocodile, R.raw.sound_zebra

        };


        AnimalsAdapter adapter = new AnimalsAdapter(this, animalImages, animalNames, animalSounds, sharedPreferences);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }
}
