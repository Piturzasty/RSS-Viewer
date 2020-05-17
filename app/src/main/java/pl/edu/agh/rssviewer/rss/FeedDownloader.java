package pl.edu.agh.rssviewer.rss;

import android.os.AsyncTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.net.HttpURLConnection;
import java.net.URL;

abstract class FeedDownloader<T> extends AsyncTask<String, Void, T> {
    T downloadFeed(String urlString, Class<T> clazz) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                return null;
            }

            Serializer serializer = new Persister();

            return serializer.read(clazz, connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
