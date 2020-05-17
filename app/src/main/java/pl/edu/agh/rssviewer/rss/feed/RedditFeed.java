package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "category", strict = false)
class RedditFeedCategory {
    @Attribute
    private String term;

    public RedditFeedCategory() {}

    public RedditFeedCategory(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}

@Root(name = "link", strict = false)
class RedditFeedLink {
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

@Root(name = "author", strict = false)
class RedditFeedAuthor {
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

@Root(name = "entry", strict = false)
class RedditEntry {
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