package com.example.application.controller.awaluser;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.application.R;

import java.util.ArrayList;


public class WisataBuatan extends DialogFragment {

    public interface onMultiChoiceListener {
        void onPositiveButtonClicked2(String[] list2, ArrayList<String> selectedWisataBuatan);

        void onNegativeButtonClicked();
    }

    onMultiChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (onMultiChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " onMultiChoiceListener must implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ArrayList<String> selectedWisataBuatan = new ArrayList<>();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] list2 = getActivity().getResources().getStringArray(R.array.wisata_buatan);

        builder.setTitle("Pilih Minat Wisata Buatan:")
                .setMultiChoiceItems(list2, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedWisataBuatan.add(list2[i]);
                        } else {
                            selectedWisataBuatan.remove(list2[i]);
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked2(list2,selectedWisataBuatan);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeButtonClicked();
                    }
                });

        return builder.create();

    }
}
