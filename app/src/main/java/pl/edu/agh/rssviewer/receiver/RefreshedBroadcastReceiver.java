package pl.edu.agh.rssviewer.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.background.FeedDownloaderTask;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;

public class RefreshedBroadcastReceiver extends DaggerBroadcastReceiver {

    @Inject
    FeedSourceRepository feedSourceRepository;
    @Inject
    FeedRepository feedRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        new FeedDownloaderTask(feedRepository, feedSourceRepository).execute();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean allowPushNotifications = sharedPreferences.getBoolean("allow_push_notifications", false);
        if (allowPushNotifications) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "pl.edu.agh.rssviewer.REFRESH")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("New feeds")
                    .setContentText("New feeds are available for you!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(0, builder.build());
        }
    }
}
