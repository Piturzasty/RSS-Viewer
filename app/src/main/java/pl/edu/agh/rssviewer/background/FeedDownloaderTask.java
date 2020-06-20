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

    public FeedDownloaderTask(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout, FeedRepository feedRepository, FeedSourceRepository feedSourceRepository) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
        this.feedRepository = feedRepository;
        this.feedSourceRepository = feedSourceRepository;
    }

    @Override
    protected List<FeedSource> doInBackground(Void... voids) {
        return feedSourceRepository.findAll();
    }

    @Override
    protected void onPostExecute(List<FeedSource> feedSources) {
        FeedAdapter feedAdapter = feedAdapterWeakReference.get();
        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();

        if (feedAdapter != null && swipeRefreshLayout != null) {
            feedAdapter.clear();
            for (FeedSource feedSource : feedSources) {
                switch (feedSource.getType()) {
                    case Reddit:
                        new RedditFeedDownloader(feedAdapter, swipeRefreshLayout, feedRepository).execute(feedSource.getUrl());
                    case StackOverflow:
                        new StackOverflowFeedDownloader(feedAdapter, swipeRefreshLayout, feedRepository).execute(feedSource.getUrl());
                }
            }
        }
    }
}
