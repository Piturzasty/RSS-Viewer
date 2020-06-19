package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "author", strict = false)
public class RedditFeedAuthor {
    @Element
    private String name;
    @Element
    private String uri;

    public RedditFeedAuthor() {}

    public RedditFeedAuthor(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
