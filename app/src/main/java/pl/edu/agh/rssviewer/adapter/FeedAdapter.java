package pl.edu.agh.rssviewer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.agh.rssviewer.background.IconDownloaderTask;
import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.rss.Feed;
import pl.edu.agh.rssviewer.service.html.FeedParser;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private List<Feed> data;
    private Context context;

    private int selectedPosition;

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView titleTextView;
        TextView dateTextView;
        TextView contentTextView;
        ImageView iconImageView;
        ImageView imageImageView;

        FeedViewHolder(View v) {
            super(v);
            view = v;
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.item_date);
            contentTextView = itemView.findViewById(R.id.item_content);
            iconImageView = itemView.findViewById(R.id.item_icon);
            imageImageView = itemView.findViewById(R.id.item_image);
        }
    }

    public FeedAdapter(List<Feed> data) {
        this.data = data;
        this.selectedPosition = -1;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        if (context == null) {
            context = v.getContext();
        }
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, final int position) {
        Feed feed = data.get(position);

        holder.iconImageView.setImageDrawable(null);
        new IconDownloaderTask(holder.iconImageView).execute(feed.getFeedType());

        holder.titleTextView.setText(feed.getTitle());
        holder.dateTextView.setText(feed.getFormattedDate(context));

        new FeedParser(holder.contentTextView, holder.imageImageView, true).parseContent(feed);

        holder.itemView.setBackgroundColor(Color.WHITE);
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(0xfafafa);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;

        notifyItemChanged(previousPosition);
        notifyItemChanged(position);
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Feed> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
