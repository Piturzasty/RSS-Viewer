package pl.edu.agh.rssviewer.ui.management;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import pl.edu.agh.rssviewer.ActivityBase;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.adapter.FeedSourceAdapter;
import pl.edu.agh.rssviewer.background.FeedSourceDownloaderTask;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;
import pl.edu.agh.rssviewer.rss.FeedType;

public class ManagementActivity extends ActivityBase implements AddFeedDialog.OnDialogInteractionListener, FeedSourceAdapter.OnButtonInteractionListener {

    private FeedSourceAdapter feedSourceAdapter;

    @Inject
    FeedSourceRepository feedSourceRepository;
    @Inject
    FeedRepository feedRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        this.setupToolbarWithTitle(R.id.toolbar_management, R.string.action_feeds);

        feedSourceAdapter = setupRecyclerView();

        new FeedSourceDownloaderTask(feedSourceAdapter, feedRepository, feedSourceRepository).execute();

        FloatingActionButton fab = findViewById(R.id.fab_management);
        fab.setOnClickListener(view -> new AddFeedDialog().show(getSupportFragmentManager(), "AddFeedDialog"));
    }

    @Override
    public void onAddButtonClick(String url) {
        try {
            onClick(url);

            makeSnackBar("Feed has been added").setAction("Refresh", v -> onRefresh()).show();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onClickRemove(FeedSource feedSource) {
        for (Feed feed : feedRepository.findByCategory(feedSource.getCategory())) {
            feedRepository.delete(feed);
        }
        feedSourceRepository.delete(feedSource);
        new FeedSourceDownloaderTask(feedSourceAdapter, feedRepository, feedSourceRepository).execute();
        makeSnackBar("Feed source was deleted successfully").show();
    }

    private FeedSourceAdapter setupRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.feed_source_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FeedSourceAdapter feedAdapter = new FeedSourceAdapter(this);
        recyclerView.setAdapter(feedAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        return feedAdapter;
    }

    private void onClick(String url) throws Exception {
        FeedSource feedSource = feedSourceRepository.findByUrl(url);
        if (feedSource != null) {
            makeSnackBar("Feed source with the given url already exists").show();
            throw new Exception();
        }

        String category;
        FeedType feedType;
        try {
            category = getCategoryFromUrl(url);
            feedType = getFeedType(url);
        } catch (Exception exception) {
            makeSnackBar(exception.getMessage()).show();
            throw exception;
        }

        feedSourceRepository.create(new FeedSource(url, category, feedType));
    }

    private void onRefresh() {
        new FeedSourceDownloaderTask(feedSourceAdapter, feedRepository, feedSourceRepository).execute();
    }

    private FeedType getFeedType(String url) throws Exception {
        Uri uri = Uri.parse(url);
        assert uri.getAuthority() != null;
        if (uri.getAuthority().contains("reddit")) {
            return FeedType.Reddit;
        } else if (uri.getAuthority().contains("stackoverflow")) {
            return FeedType.StackOverflow;
        }
        throw new Exception("Currently feeds from this site are not supported");
    }

    private String getCategoryFromUrl(String url) throws Exception {
        Uri uri = Uri.parse(url);
        assert uri.getAuthority() != null;
        if (uri.getAuthority().contains("reddit")) {
            int index = uri.getPathSegments().indexOf("r");
            if (index != -1 && uri.getPathSegments().size() > index) {
                return uri.getPathSegments().get(index + 1);
            }
        } else if (uri.getAuthority().contains("stackoverflow")) {
            if (uri.getPathSegments().contains("tag")) {
                return uri.getQueryParameter("tagnames");
            }
            return "general";
        }
        throw new Exception("Currently feeds from this site are not supported");
    }

    private Snackbar makeSnackBar(String message) {
        return Snackbar.make(findViewById(R.id.container_management), message, Snackbar.LENGTH_LONG);
    }
}

