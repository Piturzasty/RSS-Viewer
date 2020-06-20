package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class FeedBase {
    @Element
    protected String id;
    @Element
    protected String title;
    @Element
    protected String subtitle;
    @Element
    protected String updated;
    @ElementList(inline = true, entry = "link")
    protected List<FeedLink> links;

    public FeedBase() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<FeedLink> getLinks() {
        return links;
    }

    public void setLinks(List<FeedLink> links) {
        this.links = links;
    }
}
