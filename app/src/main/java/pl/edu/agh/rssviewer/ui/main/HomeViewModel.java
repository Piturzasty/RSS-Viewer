package pl.edu.agh.rssviewer.ui.main;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> string;

    LiveData<String> getData() {
        if (string == null) {
            string = new MutableLiveData<>();
            loadData();
        }
        return string;
    }

    private void loadData() {
        string.setValue("Home");

        final Handler handler = new Handler();
        handler.postDelayed(() -> string.setValue("Home Changed"), 2000);

    }
}
