package pl.edu.agh.rssviewer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

public class NetworkChangedBroadcastReceiver extends BroadcastReceiver {
    View view;

    public NetworkChangedBroadcastReceiver(Context context, View view) {
        this.view = view;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        cm.registerDefaultNetworkCallback(new NetworkChangedCallback(context, view));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }

    private class NetworkChangedCallback extends NetworkCallback {
        private Context context;
        private View view;

        NetworkChangedCallback(Context context, View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean allowInternetCheck = sharedPreferences.getBoolean("allow_internet_check", false);
            if (allowInternetCheck) {
                Snackbar.make(view, "You are online", 3000).show();
            }
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean allowInternetCheck = sharedPreferences.getBoolean("allow_internet_check", false);
            if (allowInternetCheck) {
                Snackbar.make(view, "You are offline", 3000).show();
            }
        }
    }
}
