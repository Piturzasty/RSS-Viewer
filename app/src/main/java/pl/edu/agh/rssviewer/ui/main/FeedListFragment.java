package pl.edu.agh.rssviewer.ui.main;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.adapter.FeedAdapter;
import pl.edu.agh.rssviewer.background.FeedDownloaderTask;
import pl.edu.agh.rssviewer.listeners.RecyclerItemClickListener;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.persistence.repository.FeedRepository;
import pl.edu.agh.rssviewer.persistence.repository.FeedSourceRepository;
import pl.edu.agh.rssviewer.rss.RedditFeedDownloader;

public class FeedListFragment extends DaggerFragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private final static List<Feed> data = new ArrayList<>();

    private OnListFragmentInteractionListener listener;

    @Inject
    FeedSourceRepository feedSourceRepository;

    @Inject
    FeedRepository feedRepository;

    public FeedListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_list, container, false);

        final FeedAdapter feedAdapter = setupRecyclerView(view);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        if (savedInstanceState == null) {
            new FeedDownloaderTask(feedAdapter, swipeRefreshLayout, feedRepository, feedSourceRepository).execute();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> new RedditFeedDownloader(feedAdapter, swipeRefreshLayout, feedRepository).execute("https://www.reddit.com/r/WTF/.rss"));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        return view;
    }

    private FeedAdapter setupRecyclerView(View view) {
        Context context = view.getContext();
        final RecyclerView recyclerView = view.findViewById(R.id.feed_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final FeedAdapter feedAdapter = new FeedAdapter(data);
        recyclerView.setAdapter(feedAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(context.getDrawable(R.drawable.divider)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Context context) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    feedAdapter.setSelectedPosition(position);
                }
                Feed feed = data.get(position);
                if (listener != null) {
                    listener.onListFragmentInteraction(feed);
                }
            }

            @Override
            public void onLongItemClick(View view, int position, Context context) {
            }
        }));
        return feedAdapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Feed item);
    }
}
