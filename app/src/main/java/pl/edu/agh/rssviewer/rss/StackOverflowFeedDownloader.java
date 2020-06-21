package pl.edu.agh.rssviewer.rss;

import android.net.Uri;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowEntry;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowFeed;

public class StackOverflowFeedDownloader extends FeedDownloader<StackOverflowFeed> {
    private WeakReference<FeedAdapter> feedAdapterWeakReference;
    private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;
    private FeedRepository feedRepository;
    private boolean isExclusiveDb;

    public StackOverflowFeedDownloader(FeedRepository feedRepository) {
        this(null, null, feedRepository);
    }

    public StackOverflowFeedDownloader(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout, FeedRepository feedRepository) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
        this.feedRepository = feedRepository;
        isExclusiveDb = false;
    }

    public void exclusiveDb() {
        isExclusiveDb = true;
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
        if (stackOverflowFeed != null && isExclusiveDb) {
            String category = getCategoryName(stackOverflowFeed);
            addFeedsToDbAndGetAll(mapFeeds(stackOverflowFeed, category), category);
            return;
        }

        FeedAdapter feedAdapter = feedAdapterWeakReference.get();
        if (stackOverflowFeed != null && feedAdapter != null) {
            String category = getCategoryName(stackOverflowFeed);

            List<Feed> feeds = mapFeeds(stackOverflowFeed, category);

            combineWithDbAndAdd(feedAdapter, feeds, category);
        }

        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private List<Feed> mapFeeds(StackOverflowFeed stackOverflowFeed, String category) {
        return stackOverflowFeed
                .getEntries()
                .stream()
                .map((StackOverflowEntry entry) -> new Feed(
                        entry.getId(),
                        entry.getLink().getHref(),
                        entry.getTitle(),
                        entry.getUpdated(),
                        entry.getSummary(),
                        entry.getAuthor().getName(),
                        category,
                        FeedType.StackOverflow))
                .collect(Collectors.toList());
    }

    private String getCategoryName(StackOverflowFeed stackOverflowFeed) {
        Uri feedId = Uri.parse(stackOverflowFeed.getId());
        String category;
        if (feedId.getPathSegments().contains("tag")) {
            category = feedId.getQueryParameter("tagnames");
        } else {
            category = "general";
        }
        return category;
    }

    private void combineWithDbAndAdd(FeedAdapter feedAdapter, List<Feed> feeds, String category) {
        List<Feed> finalFeeds = addFeedsToDbAndGetAll(feeds, category);
        feedAdapter.addAll(finalFeeds);
    }

    private List<Feed> addFeedsToDbAndGetAll(List<Feed> feeds, String category) {
        List<Feed> dbFeeds = feedRepository.findByCategory(category);
        List<String> externalIds = dbFeeds.stream().map(Feed::getExternalId).collect(Collectors.toList());

        List<Feed> notExistingInDb = feeds.stream().filter(f -> !externalIds.contains(f.getExternalId())).collect(Collectors.toList());
        if (notExistingInDb.size() > 0) {
            notExistingInDb.forEach(f -> feedRepository.create(f));
        }

        dbFeeds.addAll(notExistingInDb);
        return dbFeeds;
    }
}
