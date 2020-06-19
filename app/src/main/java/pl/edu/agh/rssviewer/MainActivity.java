package pl.edu.agh.rssviewer;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.j256.ormlite.stmt.query.In;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import pl.edu.agh.rssviewer.adapter.Feed;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.receiver.NetworkChangedBroadcastReceiver;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsActivity;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsFragment;
import pl.edu.agh.rssviewer.ui.main.FeedListFragment;
import pl.edu.agh.rssviewer.ui.preferences.PreferencesActivity;

public class MainActivity extends DaggerAppCompatActivity implements FeedListFragment.OnListFragmentInteractionListener {

    @Inject
    protected FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(new NetworkChangedBroadcastReceiver(getApplicationContext(), findViewById(R.id.linear_layout)), intentFilter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(false);

//        feedRepository.create(new Feed("test"));
//
//        Feed fetchedFeed = feedRepository.findById(1L);
//        fetchedFeed.setUrl("changed");
//
//        feedRepository.update(fetchedFeed);
//
//        List<Feed> feeds = feedRepository.findAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
