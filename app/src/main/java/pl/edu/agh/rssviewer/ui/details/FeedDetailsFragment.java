package pl.edu.agh.rssviewer.ui.details;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.adapter.Feed;
import pl.edu.agh.rssviewer.background.ImageDownloaderTask;

public class FeedDetailsFragment extends Fragment {
    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView additionalInfoTextView;
    private TextView contentTextView;

    public FeedDetailsFragment() {
    }

    public static FeedDetailsFragment newInstance(Feed feed) {
        FeedDetailsFragment feedDetails = new FeedDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("feed", feed);
        feedDetails.setArguments(args);
        return feedDetails;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.feed_details_fragment, container, false);

        iconImageView = v.findViewById(R.id.details_image_view);
        titleTextView = v.findViewById(R.id.details_title);
        additionalInfoTextView = v.findViewById(R.id.details_additional_info);
        contentTextView = v.findViewById(R.id.details_full_content);

        new ImageDownloaderTask(iconImageView).execute("https://www.reddit.com/");

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            titleTextView.setVisibility(View.GONE);
        }

        Bundle args = this.getArguments();
        if (args != null) {
            Feed feed = (Feed) args.getSerializable("feed");
            assert feed != null;
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                titleTextView.setText(feed.getTitle());
            } else {
                titleTextView.setText(null);
            }

            contentTextView.setText(feed.getContent());

            StringBuilder additionalInfo = new StringBuilder("Posted by: " + feed.getAuthor());
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                additionalInfo.append(",\n").append(feed.getFormattedDate(v.getContext()));
            }
            additionalInfoTextView.setText(additionalInfo.toString());
        } else {
            titleTextView.setText("No title");
            contentTextView.setText("No content");
        }

        return v;
    }
}
