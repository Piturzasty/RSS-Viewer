package pl.edu.agh.rssviewer.adapter;

import android.content.Context;

import java.io.Serializable;

public class Feed implements Serializable {
    private String title;
    private String content;
    private String date;
    private String author;
    private FeedType feedType;

    public Feed(String title, String content, String date, String author, FeedType feedType) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
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

    public FeedType getFeedType() {
        return feedType;
    }

    public void setFeedType(FeedType feedType) {
        this.feedType = feedType;
    }
}
