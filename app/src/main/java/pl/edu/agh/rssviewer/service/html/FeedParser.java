package pl.edu.agh.rssviewer.service.html;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pl.edu.agh.rssviewer.background.ImageDownloaderTask;
import pl.edu.agh.rssviewer.persistence.model.Feed;

public class FeedParser {
    private final String imageText = "Image";
    private final String unrecognizedText = "Unrecognized";

    private final TextView textView;
    private final ImageView imageView;
    private final ConstraintLayout imageViewWrapper;
    private boolean lite;

    private int maximumTextLength;

    public FeedParser(TextView textView, ImageView imageView, boolean lite) {
        this.textView = textView;
        this.imageView = imageView;
        this.imageViewWrapper = (ConstraintLayout) imageView.getParent();
        this.lite = lite;

        this.maximumTextLength = 150;
    }

    public FeedParser(TextView textView, ImageView imageView) {
        this(textView, imageView, false);
    }

    public FeedParser setMaximumTextLength(int maximumTextLength) {
        this.maximumTextLength = maximumTextLength;
        return this;
    }

    public void parseContent(Feed feed) {
        Document document = Jsoup.parse(feed.getContent());
        switch (feed.getType()) {
            case Reddit: {
                parseRedditFeedContent(feed, document);
                break;
            }
            case StackOverflow:
                parseStackOverflowFeedContent(feed, document);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void parseRedditFeedContent(Feed feed, Document document) {
        Elements elements = document.getElementsByClass("md");
        if (elements.size() == 1) { // reddit text
            showDefaultFormattedHtmlTextOnly(feed, document);
        } else {
            elements = document.getElementsByTag("img");
            if (elements.size() == 1) { // reddit image
                if (!lite) {
                    hideTextView();
                    new ImageDownloaderTask(imageView).execute(elements.attr("src"));
                } else {
                    hideImageView();
                    textView.setText(imageText);
                }
            } else { // unrecognized
                if (lite) {
                    hideImageView();
                    textView.setText(unrecognizedText);
                } else {
                    showDefaultFormattedHtmlTextOnly(feed, document);
                }
            }
        }
    }

    private void parseStackOverflowFeedContent(Feed feed, Document document) {
        showDefaultFormattedHtmlTextOnly(feed, document);
    }

    private void showDefaultFormattedHtmlTextOnly(Feed feed, Document document) {
        hideImageView();
        if (lite) {
            Elements p = document.getElementsByTag("p");
            if (p.size() > 0) {
                String content = convertToHtml(p.get(0).outerHtml())
                        .toString()
                        .replace("\n", "");

                textView.setText(truncateIfNecessary(content));
            } else {
                textView.setText(unrecognizedText);
            }
        } else {
            textView.setText(convertToHtml(feed.getContent()));
        }
    }

    private Spanned convertToHtml(String htmlContent) {
        return Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY);
    }

    private String truncateIfNecessary(String content) {
        if (content.length() > maximumTextLength) {
            content = content.substring(0, maximumTextLength);
            int lastDot = content.lastIndexOf('.');
            if (lastDot != -1) {
                content = content.substring(0, lastDot + 1) + "...";
            }
        }
        return content;
    }

    private void hideTextView() {
        textView.setVisibility(View.GONE);
        textView.setText(null);
    }

    private void hideImageView() {
        imageViewWrapper.setVisibility(View.GONE);
        imageView.setImageDrawable(null);
    }
}
