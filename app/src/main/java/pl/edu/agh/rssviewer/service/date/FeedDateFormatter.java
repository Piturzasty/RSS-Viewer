package pl.edu.agh.rssviewer.service.date;

import android.content.Context;

import java.text.DateFormat;
import java.util.Date;

import pl.edu.agh.rssviewer.rss.FeedType;

public class FeedDateFormatter {
    private static IFeedDateConverter redditDateFeedConverter;
    private static IFeedDateConverter stackOverflowDateFeedConverter;

    public static String getFormattedDate(String dateString, FeedType feedType, Context context) {
        final DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
        final DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

        Date date = getFeedDateConverter(feedType).convertToUtc(dateString);
        return timeFormat.format(date) + ", " + dateFormat.format(date);
    }

    private static IFeedDateConverter getFeedDateConverter(FeedType feedType) {
        switch (feedType) {
            case Reddit:
                if (redditDateFeedConverter == null) {
                    redditDateFeedConverter = new RedditFeedDateConverter();
                }
                return redditDateFeedConverter;
            case StackOverflow:
                if (stackOverflowDateFeedConverter == null) {
                    stackOverflowDateFeedConverter = new DefaultFeedDateConverter();
                }
                return stackOverflowDateFeedConverter;
            default:
                return new DefaultFeedDateConverter();
        }
    }
}
