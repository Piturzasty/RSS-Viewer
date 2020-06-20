package pl.edu.agh.rssviewer.ui.management;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import pl.edu.agh.rssviewer.ActivityBase;
import pl.edu.agh.rssviewer.R;

public class ManagementActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        this.setupToolbarWithTitle(R.id.toolbar_management, R.string.action_feeds);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            new AddFeedDialog().show(getSupportFragmentManager(), "AddFeedDialog");
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }
}

