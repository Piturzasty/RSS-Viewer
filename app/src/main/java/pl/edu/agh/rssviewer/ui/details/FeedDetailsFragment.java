package pl.edu.agh.rssviewer.ui.details;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.background.IconDownloaderTask;
import pl.edu.agh.rssviewer.persistence.model.Feed;
import pl.edu.agh.rssviewer.service.html.FeedParser;

public class FeedDetailsFragment extends Fragment {
    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView additionalInfoTextView;
    private TextView contentTextView;

    private ImageView imageImageView;

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
        final View view = inflater.inflate(R.layout.feed_details_fragment, container, false);

        iconImageView = view.findViewById(R.id.details_image_view);
        titleTextView = view.findViewById(R.id.details_title);
        additionalInfoTextView = view.findViewById(R.id.details_additional_info);
        contentTextView = view.findViewById(R.id.details_full_content);
        imageImageView = view.findViewById(R.id.details_image_view_2);

        Button readMoreButton = view.findViewById(R.id.details_button_more);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            titleTextView.setVisibility(View.GONE);
            titleTextView.setText(null);
        }

        Bundle args = this.getArguments();
        if (args != null) {
            Feed feed = (Feed) args.getSerializable("feed");
            assert feed != null;
            fillViewWithData(view, orientation, feed);

            readMoreButton.setOnClickListener(v -> onReadMoreClick(feed));
        }

        return view;
    }

    private void onReadMoreClick(Feed feed) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(feed.getUrl())));
    }

    private void fillViewWithData(View view, int orientation, Feed feed) {
        iconImageView.setImageDrawable(null);
        new IconDownloaderTask(iconImageView).execute(feed.getType());

        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            titleTextView.setText(feed.getTitle());
        }

        new FeedParser(contentTextView, imageImageView).parseContent(feed);

        StringBuilder additionalInfo = new StringBuilder("Posted by: " + feed.getAuthor());
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            additionalInfo.append(",\n").append(feed.getFormattedDate(view.getContext()));
        }
        additionalInfoTextView.setText(additionalInfo.toString());
    }
}
