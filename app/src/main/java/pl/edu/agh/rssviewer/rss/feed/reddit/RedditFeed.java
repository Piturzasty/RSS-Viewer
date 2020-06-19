package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class RedditFeed {
    @Element(name = "category")
    private RedditFeedCategory category;
    @Element
    private String updated;
    @Element
    private String icon;
    @Element
    private String id;
    @ElementList(inline = true, entry = "link")
    private List<RedditFeedLink> link;
    @Element
    private String logo;
    @Element
    private String subtitle;
    @Element
    private String title;
    @ElementList(inline = true, entry = "entry")
    private List<RedditEntry> entries;

    public RedditFeed() {}

    public RedditFeed(RedditFeedCategory category, String updated, String icon, String id, List<RedditFeedLink> link, String logo, String subtitle, String title, List<RedditEntry> entries) {
        this.category = category;
        this.updated = updated;
        this.icon = icon;
        this.id = id;
        this.link = link;
        this.logo = logo;
        this.subtitle = subtitle;
        this.title = title;
        this.entries = entries;
    }

    public RedditFeedCategory getCategory() {
        return category;
    }

    public void setCategory(RedditFeedCategory category) {
        this.category = category;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RedditFeedLink> getLink() {
        return link;
    }

    public void setLink(List<RedditFeedLink> link) {
        this.link = link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RedditEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RedditEntry> entries) {
        this.entries = entries;
    }
}