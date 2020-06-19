package pl.edu.agh.rssviewer.ui.preferences;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;

public class PreferencesActivity extends DaggerAppCompatActivity {

    @Inject
    public FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        Toolbar toolbar = findViewById(R.id.toolbar_preferences);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle(R.string.action_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment4, new PreferencesFragment())
                .commitNow();
    }
}
