package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import pl.edu.agh.rssviewer.rss.feed.FeedCategory;
import pl.edu.agh.rssviewer.rss.feed.FeedEntry;

@Root(name = "entry", strict = false)
public class StackOverflowEntry extends FeedEntry {
    @ElementList(inline = true, entry = "category")
    private List<FeedCategory> categories;
    @Element
    private String published;
    @Element
    private String summary;

    public StackOverflowEntry() {}

    public List<FeedCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FeedCategory> categories) {
        this.categories = categories;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
