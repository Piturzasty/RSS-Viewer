package pl.edu.agh.rssviewer.rss;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.rss.feed.reddit.RedditEntry;
import pl.edu.agh.rssviewer.rss.feed.reddit.RedditFeed;

public class RedditFeedDownloader extends FeedDownloader<RedditFeed> {
    private WeakReference<FeedAdapter> feedAdapterWeakReference;
    private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;
    private FeedRepository feedRepository;
    private boolean isExclusiveDb;

    public RedditFeedDownloader(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout, FeedRepository feedRepository) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
        this.feedRepository = feedRepository;
        isExclusiveDb = false;
    }

    public void exclusiveDb() {
        isExclusiveDb = true;
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
        if (redditFeed != null && isExclusiveDb) {
            addFeedsToDbAndGetAll(redditFeed, mapFeeds(redditFeed));
            return;
        }

        FeedAdapter feedAdapter = feedAdapterWeakReference.get();
        if (redditFeed != null && feedAdapter != null) {
            List<Feed> feeds = mapFeeds(redditFeed);
            combineWithDbAndAdd(redditFeed, feedAdapter, feeds);
        }

        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private List<Feed> mapFeeds(RedditFeed redditFeed) {
        return redditFeed
                .getEntries()
                .stream()
                .map((RedditEntry entry) -> new Feed(
                        entry.getId(),
                        entry.getLink().getHref(),
                        entry.getTitle(),
                        entry.getUpdated(),
                        entry.getContent(),
                        entry.getAuthor().getName(),
                        redditFeed.getCategory().getTerm(),
                        FeedType.Reddit))
                .collect(Collectors.toList());
    }

    private void combineWithDbAndAdd(RedditFeed redditFeed, FeedAdapter feedAdapter, List<Feed> feeds) {
        List<Feed> finalFeeds = addFeedsToDbAndGetAll(redditFeed, feeds);
        feedAdapter.addAll(finalFeeds);
    }

    private List<Feed> addFeedsToDbAndGetAll(RedditFeed redditFeed, List<Feed> feeds) {
        List<Feed> dbFeeds = feedRepository.findByCategory(redditFeed.getCategory().getTerm());
        List<String> externalIds = dbFeeds.stream().map(Feed::getExternalId).collect(Collectors.toList());

        List<Feed> notExistingInDb = feeds.stream().filter(f -> !externalIds.contains(f.getExternalId())).collect(Collectors.toList());
        if (notExistingInDb.size() > 0) {
            notExistingInDb.forEach(f -> feedRepository.create(f));
        }

        dbFeeds.addAll(notExistingInDb);
        return dbFeeds;
    }
}

