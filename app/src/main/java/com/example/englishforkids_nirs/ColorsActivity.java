package com.example.englishforkids_nirs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ColorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        recyclerView = findViewById(R.id.recyclerView);

        int[] colorImages = {
                R.drawable.red, R.drawable.blue, R.drawable.green,
                R.drawable.purple, R.drawable.yellow, R.drawable.black,
                R.drawable.pink, R.drawable.orange, R.drawable.grey
        };

        String[] colorNames = getResources().getStringArray(R.array.color_names);


        int[] colorSounds = {
                R.raw.sound_red, R.raw.sound_blue, R.raw.sound_green,
                R.raw.sound_purple, R.raw.sound_yellow, R.raw.black,
                R.raw.pink, R.raw.orange, R.raw.grey
        };

        ColorsAdapter adapter = new ColorsAdapter(this, colorImages,  colorNames, colorSounds);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }
}