package pl.edu.agh.rssviewer.persistence.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

import pl.edu.agh.rssviewer.persistence.dao.FeedDaoImpl;
import pl.edu.agh.rssviewer.rss.FeedType;
import pl.edu.agh.rssviewer.service.date.FeedDateFormatter;

@DatabaseTable(tableName = "feeds", daoClass = FeedDaoImpl.class)
public class Feed implements Serializable, Comparable<Feed> {
    public static final String CATEGORY_NAME = "category";

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String externalId;
    @DatabaseField(canBeNull = false)
    private String url;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField(canBeNull = false)
    private String date;
    @DatabaseField(canBeNull = false)
    private String content;
    @DatabaseField(canBeNull = false)
    private String author;
    @DatabaseField(canBeNull = false)
    private String category;
    @DatabaseField(canBeNull = false)
    private FeedType type;

    public Feed() {
    }

    public Feed(String externalId, String url, String title, String date, String content, String author, String category, FeedType type) {
        this.externalId = externalId;
        this.url = url;
        this.title = title;
        this.date = date;
        this.content = content;
        this.author = author;
        this.category = category;
        this.type = type;
    }

    public String getFormattedDate(Context context) {
        return FeedDateFormatter.getFormattedDate(date, type, context);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    @Override
    public int compareTo(Feed other) {
        Date d1 = FeedDateFormatter.getDate(date, type);
        Date d2 = FeedDateFormatter.getDate(other.getDate(), other.getType());
        return d2.compareTo(d1);
    }
}
