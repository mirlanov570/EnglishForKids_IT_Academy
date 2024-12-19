package com.example.englishforkids_nirs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import android.net.Uri;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "daily_reminder";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Создание канала уведомлений
            NotificationChannel channel = new NotificationChannel(channelId, "Ежедневные напоминания",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Указание звука уведомления
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound);

            // Установка звука для канала уведомлений
            channel.setSound(soundUri, null);
            notificationManager.createNotificationChannel(channel);
        }

        // Создание уведомления
        int notificationId = (int) System.currentTimeMillis();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Напоминание")
                        .setContentText("Пора заняться обучением!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound)); // Звук для уведомления

        // Отправка уведомления
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
