package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import pl.edu.agh.rssviewer.rss.feed.FeedBase;
import pl.edu.agh.rssviewer.rss.feed.FeedCategory;

@Root(name = "feed", strict = false)
public class RedditFeed extends FeedBase {
    @Element(name = "category")
    private FeedCategory category;
    @Element
    private String icon;

    @Element
    private String logo;

    @ElementList(inline = true, entry = "entry")
    private List<RedditEntry> entries;

    public RedditFeed() {}

    public FeedCategory getCategory() {
        return category;
    }

    public void setCategory(FeedCategory category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<RedditEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<RedditEntry> entries) {
        this.entries = entries;
    }
}