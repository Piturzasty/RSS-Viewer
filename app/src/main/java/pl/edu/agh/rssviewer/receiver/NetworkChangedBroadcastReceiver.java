package pl.edu.agh.rssviewer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public class NetworkChangedBroadcastReceiver extends BroadcastReceiver {
    View view;

    public NetworkChangedBroadcastReceiver(Context context, View view) {
        this.view = view;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        cm.registerDefaultNetworkCallback(new NetworkChangedCallback(view));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }

    private class NetworkChangedCallback extends NetworkCallback {
        View view;

        NetworkChangedCallback(View view) {
            this.view = view;
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            Snackbar.make(view, "Network is available", 3000).show();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);

            Snackbar.make(view, "Network is unavailable", 3000).show();
        }
    }
}
