package pl.edu.agh.rssviewer.ui.details;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import pl.edu.agh.rssviewer.ActivityBase;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.persistence.model.Feed;

public class FeedDetailsActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ActionBar ab = this.setupToolbar(R.id.toolbar_details, true);

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
}
