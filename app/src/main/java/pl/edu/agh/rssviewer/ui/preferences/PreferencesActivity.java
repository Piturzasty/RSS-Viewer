package pl.edu.agh.rssviewer.ui.preferences;

import android.os.Bundle;

import javax.inject.Inject;

import pl.edu.agh.rssviewer.ActivityBase;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;

public class PreferencesActivity extends ActivityBase {

    @Inject
    public FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        this.noMenu();
        this.setupToolbarWithTitle(R.id.toolbar_preferences, R.string.action_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment4, new PreferencesFragment())
                .commitNow();
    }
}
