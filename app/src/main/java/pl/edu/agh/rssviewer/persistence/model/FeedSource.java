package pl.edu.agh.rssviewer.persistence.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pl.edu.agh.rssviewer.persistence.dao.FeedSourceDaoImpl;

@DatabaseTable(tableName = "feedSources", daoClass = FeedSourceDaoImpl.class)
public class FeedSource {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String url;

    public FeedSource() {}

    public FeedSource(String url) {
        this.url = url;
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
}
