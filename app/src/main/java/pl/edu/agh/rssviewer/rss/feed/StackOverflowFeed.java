package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "category", strict = false)
class StackOverflowFeedCategory {
    @Attribute
    private String term;

    public StackOverflowFeedCategory() {}

    public StackOverflowFeedCategory(String term) {
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
class StackOverflowFeedLink {
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

@Root(name = "author", strict = false)
class StackOverflowFeedAuthor {
    @Element
    private String name;
    @Element
    private String uri;

    public StackOverflowFeedAuthor(){}

    public StackOverflowFeedAuthor(String name, String uri) {
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
class StackOverflowEntry {
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