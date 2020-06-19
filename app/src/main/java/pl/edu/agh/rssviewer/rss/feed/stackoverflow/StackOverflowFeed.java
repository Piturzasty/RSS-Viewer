package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class StackOverflowFeed {
    @Element
    private String title;
    @ElementList(inline = true, entry = "link")
    private List<StackOverflowFeedLink> links;
    @Element
    private String subtitle;
    @Element
    private String updated;
    @Element
    private String id;
    @ElementList(inline = true, entry = "entry")
    private List<StackOverflowEntry> entries;

    public StackOverflowFeed() {}

    public StackOverflowFeed(String title, List<StackOverflowFeedLink> links, String subtitle, String updated, String id, List<StackOverflowEntry> entries) {
        this.title = title;
        this.links = links;
        this.subtitle = subtitle;
        this.updated = updated;
        this.id = id;
        this.entries = entries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StackOverflowFeedLink> getLinks() {
        return links;
    }

    public void setLinks(List<StackOverflowFeedLink> links) {
        this.links = links;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StackOverflowEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StackOverflowEntry> entries) {
        this.entries = entries;
    }
}