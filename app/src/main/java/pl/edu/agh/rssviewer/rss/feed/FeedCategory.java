package pl.edu.agh.rssviewer.rss.feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category", strict = false)
public class FeedCategory {
    @Attribute
    private String term;

    public FeedCategory() {}

    public FeedCategory(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
