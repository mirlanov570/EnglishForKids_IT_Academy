package com.example.englishforkids_nirs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClothingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);

        recyclerView = findViewById(R.id.recyclerView);

        // Массив изображений одежды
        int[] clothingImages = {
                R.drawable.shirt, R.drawable.pants, R.drawable.jacket,
                R.drawable.hat, R.drawable.shoes, R.drawable.dress,
                R.drawable.scarf, R.drawable.gloves, R.drawable.socks
        };

        // Массив названий одежды
        String[] clothingNames = getResources().getStringArray(R.array.clothing_names);

        // Массив звуков одежды (если требуется)
        int[] clothingSounds = {
                R.raw.sound_shirt, R.raw.sound_pants, R.raw.sound_jacket,
                R.raw.sound_hat, R.raw.sound_shoes, R.raw.sound_dress,
                R.raw.sound_scarf, R.raw.sound_gloves, R.raw.sound_socks
        };


        ClothingAdapter adapter = new ClothingAdapter(this, clothingImages, clothingNames, clothingSounds);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }
}
