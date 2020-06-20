package pl.edu.agh.rssviewer.ui.management;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import pl.edu.agh.rssviewer.ActivityBase;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.adapter.FeedSourceAdapter;
import pl.edu.agh.rssviewer.background.FeedSourceDownloaderTask;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;

public class ManagementActivity extends ActivityBase implements AddFeedDialog.OnDialogInteractionListener {

    private List<FeedSource> feedSources = new ArrayList<>();

    @Inject
    FeedSourceRepository feedSourceRepository;

    @Inject
    FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        this.setupToolbarWithTitle(R.id.toolbar_management, R.string.action_feeds);

        FeedSourceAdapter feedAdapter = setupRecyclerView();

        new FeedSourceDownloaderTask(feedAdapter, feedRepository, feedSourceRepository).execute();

        FloatingActionButton fab = findViewById(R.id.fab_management);
        fab.setOnClickListener(view -> new AddFeedDialog().show(getSupportFragmentManager(), "AddFeedDialog"));
    }

    @Override
    public void onAddButtonClick(String uri) {
        onClick(uri);

        Snackbar.make(findViewById(R.id.container_management), "Feed has been added", Snackbar.LENGTH_LONG)
                .setAction("Refresh", v -> onRefresh())
                .show();
    }

    private FeedSourceAdapter setupRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.feed_source_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FeedSourceAdapter feedAdapter = new FeedSourceAdapter(feedSources);
        recyclerView.setAdapter(feedAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        return feedAdapter;
    }

    private void onClick(String uri) {
        Log.d("ManagementActivity", uri);
    }

    private void onRefresh() {
        Log.d("ManagementActivity", "refreshed");
    }
}

