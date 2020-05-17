package pl.edu.agh.rssviewer.rss;

import pl.edu.agh.rssviewer.rss.feed.RedditFeed;

public class RedditFeedDownloader extends FeedDownloader<RedditFeed> {
    @Override
    protected RedditFeed doInBackground(String... strings) {
        if (strings.length != 1) {
            return null;
        }
        return downloadFeed(strings[0], RedditFeed.class);
    }
}

