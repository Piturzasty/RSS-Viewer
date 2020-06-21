package pl.edu.agh.rssviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.rssviewer.R;
import pl.edu.agh.rssviewer.background.IconDownloaderTask;
import pl.edu.agh.rssviewer.persistence.model.FeedSource;

public class FeedSourceAdapter extends RecyclerView.Adapter<FeedSourceAdapter.FeedViewHolder> {
    private List<FeedSource> data = new ArrayList<>();
    private Context context;
    private OnButtonInteractionListener listener;

    public interface OnButtonInteractionListener {
        void onClickRemove(FeedSource feedSource);
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView iconImageView;
        TextView titleTextView;
        TextView typeTextView;
        Button removeButton;
        TextView countTextView;

        FeedViewHolder(View v) {
            super(v);
            view = v;
            iconImageView = view.findViewById(R.id.feed_source_icon);
            titleTextView = view.findViewById(R.id.feed_source_title);
            typeTextView = view.findViewById(R.id.feed_source_type);
            removeButton = view.findViewById(R.id.feed_source_button);
            countTextView = view.findViewById(R.id.feed_source_count);
        }
    }

    public FeedSourceAdapter(OnButtonInteractionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_source, parent, false);
        if (context == null) {
            context = v.getContext();
        }
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, final int position) {
        FeedSource feedSource = data.get(position);

        holder.iconImageView.setImageDrawable(null);
        new IconDownloaderTask(holder.iconImageView).execute(feedSource.getType());
        holder.titleTextView.setText(feedSource.getUrl());
        holder.typeTextView.setText(feedSource.getType().toString());
        holder.removeButton.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClickRemove(feedSource);
            }
        });
        holder.countTextView.setText(String.valueOf(feedSource.getFeedCount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<FeedSource> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
