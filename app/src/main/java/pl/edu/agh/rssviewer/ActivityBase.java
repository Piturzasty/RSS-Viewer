package pl.edu.agh.rssviewer;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import dagger.android.support.DaggerAppCompatActivity;
import pl.edu.agh.rssviewer.ui.management.ManagementActivity;
import pl.edu.agh.rssviewer.ui.preferences.PreferencesActivity;

public abstract class ActivityBase extends DaggerAppCompatActivity {
    private boolean hasMenu = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (hasMenu) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_feeds:
                if (!(this instanceof ManagementActivity)) {
                    startActivity(new Intent(this, ManagementActivity.class));
                }
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, PreferencesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void noMenu() {
        hasMenu = false;
    }

    protected ActionBar setupToolbar(int id, boolean canGoBack) {
        Toolbar toolbar = findViewById(id);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(canGoBack);

        return ab;
    }

    protected void setupToolbarWithTitle(int id, int title) {
        setupToolbar(id, true).setTitle(title);
    }
}
