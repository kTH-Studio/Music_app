package ru.kartofan.theme.music.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_2_ID = "Media player";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Media player",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel2);
        }
    }
}