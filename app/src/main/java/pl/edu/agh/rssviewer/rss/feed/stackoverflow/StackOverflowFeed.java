package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import pl.edu.agh.rssviewer.rss.feed.FeedBase;

@Root(name = "feed", strict = false)
public class StackOverflowFeed extends FeedBase {
    @ElementList(inline = true, entry = "entry")
    private List<StackOverflowEntry> entries;

    public StackOverflowFeed() {}

    public List<StackOverflowEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StackOverflowEntry> entries) {
        this.entries = entries;
    }
}