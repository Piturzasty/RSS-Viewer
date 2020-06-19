package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "entry", strict = false)
public class StackOverflowEntry {
    @Element
    private String id;
    @Element
    private String title;
    @ElementList(inline = true, entry = "category")
    private List<StackOverflowFeedCategory> categories;
    @Element
    private StackOverflowFeedAuthor author;
    @Element
    private StackOverflowFeedLink link;
    @Element
    private String published;
    @Element
    private String updated;
    @Element
    private String summary;

    public StackOverflowEntry() {}

    public StackOverflowEntry(String id, String title, List<StackOverflowFeedCategory> categories, StackOverflowFeedAuthor author, StackOverflowFeedLink link, String published, String updated, String summary) {
        this.id = id;
        this.title = title;
        this.categories = categories;
        this.author = author;
        this.link = link;
        this.published = published;
        this.updated = updated;
        this.summary = summary;
    }

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

    public List<StackOverflowFeedCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<StackOverflowFeedCategory> categories) {
        this.categories = categories;
    }

    public StackOverflowFeedAuthor getAuthor() {
        return author;
    }

    public void setAuthor(StackOverflowFeedAuthor author) {
        this.author = author;
    }

    public StackOverflowFeedLink getLink() {
        return link;
    }

    public void setLink(StackOverflowFeedLink link) {
        this.link = link;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
