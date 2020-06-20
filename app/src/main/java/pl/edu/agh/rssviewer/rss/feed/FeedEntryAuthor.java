package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "author", strict = false)
public class FeedEntryAuthor {
    @Element
    private String name;
    @Element
    private String uri;

    public FeedEntryAuthor() {}

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
