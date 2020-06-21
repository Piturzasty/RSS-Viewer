package pl.edu.agh.rssviewer.background;

import android.os.AsyncTask;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;
import pl.edu.agh.rssviewer.rss.RedditFeedDownloader;
import pl.edu.agh.rssviewer.rss.StackOverflowFeedDownloader;

public class FeedDownloaderTask extends AsyncTask<Void, Void, List<FeedSource>> {
    private final WeakReference<FeedAdapter> feedAdapterWeakReference;
    private final WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;
    private FeedRepository feedRepository;
    private FeedSourceRepository feedSourceRepository;
    private boolean isExclusiveDb;

    public FeedDownloaderTask(FeedRepository feedRepository, FeedSourceRepository feedSourceRepository) {
        this(null, null, feedRepository, feedSourceRepository);
        isExclusiveDb = true;
    }

    public FeedDownloaderTask(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout, FeedRepository feedRepository, FeedSourceRepository feedSourceRepository) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
        this.feedRepository = feedRepository;
        this.feedSourceRepository = feedSourceRepository;
        isExclusiveDb = false;
    }

    @Override
    protected List<FeedSource> doInBackground(Void... voids) {
        return feedSourceRepository.findAll();
    }

    @Override
    protected void onPostExecute(List<FeedSource> feedSources) {
        if (isExclusiveDb) {
            processFeedSources(feedSources, null, null);
        }

        FeedAdapter feedAdapter = feedAdapterWeakReference.get();
        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (feedAdapter != null && swipeRefreshLayout != null) {
            feedAdapter.clear();
            processFeedSources(feedSources, feedAdapter, swipeRefreshLayout);
        }
    }

    private void processFeedSources(List<FeedSource> feedSources, FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        for (FeedSource feedSource : feedSources) {
            switch (feedSource.getType()) {
                case Reddit:
                    RedditFeedDownloader redditFeedDownloader = new RedditFeedDownloader(feedAdapter, swipeRefreshLayout, feedRepository);
                    if (isExclusiveDb) {
                        redditFeedDownloader.exclusiveDb();
                    }
                    redditFeedDownloader.execute(feedSource.getUrl());
                    break;
                case StackOverflow:
                    StackOverflowFeedDownloader stackOverflowFeedDownloader = new StackOverflowFeedDownloader(feedAdapter, swipeRefreshLayout, feedRepository);
                    if (isExclusiveDb) {
                        stackOverflowFeedDownloader.exclusiveDb();
                    }
                    stackOverflowFeedDownloader.execute(feedSource.getUrl());
                    break;
            }
        }
    }
}
