package pl.edu.agh.rssviewer.adapter;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RedditFeedDateConverter extends IFeedDateConverter {
    @Override
    public Date convertToUtc(String dateString) {
        String utcString = OffsetDateTime.parse(dateString).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        try {
            return parseFromUtcString(utcString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getUtcNow();
    }
}
