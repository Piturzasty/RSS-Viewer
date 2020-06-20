package pl.edu.agh.rssviewer.rss;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.rss.feed.reddit.RedditEntry;
import pl.edu.agh.rssviewer.rss.feed.reddit.RedditFeed;

public class RedditFeedDownloader extends FeedDownloader<RedditFeed> {
    private WeakReference<FeedAdapter> feedAdapterWeakReference;
    private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;

    public RedditFeedDownloader(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
    }

    @Override
    protected RedditFeed doInBackground(String... strings) {
        if (strings.length != 1) {
            return null;
        }
        return downloadFeed(strings[0], RedditFeed.class);
    }

    @Override
    protected void onPostExecute(RedditFeed redditFeed) {
        super.onPostExecute(redditFeed);

        FeedAdapter feedAdapter = feedAdapterWeakReference.get();

        if (redditFeed != null && feedAdapter != null) {
            List<Feed> feeds = redditFeed
                    .getEntries()
                    .stream()
                    .map((RedditEntry entry) -> new Feed(
                            entry.getLink().getHref(),
                            entry.getTitle(),
                            entry.getUpdated(),
                            entry.getContent(),
                            entry.getAuthor().getName(),
                            FeedType.Reddit))
                    .collect(Collectors.toList());

            feedAdapter.addAll(feeds);
        }

        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

