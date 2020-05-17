package pl.edu.agh.rssviewer.ui.home;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutionException;

import pl.edu.agh.rssviewer.rss.RedditFeedDownloader;
import pl.edu.agh.rssviewer.rss.StackOverflowFeedDownloader;
import pl.edu.agh.rssviewer.rss.feed.RedditFeed;
import pl.edu.agh.rssviewer.rss.feed.StackOverflowFeed;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> string;

    LiveData<String> getData() {
        if (string == null) {
            string = new MutableLiveData<>();
            loadData();
        }
        return string;
    }

    private void loadData() {
        RedditFeed redditFeed;
        StackOverflowFeed stackOverflowFeed;
        try {
            RedditFeedDownloader feedDownloader = new RedditFeedDownloader();
            redditFeed = feedDownloader.execute("https://www.reddit.com/r/WTF/.rss").get();
            StackOverflowFeedDownloader feedDownloader2 = new StackOverflowFeedDownloader();
            stackOverflowFeed = feedDownloader2.execute("https://stackoverflow.com/feeds/").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        string.setValue("Home");

        final Handler handler = new Handler();
        handler.postDelayed(() -> string.setValue("Home Changed"), 2000);

    }
}
