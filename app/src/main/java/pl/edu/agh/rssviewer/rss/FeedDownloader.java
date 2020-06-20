package pl.edu.agh.rssviewer.rss;

import android.os.AsyncTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

abstract class FeedDownloader<T> extends AsyncTask<String, Void, T> {
    private static final Pattern pattern = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+");

    T downloadFeed(String urlString, Class<T> clazz) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() != 200) {
                return null;
            }

            Serializer serializer = new Persister();

            return serializer.read(clazz, escapeInvalidXmlCharacters(connection));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String escapeInvalidXmlCharacters(HttpURLConnection connection) throws IOException {
        BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
        byte[] buffer = new byte[1024];

        int bytesRead;
        StringBuilder data = new StringBuilder();
        while((bytesRead = in.read(buffer)) != -1) {
            data.append(new String(buffer, 0, bytesRead));
        }
        return pattern.matcher(data.toString()).replaceAll("");
    }
}
