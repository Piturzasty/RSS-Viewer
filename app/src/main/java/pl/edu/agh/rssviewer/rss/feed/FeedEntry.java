package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Element;

public class FeedEntry {
    @Element
    protected String id;
    @Element
    protected String title;
    @Element
    protected String updated;
    @Element
    protected FeedLink link;
    @Element
    private FeedEntryAuthor author;

    public FeedEntry() {}

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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public FeedLink getLink() {
        return link;
    }

    public void setLink(FeedLink link) {
        this.link = link;
    }

    public FeedEntryAuthor getAuthor() {
        return author;
    }

    public void setAuthor(FeedEntryAuthor author) {
        this.author = author;
    }
}
