package com.example.englishforkids_nirs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    private TextView learnedWordsTextView;
    private TextView learnedWordsListTextView;
    private TextView quizResultsTextView;
    private Button resetButton;
    private SharedPreferences lettersPrefs;
    private SharedPreferences numbersPrefs;
    private SharedPreferences quizPrefs;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        pieChart = findViewById(R.id.pieChart);
        learnedWordsTextView = findViewById(R.id.learnedWordsTextView);
        learnedWordsListTextView = findViewById(R.id.learnedWordsListTextView);
        quizResultsTextView = findViewById(R.id.quizResultsTextView);
        resetButton = findViewById(R.id.resetButton);

        lettersPrefs = getSharedPreferences("LearnedLetters", Context.MODE_PRIVATE);
        numbersPrefs = getSharedPreferences("LearnedNumbers", Context.MODE_PRIVATE);
        quizPrefs = getSharedPreferences("QuizResults", Context.MODE_PRIVATE);

        updateLearnedWordsCount();
        updateLearnedWordsList();
        updateQuizResults();
        updatePieChart();

        resetButton.setOnClickListener(v -> {
            resetLearnedData();
            updateLearnedWordsCount();
            updateLearnedWordsList();
            resetQuizResults();
            updatePieChart();
        });
    }

    private void updateLearnedWordsCount() {
        int learnedLettersCount = countLearnedItems(lettersPrefs, "letter_");
        int learnedNumbersCount = countLearnedItems(numbersPrefs, "number_");

        learnedWordsTextView.setText("Выучено букв: " + learnedLettersCount +
                "\nВыучено цифр: " + learnedNumbersCount);
    }

    private void updateLearnedWordsList() {
        String learnedLettersString = getLearnedItemsString(lettersPrefs, "letter_", true);
        String learnedNumbersString = getLearnedItemsString(numbersPrefs, "number_", false);

        learnedWordsListTextView.setText("Буквы: " + learnedLettersString +
                "\nЦифры: " + learnedNumbersString);
    }

    private void updateQuizResults() {
        int correctAnswers = quizPrefs.getInt("correctAnswers", 0);
        int totalQuestions = quizPrefs.getInt("totalQuestions", 0);
        String result = "Результаты викторины:\nПравильных ответов: " + correctAnswers +
                "\nВсего вопросов: " + totalQuestions;
        quizResultsTextView.setText(result);
    }

    private void updatePieChart() {
        int learnedLettersCount = countLearnedItems(lettersPrefs, "letter_");
        int learnedNumbersCount = countLearnedItems(numbersPrefs, "number_");
        int correctAnswers = quizPrefs.getInt("correctAnswers", 0);
        int totalQuestions = quizPrefs.getInt("totalQuestions", 0);
        int incorrectAnswers = totalQuestions - correctAnswers;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(learnedLettersCount, "Выученные буквы"));
        entries.add(new PieEntry(learnedNumbersCount, "Выученные цифры"));
        if (totalQuestions > 0) {
            entries.add(new PieEntry(correctAnswers, "Правильные ответы"));
            entries.add(new PieEntry(incorrectAnswers, "Неправильные ответы"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Прогресс");
        dataSet.setColors(
                Color.rgb(63, 81, 181),  // Синий
                Color.rgb(0, 150, 136), // Зеленый
                Color.rgb(255, 193, 7), // Желтый
                Color.rgb(244, 67, 54)  // Красный
        );
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Прогресс");
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void resetLearnedData() {
        resetSharedPreferences(lettersPrefs);
        resetSharedPreferences(numbersPrefs);
    }

    private void resetQuizResults() {
        SharedPreferences.Editor editor = quizPrefs.edit();
        editor.clear();
        editor.apply();
        quizResultsTextView.setText("Результаты викторины: Нет данных");
    }

    private void resetSharedPreferences(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    private int countLearnedItems(SharedPreferences prefs, String prefix) {
        int count = 0;
        for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
            if (entry.getKey().startsWith(prefix) && entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                count++;
            }
        }
        return count;
    }

    private String getLearnedItemsString(SharedPreferences prefs, String prefix, boolean isLetter) {
        ArrayList<String> learnedItems = new ArrayList<>();
        for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
            if (entry.getKey().startsWith(prefix) && entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                if (isLetter) {
                    char letter = (char) ('A' + Integer.parseInt(entry.getKey().substring(prefix.length())));
                    learnedItems.add(String.valueOf(letter));
                } else {
                    int number = Integer.parseInt(entry.getKey().substring(prefix.length())) + 1;
                    learnedItems.add(String.valueOf(number));
                }
            }
        }
        Collections.sort(learnedItems);
        return String.join(", ", learnedItems);
    }
}
