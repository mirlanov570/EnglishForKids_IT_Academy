package com.example.englishforkids_nirs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private CheckBox checkMonday, checkTuesday, checkWednesday, checkThursday, checkFriday, checkSaturday, checkSunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        timePicker = findViewById(R.id.timePicker);
        checkMonday = findViewById(R.id.checkMonday);
        checkTuesday = findViewById(R.id.checkTuesday);
        checkWednesday = findViewById(R.id.checkWednesday);
        checkThursday = findViewById(R.id.checkThursday);
        checkFriday = findViewById(R.id.checkFriday);
        checkSaturday = findViewById(R.id.checkSaturday);
        checkSunday = findViewById(R.id.checkSunday);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> saveNotificationSettings());
    }

    private void saveNotificationSettings() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        if (checkMonday.isChecked()) {
            scheduleNotification(Calendar.MONDAY, hour, minute);
        }

        if (checkTuesday.isChecked()) {
            scheduleNotification(Calendar.TUESDAY, hour, minute);
        }

        if (checkWednesday.isChecked()) {
            scheduleNotification(Calendar.WEDNESDAY, hour, minute);
        }

        if (checkThursday.isChecked()) {
            scheduleNotification(Calendar.THURSDAY, hour, minute);
        }

        if (checkFriday.isChecked()) {
            scheduleNotification(Calendar.FRIDAY, hour, minute);
        }

        if (checkSaturday.isChecked()) {
            scheduleNotification(Calendar.SATURDAY, hour, minute);
        }

        if (checkSunday.isChecked()) {
            scheduleNotification(Calendar.SUNDAY, hour, minute);
        }

        Toast.makeText(this, "Расписание сохранено!", Toast.LENGTH_SHORT).show();
    }

    private void scheduleNotification(int dayOfWeek, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }


        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, dayOfWeek, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    }
