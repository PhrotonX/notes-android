package com.phroton.notes.ui.editor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.R;

public class ColorDialogFragment extends DialogFragment {
    public static final String EXTRA_COLOR_ID = "ColorDialogFragment.EXTRA_COLOR_ID";
    public static final String REQUEST_COLOR_UPDATED = "ColorDialogFragment.REQUEST_COLOR_UPDATED";
    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_color, container);
    }*/

    private int m_value;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        RadioGroup view = (RadioGroup)inflater.inflate(R.layout.dialog_color, null);
        view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_COLOR_ID, checkedId);
                m_value = checkedId;
                getParentFragmentManager().setFragmentResult(REQUEST_COLOR_UPDATED, bundle);
                dismiss();
            }
        });

        //Toast.makeText(getContext(), "ColorDialogFragment -  Checked Value: " + m_value, Toast.LENGTH_SHORT).show();

        return new AlertDialog.Builder(getActivity())
                .setTitle(getContext().getResources().getString(R.string.menu_color))
                .setView(view)
                .setNegativeButton(getContext().getResources().getString(R.string.cancel), ((dialog, which) -> {}))
                .create();
        //builder.setView();

    }
}
