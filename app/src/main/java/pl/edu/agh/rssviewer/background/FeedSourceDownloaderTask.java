package pl.edu.agh.rssviewer.background;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.edu.agh.rssviewer.adapter.FeedSourceAdapter;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;

public class FeedSourceDownloaderTask extends AsyncTask<Void, Void, List<FeedSource>> {
    private final WeakReference<FeedSourceAdapter> feedAdapterWeakReference;
    private FeedRepository feedRepository;
    private FeedSourceRepository feedSourceRepository;

    public FeedSourceDownloaderTask(FeedSourceAdapter feedAdapter, FeedRepository feedRepository, FeedSourceRepository feedSourceRepository) {
        this.feedAdapterWeakReference = new WeakReference<>(feedAdapter);
        this.feedRepository = feedRepository;
        this.feedSourceRepository = feedSourceRepository;
    }

    @Override
    protected List<FeedSource> doInBackground(Void... voids) {
        List<FeedSource> feedSources = feedSourceRepository.findAll();
        for (FeedSource feedSource : feedSources) {
            feedSource.setFeedCount(feedRepository.findByCategory(feedSource.getCategory()).size());
        }
        return feedSources;
    }

    @Override
    protected void onPostExecute(List<FeedSource> feedSources) {
        FeedSourceAdapter feedAdapter = feedAdapterWeakReference.get();
        if (feedAdapter != null) {
            feedAdapter.clear();
            feedAdapter.addAll(feedSources);
        }
    }
}
