package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import pl.edu.agh.rssviewer.rss.feed.FeedEntry;

@Root(name = "entry", strict = false)
public class RedditEntry extends FeedEntry {
    @Element
    private String content;

    public RedditEntry() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
