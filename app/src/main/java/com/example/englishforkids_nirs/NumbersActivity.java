package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NumbersActivity extends AppCompatActivity {

    private ImageButton numButton;
    private ImageView nextLetterButton;
    private ImageView prevLetterButton;
    private TextView numberTitle;
    private ImageView markLearnedButton;

    private final int[] letterImages = {
            R.drawable.let_1, R.drawable.let_2, R.drawable.let_3, R.drawable.let_4, R.drawable.let_5,
            R.drawable.let_6, R.drawable.let_7, R.drawable.let_8, R.drawable.let_9, R.drawable.let_10
    };

    private final int[] letterSounds = {
            R.raw.sound_1, R.raw.sound_2, R.raw.sound_3, R.raw.sound_4, R.raw.sound_5,
            R.raw.sound_6, R.raw.sound_7, R.raw.sound_8, R.raw.sound_9, R.raw.sound_10
    };

    private final String[] numberTitles = {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };

    private int currentIndex = 0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        numButton = findViewById(R.id.numButton);
        nextLetterButton = findViewById(R.id.nextLetterButton);
        prevLetterButton = findViewById(R.id.prevLetterButton);
        numberTitle = findViewById(R.id.numberTitle);
        markLearnedButton = findViewById(R.id.markLearnedButton);

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("LearnedNumbers", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        updateLetterCard();

        // Воспроизведение звука цифры
        numButton.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(NumbersActivity.this, letterSounds[currentIndex]);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        });

        // Следующая цифра
        nextLetterButton.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % letterImages.length;
            updateLetterCard();
        });

        // Предыдущая цифра
        prevLetterButton.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + letterImages.length) % letterImages.length;
            updateLetterCard();
        });

        // Отметить цифру как выученную
        markLearnedButton.setOnClickListener(v -> {
            markNumberAsLearned(currentIndex);
            Toast.makeText(this, "Цифра " + numberTitles[currentIndex] + " отмечена как выученная!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateLetterCard() {
        numButton.setImageResource(letterImages[currentIndex]);
        numberTitle.setText(numberTitles[currentIndex]);
    }

    private void markNumberAsLearned(int index) {
        String numberKey = "number_" + index;
        boolean isAlreadyLearned = sharedPreferences.getBoolean(numberKey, false);

        if (!isAlreadyLearned) {
            editor.putBoolean(numberKey, true);
            editor.apply();
        }
    }
}
