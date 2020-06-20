package pl.edu.agh.rssviewer.ui.details;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.adapter.Feed;
import pl.edu.agh.rssviewer.background.IconDownloaderTask;
import pl.edu.agh.rssviewer.background.ImageDownloaderTask;

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
        }

        return view;
    }

    private void fillViewWithData(View view, int orientation, Feed feed) {
        new IconDownloaderTask(iconImageView).execute("https://www.reddit.com/");

        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            titleTextView.setText(feed.getTitle());
        }

        parseFeedContent(feed);

        StringBuilder additionalInfo = new StringBuilder("Posted by: " + feed.getAuthor());
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            additionalInfo.append(",\n").append(feed.getFormattedDate(view.getContext()));
        }
        additionalInfoTextView.setText(additionalInfo.toString());
    }

    private void parseFeedContent(Feed feed) {
        Document document = Jsoup.parse(feed.getContent());
        switch (feed.getFeedType()) {
            case Reddit: {
                parseRedditFeedContent(feed, document);
                break;
            }
            case StackOverflow:
                contentTextView.setText(Html.fromHtml(feed.getContent(), Html.FROM_HTML_MODE_COMPACT));
                break;
        }
    }

    private void parseRedditFeedContent(Feed feed, Document document) {
        Elements elements = document.getElementsByClass("md");
        if (elements.size() == 1) { // reddit text
            imageImageView.setVisibility(View.GONE);
            imageImageView.setImageDrawable(null);
            contentTextView.setText(Html.fromHtml(elements.get(0).childNode(0).outerHtml(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            elements = document.getElementsByTag("img");
            if (elements.size() == 1) { // reddit image
                contentTextView.setVisibility(View.GONE);
                contentTextView.setText(null);
                new ImageDownloaderTask(imageImageView).execute(elements.attr("src"));
            } else { // unrecognized
                imageImageView.setVisibility(View.GONE);
                contentTextView.setText(Html.fromHtml(feed.getContent(), Html.FROM_HTML_MODE_COMPACT));
            }
        }
    }
}
