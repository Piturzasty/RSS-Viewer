package pl.edu.agh.rssviewer.rss.feed.reddit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "category", strict = false)
public class RedditFeedCategory {
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
