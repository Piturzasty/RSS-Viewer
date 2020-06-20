package pl.edu.agh.rssviewer.rss;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.rss.feed.FeedCategory;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowEntry;
import pl.edu.agh.rssviewer.rss.feed.stackoverflow.StackOverflowFeed;

public class StackOverflowFeedDownloader extends FeedDownloader<StackOverflowFeed> {
    private WeakReference<FeedAdapter> feedAdapterWeakReference;
    private WeakReference<SwipeRefreshLayout> swipeRefreshLayoutWeakReference;
    private FeedRepository feedRepository;

    public StackOverflowFeedDownloader(FeedAdapter feedAdapter, SwipeRefreshLayout swipeRefreshLayout, FeedRepository feedRepository) {
        feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        swipeRefreshLayoutWeakReference = new WeakReference<>(swipeRefreshLayout);
        this.feedRepository = feedRepository;
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
                            entry.getId(),
                            entry.getLink().getHref(),
                            entry.getTitle(),
                            entry.getUpdated(),
                            entry.getSummary(),
                            entry.getAuthor().getName(),
                            entry.getCategories().stream().map(FeedCategory::getTerm).collect(Collectors.joining(",")),
                            FeedType.StackOverflow))
                    .collect(Collectors.toList());

            combineWithDbAndAdd(stackOverflowFeed, feedAdapter, feeds);
        }

        SwipeRefreshLayout swipeRefreshLayout = swipeRefreshLayoutWeakReference.get();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void combineWithDbAndAdd(StackOverflowFeed stackOverflowFeed, FeedAdapter feedAdapter, List<Feed> feeds) {
        List<Feed> dbFeeds = feedRepository.findByCategory(stackOverflowFeed.getEntries().get(0).getCategories().get(0).getTerm());
        List<String> externalIds = dbFeeds.stream().map(Feed::getExternalId).collect(Collectors.toList());

        List<Feed> notExistingInDb = feeds.stream().filter(f -> !externalIds.contains(f.getExternalId())).collect(Collectors.toList());
        if (notExistingInDb.size() > 0) {
            notExistingInDb.forEach(f -> feedRepository.create(f));
        }

        dbFeeds.addAll(notExistingInDb);

        feedAdapter.addAll(dbFeeds);
    }
}
