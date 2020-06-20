package pl.edu.agh.rssviewer.background;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

import pl.edu.agh.rssviewer.rss.FeedType;

public class IconDownloaderTask extends AsyncTask<FeedType, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public IconDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(FeedType... params) {
        if (params.length != 1) {
            return null;
        }
        return downloadBitmap(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageDrawable(null);
            }
        }
    }

    private Bitmap downloadBitmap(FeedType feedType) {
        final Uri iconUri = getUrlFromFeed(feedType).buildUpon().path("favicon.ico").build();

        try
        {
            URLConnection conn = new URL(iconUri.toString()).openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 8192);
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            return null;
        }
    }

    private Uri getUrlFromFeed(FeedType feedType) {
        switch (feedType){
            case Reddit:
                return Uri.parse("https://www.reddit.com/");
            case StackOverflow:
                return Uri.parse("https://stackoverflow.com/");
        }
        return Uri.parse("https://www.agh.edu.pl/");
    }
}
