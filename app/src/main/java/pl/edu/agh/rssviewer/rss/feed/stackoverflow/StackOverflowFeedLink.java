package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "link", strict = false)
public class StackOverflowFeedLink {
    @Attribute
    private String rel;
    @Attribute
    private String href;

    public StackOverflowFeedLink() {}

    public StackOverflowFeedLink(String rel, String href) {
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
