package pl.edu.agh.rssviewer;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.receiver.NetworkChangedBroadcastReceiver;
import pl.edu.agh.rssviewer.rss.Feed;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsActivity;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsFragment;
import pl.edu.agh.rssviewer.ui.main.FeedListFragment;

public class MainActivity extends ActivityBase implements FeedListFragment.OnListFragmentInteractionListener {
    BroadcastReceiver broadcastReceiver;

    @Inject
    protected FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupToolbar(R.id.toolbar, false);

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        broadcastReceiver  = new NetworkChangedBroadcastReceiver(getApplicationContext(), findViewById(R.id.linear_layout));
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
}
