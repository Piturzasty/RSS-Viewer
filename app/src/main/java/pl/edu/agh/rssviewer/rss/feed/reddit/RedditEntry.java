package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "entry", strict = false)
public class RedditEntry {
    @Element
    private RedditFeedAuthor author;
    @Element
    private String content;
    @Element
    private String id;
    @Element
    private RedditFeedLink link;
    @Element
    private String updated;
    @Element
    private String title;

    public RedditEntry() {}

    public RedditEntry(RedditFeedAuthor author, String content, String id, RedditFeedLink link, String updated, String title) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.link = link;
        this.updated = updated;
        this.title = title;
    }

    public RedditFeedAuthor getAuthor() {
        return author;
    }

    public void setAuthor(RedditFeedAuthor author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RedditFeedLink getLink() {
        return link;
    }

    public void setLink(RedditFeedLink link) {
        this.link = link;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
