package pl.edu.agh.rssviewer.adapter;

import java.text.ParseException;
import java.util.Date;

public class DefaultFeedDateConverter extends IFeedDateConverter {
    @Override
    Date convertToUtc(String dateString) {
        try {
            return parseFromUtcString(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getUtcNow();
    }
}
