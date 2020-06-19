package pl.edu.agh.rssviewer.adapter;

import android.content.Context;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

public class Feed implements Serializable {
    private String title;
    private String content;
    private String date;
    private String author;

//    private IFeedDateConverter feedDateConverter;

    public Feed(String title, String content, String date, String author) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.author = author;
//        this.feedDateConverter = feedDateConverter;
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
        final DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        final DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        try {
            Date date = new SimpleDateFormat(dateTimeFormat, Locale.getDefault(Locale.Category.FORMAT)).parse(this.date);
            assert date != null;
            return timeFormat.format(date) + ", " + dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Date date = feedDateConverter.convertToUtc(this.date);
        return DateFormat.getDateTimeInstance().format(this.date);
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
}
