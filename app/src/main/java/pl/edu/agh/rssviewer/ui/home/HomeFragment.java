package pl.edu.agh.rssviewer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import pl.edu.agh.rssviewer.R;

public class HomeFragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.text_home);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        viewModel.getData().observe(this, s -> textView.setText(s));
    }

}
