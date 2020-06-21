package pl.edu.agh.rssviewer;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.receiver.NetworkChangedBroadcastReceiver;
import pl.edu.agh.rssviewer.receiver.RefreshedBroadcastReceiver;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsActivity;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsFragment;
import pl.edu.agh.rssviewer.ui.main.FeedListFragment;

public class MainActivity extends ActivityBase implements FeedListFragment.OnListFragmentInteractionListener {
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupToolbar(R.id.toolbar, false);

        createNotificationChannel();

        setupRepeatedBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        broadcastReceiver = new NetworkChangedBroadcastReceiver(getApplicationContext(), findViewById(R.id.linear_layout));
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onListFragmentInteraction(Feed feed) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment2, FeedDetailsFragment.newInstance(feed));
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(MainActivity.this, FeedDetailsActivity.class);
            intent.putExtra("feed", feed);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void setupRepeatedBroadcastReceiver() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, RefreshedBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("pl.edu.agh.rssviewer.REFRESH", "pl.edu.agh.rssviewer.REFRESH", importance);
        channel.setDescription("Channel for notification about new feeds");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }
}
