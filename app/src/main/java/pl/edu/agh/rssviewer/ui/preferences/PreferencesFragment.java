package pl.edu.agh.rssviewer.ui.preferences;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import pl.edu.agh.rssviewer.R;

public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
