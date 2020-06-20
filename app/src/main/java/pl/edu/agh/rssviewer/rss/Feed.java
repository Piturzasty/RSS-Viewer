package pl.edu.agh.rssviewer.rss;

import android.content.Context;

import java.io.Serializable;

import pl.edu.agh.rssviewer.service.date.FeedDateFormatter;

public class Feed implements Serializable {
    private String title;
    private String content;
    private String date;
    private String author;
    private String link;
    private FeedType feedType;

    public Feed(String title, String content, String date, String author, String link, FeedType feedType) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
        this.link = link;
        this.feedType = feedType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate(Context context) {
        return FeedDateFormatter.getFormattedDate(date, feedType, context);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public FeedType getFeedType() {
        return feedType;
    }

    public void setFeedType(FeedType feedType) {
        this.feedType = feedType;
    }
}
