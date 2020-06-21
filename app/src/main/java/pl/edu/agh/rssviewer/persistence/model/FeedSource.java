package pl.edu.agh.rssviewer.persistence.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pl.edu.agh.rssviewer.persistence.dao.FeedSourceDaoImpl;
import pl.edu.agh.rssviewer.rss.FeedType;

@DatabaseTable(tableName = "feedSources", daoClass = FeedSourceDaoImpl.class)
public class FeedSource {
    public static final String URL_NAME = "url";

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String url;
    @DatabaseField(canBeNull = false)
    private String category;
    @DatabaseField(canBeNull = false)
    private FeedType type;

    private int feedCount;

    public FeedSource() {}

    public FeedSource(String url, String category, FeedType type) {
        this.url = url;
        this.category = category;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public FeedType getType() {
        return type;
    }

    public void setType(FeedType type) {
        this.type = type;
    }

    public int getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(int feedCount) {
        this.feedCount = feedCount;
    }
}
