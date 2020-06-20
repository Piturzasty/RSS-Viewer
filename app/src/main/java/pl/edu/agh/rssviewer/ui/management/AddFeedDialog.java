package pl.edu.agh.rssviewer.ui.management;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import pl.edu.agh.rssviewer.R;

public class AddFeedDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentActivity a = getActivity();
        assert a != null;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setView(inflater.inflate(R.layout.dialog_add_feed, null))
                .setMessage("test")
                .setPositiveButton("Add", (dialog, id) -> {
                    // Send the positive button event back to the host activity
                    // listener.onDialogPositiveClick(AddFeedDialog.this);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // Send the negative button event back to the host activity
                    // listener.onDialogNegativeClick(AddFeedDialog.this);
                });
        return builder.create();
    }
}
