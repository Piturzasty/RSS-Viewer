package pl.edu.agh.rssviewer.ui.management;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import pl.edu.agh.rssviewer.R;

public class AddFeedDialog extends DialogFragment {

    private OnDialogInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentActivity a = getActivity();
        assert a != null;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_feed, null);
        final EditText editText = view.findViewById(R.id.feed_url);
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setView(view)
                .setPositiveButton("Add", (dialog, id) -> {
                    if (listener != null) {
                        listener.onAddButtonClick(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogInteractionListener) {
            listener = (OnDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnDialogInteractionListener {
        void onAddButtonClick(String url);
    }
}
