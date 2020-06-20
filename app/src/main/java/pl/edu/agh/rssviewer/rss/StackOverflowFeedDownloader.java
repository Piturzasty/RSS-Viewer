package pl.edu.agh.rssviewer.rss;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.rssviewer.adapter.Feed;
import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.adapter.FeedType;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowEntry;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowFeed;

public class StackOverflowFeedDownloader extends FeedDownloader<StackOverflowFeed> {
    private WeakReference<FeedAdapter> feedAdapterWeakReference;
    private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;

    public StackOverflowFeedDownloader(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
    }

    @Override
    protected StackOverflowFeed doInBackground(String... strings) {
        if (strings.length != 1) {
            return null;
        }
        return downloadFeed(strings[0], StackOverflowFeed.class);
    }

    @Override
    protected void onPostExecute(StackOverflowFeed stackOverflowFeed) {
        super.onPostExecute(stackOverflowFeed);

        FeedAdapter feedAdapter = feedAdapterWeakReference.get();

        if (stackOverflowFeed != null && feedAdapter != null) {
            List<Feed> feeds = stackOverflowFeed
                    .getEntries()
                    .stream()
                    .map((StackOverflowEntry entry) -> new Feed(
                            entry.getTitle(),
                            entry.getSummary(),
                            entry.getUpdated(),
                            entry.getAuthor().getName(),
                            entry.getLink().getHref(),
                            FeedType.StackOverflow))
                    .collect(Collectors.toList());

            feedAdapter.clear();
            feedAdapter.addAll(feeds);
        }

        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
