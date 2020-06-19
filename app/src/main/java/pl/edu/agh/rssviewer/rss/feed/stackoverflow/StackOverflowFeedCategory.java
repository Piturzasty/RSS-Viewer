package pl.edu.agh.rssviewer.rss.feed.stackoverflow;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category", strict = false)
public class StackOverflowFeedCategory {
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
