package pl.edu.agh.rssviewer.service.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

abstract class IFeedDateConverter {

    Date parseFromUtcString(String utcDateString) throws ParseException {
        String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        return new SimpleDateFormat(dateTimeFormat, Locale.getDefault(Locale.Category.FORMAT)).parse(utcDateString);
    }

    Date getUtcNow() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    abstract Date convertToUtc(String dateString);
}

