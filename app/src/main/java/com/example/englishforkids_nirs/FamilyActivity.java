package com.example.englishforkids_nirs;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FamilyActivity extends AppCompatActivity {

    private ImageButton letterButton;
    private ImageView nextLetterButton;
    private ImageView prevLetterButton;
    private TextView letterTitle;

    private final int[] letterImages = {
            R.drawable.father, R.drawable.mother, R.drawable.brother, R.drawable.sister, R.drawable.grandmother,
            R.drawable.grandfather, R.drawable.uncle, R.drawable.aunt, R.drawable.parents, R.drawable.grandparents
    };

    private final int[] letterSounds = {
            R.raw.father, R.raw.mother, R.raw.brother, R.raw.sister, R.raw.grandmother,
            R.raw.grandfather, R.raw.uncle, R.raw.aunt, R.raw.parents, R.raw.grandparents
    };

    private final String[] letterTitles = {
            "Father", "Mother", "Brother", "Sister", "Grandmother",
            "Grandfather", "Uncle", "Aunt", "Parents", "Grandparents"
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        letterButton = findViewById(R.id.letterButton);
        nextLetterButton = findViewById(R.id.nextLetterButton);
        prevLetterButton = findViewById(R.id.prevLetterButton);
        letterTitle = findViewById(R.id.letterTitle);

        updateLetterCard();

        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(FamilyActivity.this, letterSounds[currentIndex]);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        });

        nextLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % letterImages.length;
                updateLetterCard();
            }
        });

        prevLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1 + letterImages.length) % letterImages.length;
                updateLetterCard();
            }
        });
    }

    private void updateLetterCard() {
        letterButton.setImageResource(letterImages[currentIndex]);
        letterTitle.setText(letterTitles[currentIndex]);
    }
}
