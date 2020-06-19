package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "link", strict = false)
public class RedditFeedLink {
    @Attribute(required = false)
    private String rel;
    @Attribute
    private String href;

    public RedditFeedLink() {}

    public RedditFeedLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
