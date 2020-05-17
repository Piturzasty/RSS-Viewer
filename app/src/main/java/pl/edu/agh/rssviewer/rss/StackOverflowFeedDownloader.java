package pl.edu.agh.rssviewer.rss;

import pl.edu.agh.rssviewer.rss.feed.StackOverflowFeed;

public class StackOverflowFeedDownloader extends FeedDownloader<StackOverflowFeed> {

    @Override
    protected StackOverflowFeed doInBackground(String... strings) {
        if (strings.length != 1) {
            return null;
        }
        return downloadFeed(strings[0], StackOverflowFeed.class);
    }
}
