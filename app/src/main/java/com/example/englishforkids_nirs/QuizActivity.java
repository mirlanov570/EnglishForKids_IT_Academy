package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private ImageView imageView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button startButton;
    private TextView correctCountTextView;
    private TextView incorrectCountTextView;

    private int correctAnswers = 0;
    private int totalQuestions = 0;

    private String[] words = {
            "Tiger", "Father", "Blue", "Frog", "Grandmother", "Dress", "Purple", "Mother", "Crocodile", "Black", "Sister"
    };

    private String[] translations = {
            "Тигр", "Папа", "Синий", "Лягушка", "Бабушка", "Платье", "Фиолетовый", "Мама", "Крокодил", "Черный", "Сестра"
    };

    private int[] images = {
            R.drawable.tiger, R.drawable.father, R.drawable.blue,
            R.drawable.frog, R.drawable.grandmother, R.drawable.dress,
            R.drawable.purple, R.drawable.mother, R.drawable.crocodile,
            R.drawable.black, R.drawable.sister
    };

    private int[] sounds = {
            R.raw.uu, R.raw.bb, R.raw.jj,
            R.raw.dd, R.raw.ee, R.raw.ff,
            R.raw.pp, R.raw.ii, R.raw.kk,
            R.raw.hh, R.raw.ll
    };

    private List<Integer> wordIndices = new ArrayList<>();
    private int currentIndex = 0;
    private Handler handler = new Handler();

    private MediaPlayer mediaPlayer;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.question);
        imageView = findViewById(R.id.imageView);
        option1Button = findViewById(R.id.option1);
        option2Button = findViewById(R.id.option2);
        option3Button = findViewById(R.id.option3);
        option4Button = findViewById(R.id.option4);
        startButton = findViewById(R.id.startButton);
        correctCountTextView = findViewById(R.id.correctCount);
        incorrectCountTextView = findViewById(R.id.incorrectCount);


        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("QuizProgress", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(); // Запуск викторины по нажатию на кнопку
            }
        });

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1Button);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2Button);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3Button);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option4Button);
            }
        });
    }

    private void startQuiz() {
        startButton.setVisibility(View.GONE); // Скрыть кнопку "Начать"
        initializeWordIndices();
        showNextQuestion();
    }

    private void initializeWordIndices() {
        wordIndices.clear();
        for (int i = 0; i < words.length; i++) {
            wordIndices.add(i);
        }
        Collections.shuffle(wordIndices);
    }

    private void showNextQuestion() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        if (wordIndices.isEmpty()) {
            // Показать результаты викторины
            showResults();
            return;
        }

        currentIndex = wordIndices.remove(0);

        // Воспроизведение звука
        mediaPlayer = MediaPlayer.create(this, sounds[currentIndex]);
        mediaPlayer.start();

        questionTextView.setText(words[currentIndex]);
        imageView.setImageResource(images[currentIndex]);

        List<String> options = new ArrayList<>();
        options.add(translations[currentIndex]);
        while (options.size() < 4) {
            int randomIndex = (int) (Math.random() * translations.length);
            if (!options.contains(translations[randomIndex])) {
                options.add(translations[randomIndex]);
            }
        }
        Collections.shuffle(options);

        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
        option4Button.setText(options.get(3));
    }

    private void checkAnswer(Button selectedButton) {
        totalQuestions++;
        if (selectedButton.getText().toString().equals(translations[currentIndex])) {
            correctAnswers++; // Увеличиваем правильные ответы
            selectedButton.setBackgroundColor(Color.GREEN);
        } else {
            selectedButton.setBackgroundColor(Color.RED);
        }


        correctCountTextView.setText("Правильные: " + correctAnswers);
        incorrectCountTextView.setText("Неправильные: " + (totalQuestions - correctAnswers));



        // Убираем задержку перед вызовом метода showResults, если вопросов больше нет
        if (wordIndices.isEmpty()) {
            showResults(); // Если вопросы закончились, показать результат
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetButtonColors();
                    showNextQuestion();
                }
            }, 1000);
        }
    }

    private void showResults() {

        Toast.makeText(this, "Викторина завершена! Правильных ответов: " + correctAnswers, Toast.LENGTH_LONG).show();


        SharedPreferences prefs = getSharedPreferences("QuizResults", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("correctAnswers", correctAnswers);
        editor.putInt("totalQuestions", totalQuestions);
        editor.apply();

        // Перезапуск викторины
        startButton.setVisibility(View.VISIBLE); // Показать кнопку "Начать" для нового запуска
        initializeWordIndices();
        correctAnswers = 0;
        totalQuestions = 0;
    }


    private void saveQuizProgress(int correctAnswers) {
        editor.putInt("correct_answers", correctAnswers);  // Сохраняем количество правильных ответов
        editor.apply();
    }



    private void resetButtonColors() {
        option1Button.setBackgroundColor(Color.LTGRAY);
        option2Button.setBackgroundColor(Color.LTGRAY);
        option3Button.setBackgroundColor(Color.LTGRAY);
        option4Button.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
