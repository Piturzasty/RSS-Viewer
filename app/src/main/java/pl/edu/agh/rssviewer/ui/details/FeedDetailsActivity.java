package pl.edu.agh.rssviewer.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.rss.Feed;
import pl.edu.agh.rssviewer.ui.preferences.PreferencesActivity;

public class FeedDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        Feed feed = (Feed) getIntent().getSerializableExtra("feed");
        if (feed == null) {
            return;
        }

        ab.setTitle(feed.getTitle());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment3, FeedDetailsFragment.newInstance(feed))
                    .commitNow();
        }
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
                Intent intent = new Intent(FeedDetailsActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
