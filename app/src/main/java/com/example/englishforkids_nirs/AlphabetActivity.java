package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlphabetActivity extends AppCompatActivity {

    private ImageButton letterButton;
    private ImageView nextLetterButton;
    private ImageView prevLetterButton;
    private ImageView markLearnedButton;

    private final int[] letterImages = {
            R.drawable.let_a, R.drawable.let_b, R.drawable.let_c, R.drawable.let_d, R.drawable.let_e, R.drawable.let_f,
            R.drawable.let_g, R.drawable.let_h, R.drawable.let_i, R.drawable.let_j, R.drawable.let_k, R.drawable.let_l,
            R.drawable.let_m, R.drawable.let_n, R.drawable.let_o, R.drawable.let_p, R.drawable.let_q, R.drawable.let_r,
            R.drawable.let_s, R.drawable.let_t, R.drawable.let_u, R.drawable.let_v, R.drawable.let_w, R.drawable.let_x,
            R.drawable.let_y, R.drawable.let_z
    };

    private final int[] letterSounds = {
            R.raw.sound_a, R.raw.sound_b, R.raw.sound_c, R.raw.sound_d, R.raw.sound_e, R.raw.sound_f,
            R.raw.sound_g, R.raw.sound_h, R.raw.sound_i, R.raw.sound_j, R.raw.sound_k, R.raw.sound_l,
            R.raw.sound_m, R.raw.sound_n, R.raw.sound_o, R.raw.sound_p, R.raw.sound_q, R.raw.sound_r,
            R.raw.sound_s, R.raw.sound_t, R.raw.sound_u, R.raw.sound_v, R.raw.sound_w, R.raw.sound_x,
            R.raw.sound_y, R.raw.sound_z
    };

    private int currentIndex = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        letterButton = findViewById(R.id.letterButton);
        nextLetterButton = findViewById(R.id.nextLetterButton);
        prevLetterButton = findViewById(R.id.prevLetterButton);
        markLearnedButton = findViewById(R.id.markLearnedButton);

        sharedPreferences = getSharedPreferences("LearnedLetters", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        updateLetterCard();

        // Воспроизведение звука буквы
        letterButton.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(AlphabetActivity.this, letterSounds[currentIndex]);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        });

        // Переход к следующей букве
        nextLetterButton.setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % letterImages.length;
            updateLetterCard();
        });

        // Переход к предыдущей букве
        prevLetterButton.setOnClickListener(v -> {
            currentIndex = (currentIndex - 1 + letterImages.length) % letterImages.length;
            updateLetterCard();
        });

        // Логика кнопки "Отметить как выученное"
        markLearnedButton.setOnClickListener(v -> {
            markLetterAsLearned(currentIndex);
            Toast.makeText(this, "Буква " + (char) ('A' + currentIndex) + " отмечена как выученная!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateLetterCard() {
        letterButton.setImageResource(letterImages[currentIndex]);
    }

    private void markLetterAsLearned(int index) {
        String letterKey = "letter_" + index;  // ключ для буквы
        boolean isAlreadyLearned = sharedPreferences.getBoolean(letterKey, false);

        if (!isAlreadyLearned) {
            String timeKey = letterKey + "_" + System.currentTimeMillis();  // добавление метки времени
            editor.putBoolean(letterKey, true);  // сохраняем, что буква выучена
            editor.putString(timeKey, new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date()));
            editor.apply();
        }
    }

}
