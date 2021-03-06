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

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;

    public ImageDownloaderTask(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
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
                Bitmap upScaledBitmap = Bitmap.createScaledBitmap(bitmap, 1024, 1024, true);
                imageView.setImageBitmap(upScaledBitmap);
            } else {
                imageView.setImageDrawable(null);
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        final Uri imageUri = Uri.parse(url);

        try
        {
            URLConnection conn = new URL(imageUri.toString()).openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 8192);
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            return null;
        }
    }
}
