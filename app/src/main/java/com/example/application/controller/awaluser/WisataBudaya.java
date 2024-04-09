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


public class WisataBudaya extends DialogFragment {

    public interface onMultiChoiceListener {
        void onPositiveButtonClicked1(String[] list1, ArrayList<String> selectedWisataBudaya);

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

        final ArrayList<String> selectedWisataBudaya = new ArrayList<>();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] list1 = getActivity().getResources().getStringArray(R.array.wisata_budaya);

        builder.setTitle("Pilih Minat Wisata Budaya :")
                .setMultiChoiceItems(list1, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedWisataBudaya.add(list1[i]);
                        } else {
                            selectedWisataBudaya.remove(list1[i]);
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked1(list1,selectedWisataBudaya);
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
